package com.space.demo.spider.popularization;

import com.space.demo.entity.ActiveEvent;
import com.space.demo.entity.Knowledge;
import lombok.Data;
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
//@ConfigurationProperties(prefix = "spring.spider")
public class KnowledgeProduct implements PageProcessor {
    private Integer timeout=30000;
    private Integer sleeptime=1000;
    private Integer retrytimes=3;
    private Integer retrysleeptime=1000;
    private Knowledge knowledge=new Knowledge();


    @Override
    public void process(Page page) {
        if(page.getUrl().regex(".*?index.html").match()){
            String type=page.getHtml().xpath("//div[@class='l-titleBar']//a[4]/text()").get();
            String title=page.getHtml().xpath("//div[@class='gaofenTop']//dd/h4/text()").get();
            Selectable node=page.getHtml().xpath("//div[@class='gaofenTop']//dd").nodes().get(0);
            String des=node.regex("(?<=</h4>)[\\s\\S]*?(?=<a.*?</a>)").get();
            String imgsrc="http://www.cnsa.gov.cn/"+page.getHtml().xpath("//div[@class='gaofenTop']//img/@src").replace("(\\.\\./)+","").get();
            List<String> urlLists=page.getHtml().xpath("//div[@class='xinwen_l']/ul/li").links().all();
            knowledge.setType(type);
            knowledge.setTitle(title);
            knowledge.setDescribe(des);
            knowledge.setImgsrc(imgsrc);
            switch (type){
                case "探月与深空探测": knowledge.setId(1);break;
                case "高分辨率对地观测系统": knowledge.setId(2);break;
                case "中国载人航天工程": knowledge.setId(3);break;
                case "北斗导航系统": knowledge.setId(4);break;
                default:;break;
            }
            page.addTargetRequests(urlLists);
        }
        else{
            String eventTitle=page.getHtml().xpath("//div[@class='wrap1130']/h1/text()").get();
            List<String> contentList=page.getHtml().xpath("//div[@class='conText']//p/allText()").all();
            StringBuilder contentBuilder=new StringBuilder();
            for(String str:contentList){
                if(!str.matches("\\s+"))
                    contentBuilder.append(str.trim().toString());
            }
            StringBuilder imgBuilder=new StringBuilder();
            List<String> imgList=page.getHtml().xpath("//div[@class='conText']//img/@src").all();
            for(String str:imgList){
                imgBuilder.append("http://www.cnsa.gov.cn/"+str.replaceAll("../","")).append(";");
            }
            String content=contentBuilder.toString();
            String imgsrc=imgBuilder.toString();
            ActiveEvent event=new ActiveEvent(knowledge.getId(),eventTitle,content,imgsrc);
            knowledge.getActiveEvents().add(event);
            page.putField("event",event);
        }
    }

    @Override
    public Site getSite() {
        return new Site()
                .setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }

    public static void main(String[] args) {
        Spider.create(new KnowledgeProduct()).addUrl("http://www.cnsa.gov.cn/n6758824/n6759009/n6759040/index.html")
                .thread(1).run();
    }
}
