package com.space.demo.controller;

import com.space.demo.spider.popline.ResultsPopline;
import com.space.demo.spider.newsRecommend.NewsRecommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import javax.servlet.http.HttpServletResponse;

/**
 * 资讯页
 */
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/space/query")
public class Query {
    @Autowired
    NewsRecommend newsRecommend;

    /**
     * 新闻换一批
     */
    @GetMapping("/news")
    public void getNews(HttpServletResponse response, @RequestParam(value = "page")Integer page){
        ResultsPopline popline=new ResultsPopline(response);
        Spider.create(newsRecommend).addUrl("http://www.spaceflightfans.cn/page/"+page).addPipeline(popline).thread(5).run();
    }
}
