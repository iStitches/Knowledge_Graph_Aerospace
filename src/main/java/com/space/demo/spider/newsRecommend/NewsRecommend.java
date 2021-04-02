package com.space.demo.spider.newsRecommend;


import com.space.demo.entity.newsReommend.News;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class NewsRecommend implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

    @Override
    public void process(Page page) {
        List<Selectable> lists=page.getHtml().xpath("//div[@id='main-content' or @id='content']//article").nodes();
        List<News> results=new ArrayList<>();
        //获取分类标签
        for(Selectable node:lists){
            List<String> tags=node.xpath("//span[@class='cat-links']/a//text()").all();
            String title=node.xpath("//h1[@class='entry-title']/a/text()").replace("【.*?】","").get();
            String date=node.xpath("//time[@class='entry-date']/text()").get();
            String imgsrc=node.xpath("//div[@class='entry-content']//img/@src").get();
            List<String> des=node.xpath("//div[@class='entry-content']//p//allText()|//span/allText()").replace("【.*?】","").all();
            String descripe = null;
            if(des==null || des.size()==0)
                descripe = "";
            else{
                StringBuilder describe=new StringBuilder();
                for(String str:des){
                    if(str.startsWith("<"))
                        break;
                    if(!str.matches("\\S*"))
                        describe.append(str);
                }
                descripe = describe.toString();
            }
            String url=node.xpath("//h1[@class='entry-title']/a/@href").get();
            News one=new News(tags,title,date,url,imgsrc,descripe);
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
    }
}
