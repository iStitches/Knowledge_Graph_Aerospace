package com.space.demo.spider.visualdata;

import com.space.demo.common.Constant;
import com.space.demo.entity.visualData.ChinaShotStatue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ShotSuccessAndFailure implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

    private Map<Integer,Map<String,Integer>> shotSuccessAndFailure = new HashMap<>();

    @Override
    public void process(Page page) {
        List<Selectable> trList = page.getHtml().xpath("//tr").nodes();
        String title1 = page.getHtml().xpath("//h1[@class='entry-title']/text()").get().replaceAll("中国|航天","");
        String title2 = title1.replace("发射记录","");
        //获取键
        Pattern pattern = Pattern.compile("\\d*(?=年)");
        Matcher matcher = pattern.matcher(title2);
        if(matcher.find())
            title2 = matcher.group();
        Integer year = Integer.valueOf(title2);
        //存放 Map：时间----Map(成功/失败  ： 次数)
        Map<String,Integer> tmpMap = shotSuccessAndFailure.get(year);
        if(tmpMap == null){
            shotSuccessAndFailure.put(year,new HashMap<String,Integer>());
            tmpMap = shotSuccessAndFailure.get(year);
            tmpMap.put("成功",0);
            tmpMap.put("失败",0);
            tmpMap.put("其它",0);
        }

        tmpMap = shotSuccessAndFailure.get(year);
        for(int i=1;i<trList.size();i++){
            Selectable node = trList.get(i);
            String result = node.xpath("//td[7]/allText()").get().replaceAll("\\s*","");
            if (result!=null && !result.equals("")) {
                if(result.equals("成功") || result.equals("失败")){
                    tmpMap.put(result,tmpMap.get(result)+1);
                }
                else{
                    tmpMap.put("其它",tmpMap.get("其它")+1);
                }
            }
        }
        List<Selectable> urlList = page.getHtml().xpath("//div[@class='entry-content']//strong/a").nodes();
        for(int i=0;i<urlList.size();i++){
            System.out.println("爬到了第："+i+"个");
            Selectable selectable = urlList.get(i);
            String tempUrl = selectable.xpath("allText()").get();
            if(title1.contains(tempUrl) && i+1<urlList.size()){
                page.addTargetRequest(urlList.get(i+1).xpath("a/@href").get());
                break;
            }
            if(i==urlList.size()-1){
                List<ChinaShotStatue> list = new ArrayList<>();
                for(Map.Entry<Integer,Map<String,Integer>> out:shotSuccessAndFailure.entrySet()){
                    Integer tempYear = out.getKey();
                    Integer tempSuccess = out.getValue().get("成功");
                    Integer tempFailure = out.getValue().get("失败");
                    Integer tempOther = out.getValue().get("其它");
                    list.add(new ChinaShotStatue(tempYear,tempSuccess,tempFailure,tempOther));
                }
                page.putField(Constant.REDIS_SHOT_SUCCESS_FAILURE,list);
                break;
            }
        }
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetrySleepTime(retrysleeptime)
                .setRetryTimes(retrytimes);
    }
}
