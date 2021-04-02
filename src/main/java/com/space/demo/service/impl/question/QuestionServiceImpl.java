package com.space.demo.service.impl.question;

import com.space.demo.dao.question.QuestionDao;
import com.space.demo.entity.newsReommend.Question;
import com.space.demo.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao dao;

    @Override
    public void addQuestionList(List<Question> questionList) {
        if(questionList.size()>0)
            dao.batchInsert(questionList);
    }

    @Override
    public List<Question> getQuestion(Integer page, Integer size) {
        Integer start = (page-1)*size;
        return dao.getQuestionList(size,start);
    }

    @Override
    public Map<String,Object> getResultScore(List<Integer> idList, List<String> answerList) {
        List<String> correctAnswer = dao.getCorrectAnswer(idList);
        Map<String,Object> resultMap = new HashMap<>();
        int result = 0;
        for(int i=0;i<correctAnswer.size();i++){
            if(correctAnswer.get(i).equals(answerList.get(i)))
                result+=10;
        }
        resultMap.put("correctAnswer",correctAnswer);
        resultMap.put("score",result);
        return resultMap;
    }
}
