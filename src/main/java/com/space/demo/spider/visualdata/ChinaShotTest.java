package com.space.demo.spider.visualdata;

import com.space.demo.common.Constant;
import com.space.demo.entity.visualData.ChinaShotStatue;
import com.space.demo.spider.popline.RedisPopline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChinaShotTest implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;


    private List<ChinaShotStatue> statusList = new ArrayList<>();
    private Map<Integer, Map<String,Integer>> dataMap = new HashMap<>();

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='fslb']/span[@id='DataList1']/span").nodes();
        Pattern pattern1 = Pattern.compile("\\d+(?=年)");
        Pattern pattern2 = Pattern.compile("(?<=发射状态：).+");

        for(Selectable node:nodes){
            Integer year = null;
            String time = node.xpath("//div[@class='sj']/allText()").get();
//            System.out.println(time);
            Matcher matcher1 = pattern1.matcher(time);
            if(matcher1.find())
                year = Integer.valueOf(matcher1.group());
            String statue = node.xpath("//div[@class='zt']/allText()").get();
            Matcher matcher2 = pattern2.matcher(statue);
            if(matcher2.find())
                statue = matcher2.group();
//            System.out.println(statue);
            if (!dataMap.containsKey(year)){
                Map<String,Integer> inner = new HashMap();
                inner.put("success",0);
                inner.put("failure",0);
                inner.put("other",0);
                dataMap.put(year,inner);
            }
            switch (statue){
                case "发射成功":dataMap.get(year).put("success",dataMap.get(year).get("success")+1);break;
                case "发射失败":dataMap.get(year).put("failure",dataMap.get(year).get("failure")+1);break;
                default:dataMap.get(year).put("other",dataMap.get(year).get("other")+1);
            }
        }
        List<Selectable> urlList = page.getHtml().xpath("//div[@class='div_fenye']/a").nodes();
        int i = 0;
        for(i=0;i<urlList.size();i++){
            Selectable url = urlList.get(i);
            if(url.xpath("/a/allText()").get().equals("下一页")){
                page.addTargetRequest("http://www.aihangtian.com/"+url.xpath("/a/@href").get());
                break;
            }

        }
        if(i == urlList.size()){
            for(Map.Entry<Integer, Map<String,Integer>> entry:dataMap.entrySet()){
                statusList.add(new ChinaShotStatue(entry.getKey(),entry.getValue().get("success"),entry.getValue().get("failure"),entry.getValue().get("other")));
            }
            page.putField(Constant.REDIS_SHOT_SUCCESS_FAILURE,statusList);
        }
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetrySleepTime(retrysleeptime)
                .setRetryTimes(retrytimes);
    }

    public static void main(String[] args) {
        new Spider(new ChinaShotTest())
                .addUrl("http://www.aihangtian.com/fashe/china-all.html")
                .addPipeline(new RedisPopline())
                .thread(5)
                .run();
    }
}
