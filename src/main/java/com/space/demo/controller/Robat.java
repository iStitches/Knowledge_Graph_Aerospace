package com.space.demo.controller;

import com.space.demo.common.ResultObj;
import com.space.demo.service.robat.RobatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/space/robat")
public class Robat {
    @Autowired
    RobatService robatService;


    @GetMapping("/query")
    public ResultObj answerQuestion(@RequestParam String questionStr){
        String answer = robatService.returnAnswer(questionStr);
        return ResultObj.success(answer);
    }
}
