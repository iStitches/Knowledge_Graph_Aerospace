package com.space.demo.entity.newsReommend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private int id;
    private String question;
    private int score;
    private String itemA;
    private String itemB;
    private String itemC;
    private String itemD;
    private String answer;

    public Question(String question,String itemA,String itemB,String itemC,String itemD){
        this.question=question;
        this.itemA=itemA;
        this.itemB=itemB;
        this.itemC=itemC;
        this.itemD=itemD;
    }
}
