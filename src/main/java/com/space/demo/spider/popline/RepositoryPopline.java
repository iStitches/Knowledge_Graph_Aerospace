package com.space.demo.spider.popline;

import com.space.demo.entity.Detail;
import com.space.demo.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * 持久化到数据库
 */
@Component
public class RepositoryPopline implements Pipeline {
    @Autowired
    DetailService detailService;

    @Override
    public void process(ResultItems resultItems, Task task) {
         for(Map.Entry<String,Object> entry:resultItems.getAll().entrySet()){
             if(entry.getKey().contains("spaceDetail")){
                 Detail detail=(Detail)entry.getValue();
                 detailService.saveDetailToDB(detail);
             }
         }
    }
}
