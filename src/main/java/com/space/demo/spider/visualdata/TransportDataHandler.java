package com.space.demo.spider.visualdata;

import com.space.demo.common.Constant;
import com.space.demo.entity.knowledgeGraph.TransportRocket;
import com.space.demo.entity.visualData.ChinaTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransportDataHandler implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

    private Map<String, Map<String,Integer>> transportMap = new HashMap();

    @Override
    public void process(Page page) {
        List<Selectable> trNodes = page.getHtml().xpath("//div[@class='entry-content']/table[4]//tr").nodes();
        for(int i=1;i<trNodes.size();i++){
            List<Selectable> tdNodes = trNodes.get(i).xpath("tr/td").nodes();
            String transportName = null;
            for(int j=0;j<tdNodes.size();j++){
                switch (j){
                    case 0: {
                        transportMap.put(tdNodes.get(j).xpath("allText()").get(),new HashMap<>());
                        transportName = tdNodes.get(j).xpath("allText()").get();
                    }break;
                    case 1: transportMap.get(transportName).put("总发射次数",Integer.valueOf(tdNodes.get(j).xpath("allText()").get()));break;
                    case 2: {
                        System.out.println(tdNodes.get(j).xpath("allText()").get());
                        Integer successCount = tdNodes.get(j).xpath("allText()").get().equals("")?0:Integer.valueOf(tdNodes.get(j).xpath("allText()").get());
                        transportMap.get(transportName).put("成功次数",successCount);
                    }break;
                    case 4: transportMap.get(transportName).put("入轨航天器数",Integer.valueOf(tdNodes.get(j).xpath("allText()").get()));break;
                }
            }
        }
        List<ChinaTransport> list = new ArrayList<>();
        for(Map.Entry<String,Map<String,Integer>> out:transportMap.entrySet()){
            String name = out.getKey();
            Integer success = out.getValue().get("成功次数");
            Integer vehicle = out.getValue().get("入轨航天器数");
            Integer all = out.getValue().get("总发射次数");
            list.add(new ChinaTransport(name,success,vehicle,all));
        }
        page.putField(Constant.REDIS_CHINA_TRANSPORT,list);
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetryTimes(retrytimes)
                .setRetrySleepTime(retrysleeptime);
    }
}
