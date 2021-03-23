package com.space.demo.spider.visualdata;

import com.space.demo.common.Constant;
import com.space.demo.entity.visualData.WorldCountry;
import com.space.demo.entity.visualData.WorldShotStatue;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WorldShotStatusHandler implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;

    private Map<String, Map<String, Map<String, Integer>>> worldStatus = new HashMap<>();

    public WorldShotStatusHandler() {
        worldStatus.put("更早年份", new HashMap<>());
        worldStatus.put("1993~1996", new HashMap<>());
        worldStatus.put("1997~2000", new HashMap<>());
        worldStatus.put("2001~2004", new HashMap<>());
        worldStatus.put("2005~2008", new HashMap<>());
        worldStatus.put("2009~2012", new HashMap<>());
        worldStatus.put("2013~2016", new HashMap<>());
        worldStatus.put("2017~2020", new HashMap<>());
    }

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='fslb']/span/span").nodes();
        //爬取当页数据
        for (Selectable node : nodes) {
            String str = node.xpath("//div[@class='sj']/allText()").get();
            int time = 0;
            Pattern pattern = Pattern.compile("(?<=发射时间：)\\d+(?=年)");
            Matcher m =pattern.matcher(str);
            while(m.find())
                time = Integer.valueOf(m.group());
            String country = node.xpath("//div[@class='fsgj']/img/@alt").get();
            int result = node.xpath("//div[@class='zt']/allText()").get().contains("发射成功")?1:-1;

            Map<String, Map<String, Integer>> outterMap = null;
            if (time <= 2020 && time >= 2017)
                outterMap = worldStatus.get("2017~2020");
            else if (time >= 2013 && time <= 2016)
                outterMap = worldStatus.get("2013~2016");
            else if (time >= 2009 && time <= 2012)
                outterMap = worldStatus.get("2009~2012");
            else if (time >= 2005 && time <= 2008)
                outterMap = worldStatus.get("2005~2008");
            else if (time >= 2001 && time <= 2004)
                outterMap = worldStatus.get("2001~2004");
            else if (time >= 1997 && time <= 2000)
                outterMap = worldStatus.get("1997~2000");
            else if (time >= 1993 && time <= 1996)
                outterMap = worldStatus.get("1993~1996");
            else
                outterMap = worldStatus.get("更早年份");

            if(outterMap.get(country) == null){
                outterMap.put(country,new HashMap<>());
                outterMap.get(country).put("success",0);
                outterMap.get(country).put("failure",0);
            }
            Map<String, Integer> innerMap = outterMap.get(country);
            if(result == 1)
                innerMap.put("success",innerMap.get("success")+1);
            else
                innerMap.put("failure",innerMap.get("failure")+1);
        }

        //记录下个将要爬取的url
        List<Selectable> urlList = page.getHtml().xpath("//div[@class='div_fenye']/a").nodes();
        String nextUrl = null;
        for(Selectable url:urlList){
            if(url.xpath("allText()").get().equals("下一页")){
                nextUrl = url.xpath("a/@href").get();
                break;
            }
        }
        if(nextUrl==null || nextUrl=="")
        {
            List<WorldShotStatue> list = new ArrayList<>();
            for(Map.Entry<String, Map<String, Map<String, Integer>>> entry:worldStatus.entrySet()){
                String year = entry.getKey();    //获取年份
                List<WorldCountry> status = new ArrayList<>();  //存储多个对象
                for(Map.Entry<String, Map<String, Integer>> entry1:entry.getValue().entrySet()){
                     String countryName = entry1.getKey();   //获取国家名
                     Integer success = entry1.getValue().get("success");
                     Integer failure = entry1.getValue().get("failure");
                     status.add(new WorldCountry(countryName,success,failure));
                }
                list.add(new WorldShotStatue(year,status));
            }
            page.putField(Constant.REDIS_WORLD_SHOTSTATUS,list);
        }
        else{
            page.addTargetRequest("http://www.aihangtian.com/"+nextUrl);
        }
    }

        @Override
        public Site getSite () {
            return new Site().setTimeOut(timeout)
                    .setSleepTime(sleeptime)
                    .setRetryTimes(retrytimes)
                    .setRetrySleepTime(retrysleeptime);
        }
}
