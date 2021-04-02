package com.space.demo.spider.scheduled;

import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.spider.visualdata.ChinaShotTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class ShotSuccAndFailScheduled {
    @Autowired
    ChinaShotTest shotTest;
    @Autowired
    RedisPopline redisPopline;

//    @Scheduled(cron = "0 35 30 20 * ?")
    public void setShotStatus(){
        new Spider(shotTest)
                .addUrl("http://www.aihangtian.com/fashe/china-all.html")
                .addPipeline(redisPopline)
                .thread(5)
                .run();
    }
}
