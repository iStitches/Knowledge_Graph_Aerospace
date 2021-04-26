package com.space.demo.spider.visualdata;

import com.space.demo.common.Constant;
import com.space.demo.entity.visualData.ChinaShotStatue;
import com.space.demo.spider.popline.RedisPopline;
import com.space.demo.util.JSONUtil;
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
public class ChinaShotTest implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

//    private Integer timeout = 30000;
//    private Integer sleeptime = 1000;
//    private Integer retrytimes = 3;
//    private Integer retrysleeptime = 1000;


    private List<ChinaShotStatue> statusList = new ArrayList<>();

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//table[@log-set-param='table_view'][2]/tbody/tr").nodes();
        nodes.remove(0);
        nodes.remove(nodes.size()-1);
        //迄今为止所有年爬取
        for(Selectable node:nodes){
            List<Selectable> tdLists = node.xpath("//td").nodes();
            ChinaShotStatue statue = new ChinaShotStatue();
            Integer year = Integer.valueOf(tdLists.get(0).xpath("//div/allText()").get().replace("年",""));
            statue.setYear(year);
            statue.setSuccess(Integer.valueOf(tdLists.get(2).xpath("//div/allText()").get().replace("·","")));
            statue.setFailure(Integer.valueOf(tdLists.get(3).xpath("//div/allText()").get()));
            statue.setOther(0);
            statue.setAccumulateAll(Integer.valueOf(tdLists.get(4).xpath("//div/allText()").get()));
            statue.setAccumulateSuccess(Integer.valueOf(tdLists.get(5).xpath("//div/allText()").get()));
            statue.setAccumulateFailure(Integer.valueOf(tdLists.get(6).xpath("//div/allText()").get()));
            statusList.add(statue);
        }
        //数据持久化到文件
        String json = JSONUtil.parseArrayToJson(statusList);
        JSONUtil.saveJsonDataToFile("src/main/resources/static/chinaShotEveryYear.json",json);
        //数据转存到Redis中
        page.putField(Constant.REDIS_SHOT_SUCCESS_FAILURE,statusList);
    }

    @Override
    public Site getSite() {
        return new Site().setTimeOut(timeout)
                .setSleepTime(sleeptime)
                .setRetrySleepTime(retrysleeptime)
                .setRetryTimes(retrytimes);
    }

    public static void main(String[] args) {
        new Spider(new ChinaShotTest())
                .addUrl("https://baike.baidu.com/item/%E4%B8%AD%E5%9B%BD%E8%BF%90%E8%BD%BD%E7%81%AB%E7%AE%AD%E5%8F%91%E5%B0%84%E8%AE%B0%E5%BD%95%E7%BB%AD%E8%A1%A8/22332448?fr=aladdin#2")
                .addPipeline(new RedisPopline())
                .thread(1)
                .run();
    }
}
