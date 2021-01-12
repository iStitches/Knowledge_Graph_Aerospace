package com.space.demo.spider.popularization;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 历史上的今天数据爬取
 */
public class HistoryToday implements PageProcessor {
    private String charset="UTF-8";
    private Integer timeout=3000;
    private Integer retrytimes=3;

    @Override
    public void process(Page page) {
        String content=page.getHtml().xpath("//div[@class='infor']/p/text()").get();
        System.out.println(content);
    }

    @Override
    public Site getSite() {
        return new Site().setCharset(charset)
                .setTimeOut(timeout)
                .setRetryTimes(retrytimes);
    }

    public static void main(String[] args) {
        Spider.create(new HistoryToday()).addUrl("http://www.cnsa.gov.cn/index.html").thread(5).run();
    }
}
