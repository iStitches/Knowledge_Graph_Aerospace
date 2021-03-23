package com.space.demo.spider.visualdata.shotLocation;

import com.space.demo.common.Constant;
import com.space.demo.entity.visualData.localtion.ShotLocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ShotLocationHandler implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

    private Map<String,Integer> locationCount = new HashMap<>();

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='div_fsmain']//div[@class='dd']").nodes();
        for(Selectable node:nodes){
            String location = node.xpath("allText()").get().replace("发射地点：","");
            if(locationCount.get(location) == null){
                locationCount.put(location,1);
            }
            else{
                locationCount.put(location,locationCount.get(location)+1);
            }
        }
        List<Selectable> urlList = page.getHtml().xpath("//div[@class='div_fenye']/a").nodes();
        String nextUrl = null;
        for(Selectable url:urlList){
            if(url.xpath("allText()").get().equals("下一页")){
                nextUrl = url.xpath("a/@href").get();
                break;
            }
        }
        if(nextUrl==null || nextUrl=="")
        {
            List<ShotLocation> list = new ArrayList<>();
            for(Map.Entry<String,Integer> entry:locationCount.entrySet()){
                list.add(new ShotLocation(entry.getKey(),entry.getValue()));
            }
            page.putField(Constant.REDIS_SHOT_LOCATION,list);
        }
        else{
            page.addTargetRequest("http://www.aihangtian.com/"+nextUrl);
        }
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }
}
