package com.space.demo.spider.scheduled;

import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.spider.visualdata.WorldShotStatusHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class WorldShotScheduled {
    @Autowired
    WorldShotStatusHandler worldShotStatusHandler;
    @Autowired
    RedisPopline redisPopline;

//    @Scheduled(cron = "0 35 22 20 * ?")
    public void setWorldData(){
        new Spider(worldShotStatusHandler)
                .addUrl("http://www.aihangtian.com/fashe/index.html")
                .addPipeline(redisPopline)
                .thread(6)
                .run();
    }
}
