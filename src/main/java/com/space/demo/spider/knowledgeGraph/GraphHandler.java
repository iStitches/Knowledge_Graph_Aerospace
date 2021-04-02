package com.space.demo.spider.knowledgeGraph;


import com.space.demo.common.ChildNode;
import com.space.demo.common.Constant;
import com.space.demo.common.ParentNode;
import com.space.demo.spider.popline.RepositoryPopline;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 多线程爬虫解决思路：
 *   1. 确定好并且存储所有待爬取的 url；
 *   2. 开启多个线程同时爬取，当爬取完当前url时检查是否还有下一条路径(targetRequest)；如果没有则从开始的url集合中拿取一条进行爬取(同时删除)；
 *   如果开始的url集合为空，则证明整个过程爬取完毕，结束
 */
@Component
public class GraphHandler implements PageProcessor {
    @Value("${spring.spider.timeout}")
    private Integer timeout;

    @Value("${spring.spider.sleeptime}")
    private Integer sleeptime;

    @Value("${spring.spider.retrytimes}")
    private Integer retrytimes;

    @Value("${spring.spider.retrysleeptime}")
    private Integer retrysleeptime;
//    private Integer timeout = 10000;
//    private Integer sleeptime = 1000;
//    private Integer retrytimes = 3;
//    private Integer retrysleeptime = 1000;

    List<String> list = Arrays.asList("航天综合术语","航天武器突破与防御","航天技术应用","发射与地面保障设备","试验与测控","电子与信息技术","推进剂与航天材料","总体与系统设计","推进技术与发动机","制导与控制","弹头与战斗部","航天器有效载荷及机电系统","生命保障系统与航天医学","航天器轨道动作与返回技术","空间科学");

    ParentNode parentNode = new ParentNode();
    ArrayList<ChildNode> childs = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();

    {
        for(int i=2;i<=4;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.1."+i+".");
        }
        for(int i=1;i<=3;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.10."+i+".");
        }
        for(int i=1;i<=8;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.11."+i+".");
        }
        for(int i=1;i<=4;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.12."+i+".");
        }
        for(int i=1;i<=3;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.13."+i+".");
        }
        for(int i=1;i<=5;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.14."+i+".");
        }
        for(int i=1;i<=8;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.2."+i+".");
        }
        for(int i=1;i<=6;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.3."+i+".");
        }
        for(int i=1;i<=3;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.4."+i+".");
        }
        for(int i=1;i<=3;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.5."+i+".");
        }
        for(int i=1;i<=6;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.6."+i+".");
        }
        for(int i=1;i<=2;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.7."+i+".");
        }
        for(int i=1;i<=4;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.8."+i+".");
        }
        for(int i=1;i<=4;i++){
            urls.add("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.9."+i+".");
        }
    }

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='encyclopedia_cur']").nodes();
        String title = nodes.get(0).xpath("//p[@class='p2']/a[1]/allText()").get().replaceAll("(\\]|\\[)","");
        Integer categoryId = list.indexOf(title)+1;
        Random random = new Random();

        for(Selectable node:nodes){
            String name = node.xpath("//p[1]/a/allText()").get();
            String url = "http://www.spacemore.com.cn/"+node.xpath("//p[1]/a/@href").get();
            Integer size = Integer.valueOf(random.nextInt(100));
            Integer value = Integer.valueOf(random.nextInt(10000));
            childs.add(new ChildNode(name,size,value,url,categoryId));
        }

        //获取下一页
        List<Selectable> liList = page.getHtml().xpath("//div[@class='fanye']//a").nodes();
        String nextUrl = null;
        for(Selectable li : liList){
            if(li.xpath("//a/allText()").equals("下一页")){
                nextUrl = "http://www.spacemore.com.cn/"+li.xpath("//a/@href").get();
                break;
            }
        }
        if(nextUrl != null)
            page.addTargetRequest(nextUrl);
        else{
              if(!urls.isEmpty()){
                  page.addTargetRequest(urls.get(0));
                  urls.remove(0);
              }
              else
                  page.putField(Constant.REDIS_SPACE_GRAPH_WORDS_CONTENT,childs);
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
        new Spider(new GraphHandler())
                .addUrl("http://www.spacemore.com.cn/Entry/EntrySearchList?searchtype=2.1.1.")
                .thread(8)
                .run();
    }
}
