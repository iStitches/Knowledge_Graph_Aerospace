package com.space.demo.spider.scheduled;

import com.space.demo.spider.popline.RepositoryPopline;
import com.space.demo.spider.visualdata.shotLocation.LocationDetialHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class LocationDetailScheduled {
    @Autowired
    LocationDetialHandler handler;
    @Autowired
    RepositoryPopline popline;

    @Scheduled(cron = "0 39 17 22 * ?")
    public void exercise(){
        new Spider(handler)
                .addUrl("https://baike.baidu.com/item/%E4%B8%AD%E5%9B%BD%E6%96%87%E6%98%8C%E8%88%AA%E5%A4%A9%E5%8F%91%E5%B0%84%E5%9C%BA/20183072?fromtitle=%E6%96%87%E6%98%8C%E5%8F%91%E5%B0%84%E5%9C%BA&fromid=23602423&fr=aladdin#9")
                .addPipeline(popline)
                .thread(1)
                .run();
    }
}
