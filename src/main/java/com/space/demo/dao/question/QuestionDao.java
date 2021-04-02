package com.space.demo.dao.question;

import com.space.demo.entity.newsReommend.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionDao {
    void batchInsert(List<Question> questions);

    List<Question> getQuestionList(@Param(value = "limit") Integer limit,@Param(value = "offset") Integer offset);

    List<String> getCorrectAnswer(List<Integer> idList);
}
