package com.space.demo.spider.visualdata.shotLocation;

import com.space.demo.entity.visualData.localtion.LocationDetail;
import com.space.demo.spider.popline.RepositoryPopline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationDetialHandler implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

    List<LocationDetail> list = new ArrayList<>();

    @Override
    public void process(Page page) {
//        processWenChang(page);
          processTaiYuan(page);
    }


    //文昌发射场数据
    public void processWenChang(Page page){
        List<Selectable> nodes = page.getHtml().xpath("//table[1]//tr").nodes();
        for(int i=1;i<nodes.size()-1;i++){
            Integer id = Integer.valueOf(nodes.get(i).xpath("//td[1]/allText()").get());
            String rocketType = nodes.get(i).xpath("//td[2]/allText()").get();
            String carriage = nodes.get(i).xpath("//td[3]/allText()").get();
            String shotTime = nodes.get(i).xpath("//td[4]/allText()").get();
            Integer number = Integer.valueOf(nodes.get(i).xpath("//td[5]/allText()").get());
            String targetTrack = nodes.get(i).xpath("//td[6]/allText()").get();
            String result = nodes.get(i).xpath("//td[7]/allText()").get();
            list.add(new LocationDetail(rocketType,carriage,shotTime,number,targetTrack,result,"文昌发射场"));
        }
        page.putField("wenchang",list);
    }
    //太原发射数据
    public void processTaiYuan(Page page){
        List<Selectable> nodes = page.getHtml().xpath("//table[1]//tr").nodes();
        for(int i=1;i<nodes.size()-2;i++){
            Integer id = Integer.valueOf(nodes.get(i).xpath("//td[1]/allText()").get());
            String rocketType = nodes.get(i).xpath("//td[2]/allText()").get();
            String carriage = nodes.get(i).xpath("//td[3]/allText()").get();
            String shotTime = nodes.get(i).xpath("//td[4]/allText()").get();
            Integer number = Integer.valueOf(nodes.get(i).xpath("//td[5]/allText()").get());
            String targetTrack = nodes.get(i).xpath("//td[6]/allText()").get();
            String result = nodes.get(i).xpath("//td[7]/allText()").get();
            list.add(new LocationDetail(rocketType,carriage,shotTime,number,targetTrack,result,"太原发射场"));
        }
        page.putField("wenchang",list);
    }


    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }
}
