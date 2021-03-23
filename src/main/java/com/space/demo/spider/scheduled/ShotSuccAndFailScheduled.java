package com.space.demo.spider.scheduled;

import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.spider.visualdata.ShotSuccessAndFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class ShotSuccAndFailScheduled {
    @Autowired
    ShotSuccessAndFailure shotSuccessAndFailure;
    @Autowired
    RedisPopline redisPopline;

    @Scheduled(cron = "0 18 17 23 * ?")
    public void setShotStatus(){
        new Spider(shotSuccessAndFailure)
                .addUrl("http://www.spaceflightfans.cn/from-1974-to-1990-years")
                .addPipeline(redisPopline)
                .thread(5)
                .run();
    }
}
