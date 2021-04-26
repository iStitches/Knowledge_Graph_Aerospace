package com.space.demo.spider.scheduled;

import com.space.demo.spider.knowledgeGraph.GraphHandler;
import com.space.demo.spider.popline.RepositoryPopline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;


@EnableScheduling
@Component
public class ChildNodesScheduled {

    @Autowired
    GraphHandler graphHandler;
    @Autowired
    RepositoryPopline repositoryPopline;

//    @Scheduled(cron = "0 46 15 25 * ?")
    public void test(){
        new Spider(graphHandler)
                .addPipeline(repositoryPopline)
                .addUrl("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.1.1.")
                .thread(8)
                .run();
    }
}
