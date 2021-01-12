package com.space.demo.spider.popularization;

import com.space.demo.entity.Forecast;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;

/**
 * 发射预报
 */
@Data
@Component
//@ConfigurationProperties(prefix = "spring.spider")
public class ShotForecast implements PageProcessor {
    private Integer timeout=30000;
    private Integer sleeptime=1000;
    private Integer retrytimes=3;
    private Integer retrysleeptime=1000;

    @Override
    public void process(Page page) {
        List<Selectable> lists=page.getHtml().xpath("//div[@class='ai1ec-date']").nodes();
        List<Forecast> forecasts=new ArrayList<>();
        for(Selectable item:lists){
            String year=item.xpath("//div[@class='ai1ec-year']/text()").get();
            String month=item.xpath("//div[@class='ai1ec-month']/text()").get();
            String day=item.xpath("//div[@class='ai1ec-day']/text()").get();
            String week=item.xpath("//div[@class='ai1ec-weekday']/text()").get();
            String time=item.xpath("//span[@class='ai1ec-event-time']/text()").get();
            String eventTitle=item.xpath("//span[@class='ai1ec-event-title']/text()").get();
            String hoverTime=item.xpath("//div[@class='ai1ec-event-time']/text()").get();
            String hoverImgsrc=item.xpath("//a[@class='ai1ec-load-event']//img/@src").get();
            String hoverContent=item.xpath("//div[@class='ai1ec-popup-excerpt']/text()").get();
            String url=item.xpath("//div[@class='ai1ec-popup-excerpt']/a/@href").get();
            forecasts.add(new Forecast(year,month,day,week,time,eventTitle,hoverTime,hoverImgsrc,hoverContent,url));
        }
        page.putField("shotForecast",forecasts);
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }

    public static void main(String[] args) {
        Spider.create(new ShotForecast()).addUrl("http://www.spaceflightfans.cn/page/1").thread(5).run();
    }
}
