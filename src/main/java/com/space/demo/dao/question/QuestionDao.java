package com.space.demo.dao.question;

import com.space.demo.entity.newsReommend.Question;

import java.util.List;

public interface QuestionDao {
    void batchInsert(List<Question> questions);

    List<Question> getQuestionList(Integer offset,Integer limit);

    List<String> getCorrectAnswer(List<Integer> idList);
}
