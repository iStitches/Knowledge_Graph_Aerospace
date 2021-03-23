package com.space.demo.service.question;

import com.space.demo.entity.newsReommend.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    void addQuestionList(List<Question> questionList);

    List<Question> getQuestion(Integer page,Integer size);

    Map<String,Object> getResultScore(List<Integer> idList, List<String> answerList);
}
