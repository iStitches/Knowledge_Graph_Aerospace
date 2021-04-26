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

//    @Scheduled(cron = "0 35 17 25 * ?")
    public void setShotStatus(){
        new Spider(shotTest)
                .addUrl("https://baike.baidu.com/item/%E4%B8%AD%E5%9B%BD%E8%BF%90%E8%BD%BD%E7%81%AB%E7%AE%AD%E5%8F%91%E5%B0%84%E8%AE%B0%E5%BD%95%E7%BB%AD%E8%A1%A8/22332448?fr=aladdin#2")
                .addPipeline(redisPopline)
                .thread(1)
                .run();
    }
}
