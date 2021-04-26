package com.space.demo.spider.scheduled;


import com.space.demo.spider.newsRecommend.QuestionAnswer;
import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.spider.popline.RepositoryPopline;
import com.space.demo.spider.visualdata.shotLocation.ShotLocationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
@EnableScheduling
public class QuestionScheduled {
    @Autowired
    QuestionAnswer questionAnswer;
    @Autowired
    RepositoryPopline repositoryPopline;

//    @Scheduled(cron = "0 55 15 25 * ?")
    public void setLocationHandler(){
        new Spider(questionAnswer)
                .addUrl("http://www.shapc.org/Salon_Test_info.aspx?ID=290")
                .addUrl("http://www.shapc.org/Salon_Test_info.aspx?ID=281")
                .addUrl("http://www.shapc.org/Salon_Test_info.aspx?ID=280")
                .addPipeline(repositoryPopline)
                .thread(1)
                .run();
    }
}
