package com.space.demo.spider.visualdata;

import com.space.demo.common.Constant;
import com.space.demo.entity.visualData.TrackCount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;

@Component
public class TrackDataHandler implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

//    private Integer timeout=30000;
//    private Integer sleeptime=1000;
//    private Integer retrytimes=3;
//    private Integer retrysleeptime=1000;
    private Map<String,Integer> trackCount = new HashMap<>();

    @Override
    public void process(Page page) {
        List<Selectable> trList = page.getHtml().xpath("//tr").nodes();
        for(int i=1;i<trList.size();i++){
            Selectable node = trList.get(i);
            String trackName = node.xpath("//td[6]/text()").get();
            if(trackCount.get(trackName) == null)
                trackCount.put(trackName,1);
            else{
                trackCount.put(trackName,trackCount.get(trackName)+1);
            }
        }
        String title = page.getHtml().xpath("//h1[@class='entry-title']/text()").get().replaceAll("中国|航天","");
        List<Selectable> urlList = page.getHtml().xpath("//div[@class='entry-content']//a").nodes();
        for(int i=0;i<urlList.size();i++){
            Selectable selectable = urlList.get(i);
            String tempUrl = selectable.xpath("allText()").get();
            if(tempUrl.equals(title) && i+1<urlList.size()){
                page.addTargetRequest(urlList.get(i+1).xpath("a/@href").get());
                break;
            }
            if(i==urlList.size()-1){
                List<TrackCount> list = new ArrayList<>();
                for(Map.Entry<String,Integer> entry:trackCount.entrySet()){
                    list.add(new TrackCount(entry.getKey(),entry.getValue()));
                }
                page.putField(Constant.REDIS_TRACK_COUNT,list);
                break;
            }
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
