package com.space.demo.controller;

import com.space.demo.spider.popline.ResultsPopline;
import com.space.demo.spider.popularization.DetailProduct;
import com.space.demo.spider.popularization.NewsRecommend;
import com.space.demo.spider.popularization.ShotForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import javax.servlet.http.HttpServletResponse;

/**
 * 资讯页
 */
@RestController
@RequestMapping("/query")
public class Query {
    @Autowired
    NewsRecommend newsRecommend;
    @Autowired
    ShotForecast shotForecast;
    @Autowired
    DetailProduct detailProduct;

    /**
     * 新闻换一批
     */
    @GetMapping("/news")
    public void getNews(HttpServletResponse response, @RequestParam(value = "page")Integer page){
        ResultsPopline popline=new ResultsPopline(response);
        Spider.create(newsRecommend).addUrl("http://www.spaceflightfans.cn/page/"+page).addPipeline(popline).thread(5).run();
    }

    /**
     * 发射预报
     */
    @GetMapping("/forecast")
    public void getShotForecast(HttpServletResponse response){
        ResultsPopline popline=new ResultsPopline(response);
        Spider.create(shotForecast).addUrl("http://www.spaceflightfans.cn/").addPipeline(popline).thread(5).run();
    }

//    /**
//     * 航天产品介绍
//     */
//    @GetMapping("/spaceTechnology")
//    public void getSpaceTechnology(HttpServletResponse response){
//        ResultsPopline popline=new ResultsPopline(response);
//        Spider.create(detailProduct).addUrl("http://www.cnsa.gov.cn/n6758824/n6759008/n6759011/index.html").addPipeline(popline).thread(5).run();
//    }
}
