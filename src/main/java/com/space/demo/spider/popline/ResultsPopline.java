package com.space.demo.spider.popline;

import com.alibaba.fastjson.JSON;
import com.space.demo.common.CommonEnum;
import com.space.demo.common.ResultObj;
import com.space.demo.exception.BizException;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResultsPopline implements Pipeline {
    private HttpServletResponse response=null;

    public ResultsPopline(HttpServletResponse response){
        this.response=response;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Object> resLists=new ArrayList<>();
        for(Map.Entry<String,Object> entry:resultItems.getAll().entrySet()){
            resLists.add(entry.getValue());
        }
        try {
            ResultObj res=new ResultObj(CommonEnum.SUCCESS_CODE.getResultCode(),CommonEnum.SUCCESS_CODE.getResultMsg(),resLists);
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type","application/json");
            response.setHeader("Access-Control-Allow-Methods","GET,POST");
            response.setHeader("Access-Control-Allow-Origin","*");
            response.getWriter().write(JSON.toJSONString(res));
        } catch (IOException e) {
            throw new BizException(CommonEnum.NOT_FOUND);
        }
    }
}
