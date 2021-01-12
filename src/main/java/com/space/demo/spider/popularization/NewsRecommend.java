package com.space.demo.spider.popularization;


import com.space.demo.entity.News;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
@ConfigurationProperties(prefix = "spring.spider")
public class NewsRecommend implements PageProcessor {
    private Integer timeout;
    private Integer sleeptime;
    private Integer retrytimes;
    private Integer retrysleeptime;

    @Override
    public void process(Page page) {
        List<Selectable> lists=page.getHtml().xpath("//div[@id='main-content']//article").nodes();
        lists.remove(0);
        List<News> results=new ArrayList<>();
        //获取分类标签
        for(Selectable node:lists){
            List<String> tags=node.xpath("//span[@class='cat-links']/a//text()").all();
            String title=node.xpath("//h1[@class='entry-title']/a/text()").replace("【.*?】","").get();
            String date=node.xpath("//time[@class='entry-date']/text()").get();
            String imgsrc=node.xpath("//div[@class='entry-content']//img/@src").get();
            List<String> des=node.xpath("//div[@class='entry-content']//p//allText()|//span/allText()").replace("【.*?】","").all();
            StringBuilder describe=new StringBuilder();
            for(String str:des){
                if(!str.matches("\\S*"))
                    describe.append(str);
            }
            String url=node.xpath("//h1[@class='entry-title']/a/@href").get();
            News one=new News(tags,title,date,url,imgsrc,describe.toString());
            results.add(one);
        }
        page.putField("newsList",results);
    }

    @Override
    public Site getSite() {
        return new Site().setSleepTime(sleeptime)
                .setTimeOut(timeout)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }

    public static void main(String[] args) {
        Spider.create(new NewsRecommend()).addUrl("http://www.spaceflightfans.cn/").thread(5).run();
//        String str="【长九只疯狂吹风】先进液体、固体大推力发动机新进展！将支撑长五B、重型等火箭";
//        String pattern="【.*?】";
//        String res=str.replaceAll(pattern,"");
//        System.out.println(res);
    }
}
