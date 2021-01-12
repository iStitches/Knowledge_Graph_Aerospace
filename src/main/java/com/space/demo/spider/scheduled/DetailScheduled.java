package com.space.demo.spider.scheduled;

import com.space.demo.spider.popline.RepositoryPopline;
import com.space.demo.spider.popularization.DetailProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * 航天知识定时任务
 */
@Component
public class DetailScheduled {
    @Autowired
    private RepositoryPopline repositoryPopline;

    @Scheduled(cron = "0 15 12 12 * ?")   //每隔半个月执行一次
    public void repositoryScheduled(){
        Spider.create(new DetailProduct())
                .addUrl("http://www.cnsa.gov.cn/n6758824/n6759008/n6759012/index.html",
                        "http://www.cnsa.gov.cn/n6758824/n6759008/n6759013/index.html",
                        "http://www.cnsa.gov.cn/n6758824/n6759008/n6759014/index.html")
                .addPipeline(repositoryPopline)
                .thread(5)
                .run();
//        "http://www.cnsa.gov.cn/n6758824/n6759008/n6759011/index.html",
    }
}
