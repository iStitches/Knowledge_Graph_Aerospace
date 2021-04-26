package com.space.demo.spider.scheduled;


import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.spider.visualdata.TrackDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class TrackCountScheduled {
    @Autowired
    RedisPopline redisPopline;
    @Autowired
    TrackDataHandler trackDataHandler;

//    @Scheduled(cron = "0 43 15 25 * ?")
    public void setTrackCount(){
        Spider.create(trackDataHandler)
                .addUrl("http://www.spaceflightfans.cn/from-1974-to-1990-years")
                .addPipeline(redisPopline)
                .thread(1)
                .run();
    }
}
