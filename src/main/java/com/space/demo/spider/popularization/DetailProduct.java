package com.space.demo.spider.popularization;

import com.space.demo.entity.Detail;
import com.space.demo.spider.popline.RepositoryPopline;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * 航天详细产品
 */
@Data
@Component
public class DetailProduct implements PageProcessor {
    private Integer timeout=30000;
    private Integer sleeptime=1000;
    private Integer retrytimes=3;
    private Integer retrysleeptime=1000;

    @Override
    public void process(Page page) {
        //解析index.html主页
        if(page.getUrl().regex("\\S+index.html").match()){
            List<Selectable> listLis=page.getHtml().xpath("//div[@class='tjht clearfix']/ul/li").nodes();
            List<String> urlList=new ArrayList<>();
            for(Selectable li:listLis){
                String url="http://www.cnsa.gov.cn/"+li.xpath("//a/@href").replace("\\.\\./","").get();
                urlList.add(url);
            }
            page.addTargetRequests(urlList);
        }
        //解析详情页 content.html
        else{
            String type=page.getHtml().xpath("//div[@class='l-titleBar']//a[4]/text()").get();
            String name=page.getHtml().xpath("//div[@class='l-articel']//h1/text()").get();

            List<String> contentList=page.getHtml().xpath("//div[@class='conText']//p/allText()").all();
            StringBuilder content=new StringBuilder();
            for(String str:contentList){
                if(!str.replaceAll("\\s+","").matches("\\s+"))
                    content.append(str);
            }
            StringBuilder imgList=new StringBuilder();
            List<String> imgsrc=page.getHtml().xpath("//div[@class='conText']//img/@src").all();
            for(String str:imgsrc){
                imgList.append("http://www.cnsa.gov.cn/"+str.replaceAll("\\.\\./",""));
            }
//            System.out.println(content.toString().trim());
            Detail detail=new Detail(type,name,content.toString().trim(),imgList.toString());
            page.putField("spaceDetail",detail);
        }
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }

    public static void main(String[] args) {
        Spider.create(new DetailProduct())
                .addUrl("http://www.cnsa.gov.cn/n6758824/n6759008/n6759011/index.html")
                .addPipeline(new RepositoryPopline())
                .thread(5)
                .run();
    }
}
