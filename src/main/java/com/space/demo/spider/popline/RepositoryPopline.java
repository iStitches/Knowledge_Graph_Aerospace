package com.space.demo.spider.popline;

import com.space.demo.common.ChildNode;
import com.space.demo.controller.Query;
import com.space.demo.entity.newsReommend.Question;
import com.space.demo.entity.visualData.localtion.LocationDetail;
import com.space.demo.service.graph.ChildNodeService;
import com.space.demo.service.question.QuestionService;
import com.space.demo.service.visual.LocationDetailService;
import com.space.demo.spider.newsRecommend.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 持久化到数据库
 */
@Component
public class RepositoryPopline implements Pipeline {
    @Autowired
    QuestionService questionService;
    @Autowired
    LocationDetailService locationDetailService;
    @Autowired
    ChildNodeService childNodeService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Set<Map.Entry<String, Object>> entries = resultItems.getAll().entrySet();
        String keyname=null;
        if(entries.size()>0){
            for(Map.Entry<String, Object> map:entries){
                keyname = map.getKey();
                List<Object> objectList = (List<Object>) map.getValue();

                if(objectList.get(0) instanceof Question){
                    List<Question> questionlist = (List<Question>) map.getValue();
                    questionService.addQuestionList(questionlist);
                }
                else if(objectList.get(0) instanceof LocationDetail){
                    List<LocationDetail> locationDetails = (List<LocationDetail>) map.getValue();
                    locationDetailService.batchInsert(locationDetails);
                }
                else if(objectList.get(0) instanceof ChildNode){
                    List<ChildNode> childNodes = (List<ChildNode>)map.getValue();
                    childNodeService.batchInsertChildNodes(childNodes);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Spider(new QuestionAnswer())
                .addUrl("http://www.shapc.org/Salon_Test_info.aspx?ID=276")
                .addPipeline(new RepositoryPopline())
                .thread(1)
                .run();
    }
}
