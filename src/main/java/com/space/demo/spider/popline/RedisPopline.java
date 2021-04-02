package com.space.demo.spider.popline;

import com.space.demo.util.JSONUtil;
import com.space.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisPopline implements Pipeline {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Set<Map.Entry<String, Object>> entries = resultItems.getAll().entrySet();
        String redisName = null;
        if(entries.size()>0){
            for(Map.Entry<String,Object> entry:entries){
                redisName = entry.getKey();
                List countMap = (List) entry.getValue();
                redisUtil.setValue(redisName,JSONUtil.parseArrayToJson(countMap));
            }
        }
    }
}
