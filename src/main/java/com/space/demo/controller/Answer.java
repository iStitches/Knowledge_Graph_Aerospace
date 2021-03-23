package com.space.demo.controller;

import com.space.demo.common.ResultObj;
import com.space.demo.entity.newsReommend.Question;
import com.space.demo.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/space/question")
public class Answer {
    @Autowired
    QuestionService questionService;

    /**
     * 发送问题
     * @param page
     * @return
     */
    @GetMapping("/getList")
    public ResultObj getQuestionList(@RequestParam(value = "page")Integer page){
        List<Question> question = questionService.getQuestion(page, 5);
        if(question!=null && !question.isEmpty()){
            return ResultObj.success(question);
        }
        else
            return ResultObj.error();
    }

    /**
     * 计算用户回答后的得分
     */
    @PostMapping("/calcuteResult")
    public ResultObj correct(@RequestBody Map map){
        List<Integer> id = (List<Integer>) map.get("id");
        List<String> answer = (List<String>) map.get("answer");
        Map<String,Object> resultMap = questionService.getResultScore(id, answer);
        String message = null;
        Integer resultScore = (Integer) resultMap.get("score");
        if(resultScore==50)
            message="全部答对了，真棒！为学识渊博的你点个赞！";
        else if(resultScore>=30 && resultScore<=40)
            message="还不错，答对了大部分问题，再仔细学习下，力争下次满分！";
        else
            message="少年，书海满满还需努力哦！";
        resultMap.put("msg",message);
        return ResultObj.success(resultMap);
    }
}
