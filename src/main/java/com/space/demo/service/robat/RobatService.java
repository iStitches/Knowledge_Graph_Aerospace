package com.space.demo.service.robat;

import java.util.List;
import java.util.Map;

public interface RobatService {
    /**
     * 返回问题结果
     * @param question
     * @return
     */
    public String returnAnswer(String question);
    /**
     * 回答问题
     * @param question    用户提出的问题
     * @param text        语料库中的问题
     * @param answer      语料库中的回答
     * @return
     */
    public String getAnswer(String question, List<String> text, List<String> answer);

    /**
     * 匹配用户问题和语料库中的问题，制作共同分词返回最大相似度的问题
     * @param question
     * @param text
     * @return
     */
    public Map<String,String> match(String question,List<String> text);

    /**
     * 生成匹配向量
     * @param text     待统计的向量
     * @param wordSet  标准
     * @return
     */
    public List<Integer> wordVector(List<String> text,List<String> wordSet);

    /**
     * 计算相似度----计算 cos角
     * @param x
     * @param y
     * @return
     */
    public Float cosVector(List<Integer> x,List<Integer> y);
}
