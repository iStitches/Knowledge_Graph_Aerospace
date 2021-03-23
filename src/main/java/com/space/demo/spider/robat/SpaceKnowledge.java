package com.space.demo.spider.robat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.space.demo.util.JSONUtil;
import org.springframework.util.ResourceUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 航天知识语料库爬取
 * http://www.yuzhouzs.com/yzqy/
 */
public class SpaceKnowledge implements PageProcessor {
    private Integer timeout=30000;
    private Integer sleeptime=1000;
    private Integer retrytimes=3;
    private Integer retrysleeptime=1000;
    private static String path=null;

    static {
        try {
            String path=ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"config").getPath();
            File file = new File(path+"/answer1.json");
            if(!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        path = SpaceKnowledge.class.getClassLoader().getResource("config/answer1.json").getPath();
    }

    @Override
    public void process(Page page) {
        List<Selectable> allNodes = page.getHtml().xpath("//div[@class='block']").nodes();
        JSONObject subObj = null;
        for(Selectable node:allNodes){
            String question = node.xpath("//h2/a/allText()").get();
            String answer = node.xpath("//div[@class='preview']/text()").get()+"\n参考链接：http://www.yuzhouzs.com"+node.xpath("//h2/a/@href").get();
            subObj = new JSONObject();
            subObj.put("question",question);
            subObj.put("answer",answer);
            JSONUtil.saveJsonDataToFile(path,subObj);
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
        //宇宙起源
        List<String> urlLists = new ArrayList<>();
        for(int i=1;i<=6;i++)
            urlLists.add("http://www.yuzhouzs.com/yzqy/list_1_"+i+".html");
        //宇宙大爆炸
        for(int i=1;i<=2;i++)
            urlLists.add("http://www.yuzhouzs.com/yzdbz/list_2_"+i+".html");
        //太阳系
        for(int i=1;i<=21;i++)
            urlLists.add("http://www.yuzhouzs.com/tyx/list_7_"+i+".html");
        //银河系
        for(int i=1;i<=7;i++)
            urlLists.add("http://www.yuzhouzs.com/yhx/list_5_"+i+".html");
        //河外星系
        for(int i=1;i<=10;i++)
            urlLists.add("http://www.yuzhouzs.com/hwxx/list_6_"+i+".html");
        //黑洞
        for(int i=1;i<=3;i++)
            urlLists.add("http://www.yuzhouzs.com/hd/list_4_"+i+".html");
        //宇宙理论
        for(int i=1;i<=19;i++)
            urlLists.add("http://www.yuzhouzs.com/yzll/list_3_"+i+".html");
        //UFO
        for(int i=1;i<=5;i++)
            urlLists.add("http://www.yuzhouzs.com/UFO/list_8_"+i+".html");
        //宇宙结构
        for(int i=1;i<=3;i++)
            urlLists.add("http://www.yuzhouzs.com/yzjg/list_12_"+i+".html");
        //月球
        for(int i=1;i<=6;i++)
            urlLists.add("http://www.yuzhouzs.com/yueqiu/list_11_"+i+".html");
        //天文
        for(int i=1;i<=9;i++)
            urlLists.add("http://www.yuzhouzs.com/tw/list_10_"+i+".html");
        //航天
        for(int i=1;i<=13;i++)
            urlLists.add("http://www.yuzhouzs.com/ht/list_9_"+i+".html");
        //航空史
        for(int i=1;i<=4;i++)
            urlLists.add("http://www.yuzhouzs.com/hks/list_13_"+i+".html");
        //航天故事
        for(int i=1;i<=3;i++)
            urlLists.add("http://www.yuzhouzs.com/htgs/list_14_"+i+".html");
        //航天科技
        for(int i=1;i<=3;i++)
            urlLists.add("http://www.yuzhouzs.com/htkj/list_15_"+i+".html");
        //太阳
        for(int i=1;i<=2;i++)
            urlLists.add("http://www.yuzhouzs.com/smdty/list_16_"+i+".html");
        //宇宙时间奥秘
        for(int i=1;i<=6;i++)
            urlLists.add("http://www.yuzhouzs.com/yzsjam/list_17_"+i+".html");
        //宇宙之谜
        for(int i=1;i<=8;i++)
            urlLists.add("http://www.yuzhouzs.com/yzzm/list_18_"+i+".html");
        //宇宙探索
        for(int i=1;i<=2;i++)
            urlLists.add("http://www.yuzhouzs.com/yzzm/yzts/list_36_"+i+".html");
        Spider.create(new SpaceKnowledge()).addUrl(urlLists.toArray(new String[urlLists.size()])).thread(5).run();
    }
}
