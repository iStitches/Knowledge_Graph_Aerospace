package com.space.demo.spider.newsRecommend;

import com.space.demo.entity.newsReommend.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionAnswer implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

    List<Question> questions = new ArrayList<>();

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//tr").nodes();
        for(Selectable node:nodes){
            String question = node.xpath("//dt/allText()").get().trim().substring(2);
            String itemA = node.xpath("//li[1]/allText()").get().trim();
            String itemB = node.xpath("//li[2]/allText()").get().trim();
            String itemC = node.xpath("//li[3]/allText()").get().trim();
            String itemD = node.xpath("//li[4]/allText()").get().trim();
            questions.add(new Question(question,itemA,itemB,itemC,itemD));
        }
        page.putField("question",questions);
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }
}
