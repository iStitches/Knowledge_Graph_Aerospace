package com.space.demo.spider.scheduled;

import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.spider.visualdata.TransportDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class TransportScheduled {
    @Autowired
    TransportDataHandler handler;
    @Autowired
    RedisPopline redisPopline;

    @Scheduled(cron = "0 5 20 17 * ?")
    public void setTransportData(){
        new Spider(handler)
                .addPipeline(redisPopline)
                .addUrl("http://www.spaceflightfans.cn/world-global-space-flight-real-time-statistics-table-in-2021-years")
                .thread(1)
                .run();
    }
}
