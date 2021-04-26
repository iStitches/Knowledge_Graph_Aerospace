package com.space.demo.spider.scheduled;

import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.spider.visualdata.shotLocation.ShotLocationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class ShotLocationScheduled {
    @Autowired
    ShotLocationHandler shotLocationHandler;
    @Autowired
    RedisPopline redisPopline;

//    @Scheduled(cron = "0 44 15 25 * ?")
    public void setLocationHandler(){
        new Spider(shotLocationHandler)
                .addUrl("http://www.aihangtian.com/fashe/china-all.html")
                .addPipeline(redisPopline)
                .thread(5)
                .run();
    }
}
