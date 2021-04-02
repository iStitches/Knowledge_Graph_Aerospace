package com.space.demo.util;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * 分词器
 */
public class WordParser {
    private static final Logger LOG= LoggerFactory.getLogger(WordParser.class);

    //去除停用词的分词
    public static List<String> parseWithoutStopWords(String str){
        List<Word> words= WordSegmenter.seg(str, SegmentationAlgorithm.MaxNgramScore);
        List<String> res=new ArrayList<>();
        for(int i=0;i<words.size();i++){
            res.add(words.get(i).getText());
        }
        return res;
    }
    //不去除停用词的分词
    public static List<String> parseWithStopWords(String str){
        List<Word> words = WordSegmenter.segWithStopWords(str,SegmentationAlgorithm.MaxNgramScore);
        List<String> res=new ArrayList<>();
        for(int i=0;i<words.size();i++){
            res.add(words.get(i).getText());
        }
        return res;
    }

    //计算结果,相似度大于0.5的才有返回值
    public static String getAnswer(String question, List<String> text) {
        Map<String,String> resMap=match(question,text);
        Integer maxIndex=Integer.parseInt(resMap.get("maxIndex"));
        Float maxScore=Float.parseFloat(resMap.get("maxScore"));

        //根据匹配程度判断问题是否有答案
        if(maxScore <= 0.5){
            return null;
        }
        else{
            return text.get(maxIndex);
        }
    }

    //语料库和问题进行相似度匹配
    public static Map<String, String> match(String question, List<String> text) {
        //将用户提出的问题进行分词----questionWords
        List<String> questionWords = WordParser.parseWithoutStopWords(question);
        //语料库和用户提出的问题的共同分词----wordSets
        Set<String> wordSets = new HashSet<>();
        //L_question: 用户提出问题根据wordSet求出的向量    L_text: 语料库中的问题根据wordSet求出的向量
        List<Integer> L_question = new ArrayList(),L_text=new ArrayList<>();
        List<Float> scoreList = new ArrayList<>();  //相似度大小存储

        for(int i=0;i<text.size();i++){
            //将语料库中问题进行分词
            wordSets.clear();
            List<String> textWords = WordParser.parseWithoutStopWords(text.get(i));
            wordSets.addAll(questionWords);
            wordSets.addAll(textWords);
            L_question = wordVector(questionWords,new ArrayList<>(wordSets));
            L_text = wordVector(textWords,new ArrayList<>(wordSets));
            Float curScore = cosVector(L_question,L_text);
            curScore = curScore==null?0:curScore;
            scoreList.add(curScore);
        }
        Map<String,String> resMap = new HashMap<>();
        Float maxscore = Collections.max(scoreList);
        resMap.put("maxScore",maxscore.toString());
        Integer maxIndex = scoreList.indexOf(maxscore);
        resMap.put("maxIndex",maxIndex.toString());
        return resMap;
    }

    //生成text 根据wordSet为标准生成的匹配向量
    public static List<Integer> wordVector(List<String> text, List<String> wordSet) {
        List<Integer> scoreList=new ArrayList<>();
        for(int i=0;i<wordSet.size();i++)
            scoreList.add(0);
        for(int i=0;i<text.size();i++){
            if(wordSet.contains(text.get(i))){
                int index = wordSet.indexOf(text.get(i));
                scoreList.set(index,scoreList.get(index)+1);
            }
        }
        return scoreList;
    }

    //计算相似度
    public static Float cosVector(List<Integer> x, List<Integer> y) {
        if(x.size()!=y.size()){
            System.out.println("没有匹配的问题");
            return null;
        }
        double result1=0,result2=0,result3=0;
        for(int i=0;i<x.size();i++){
            result1 += x.get(i)*y.get(i);
            result2 += Math.pow(x.get(i),2);
            result3 += Math.pow(y.get(i),2);
        }
        if(result2==0 || result3==0)
            return null;
        else
            return Float.valueOf(String.valueOf(result1/Math.sqrt(result2*result3)));
    }


    public static void main(String[] args) {
        List<String> words=parseWithoutStopWords("第一个登上月球的人是美国宇航员尼尔•奥尔登•阿姆斯特朗 。1969年7月20日，阿姆斯特朗和巴兹•奥尔德林乘“阿波罗11号”飞船在月球表面着陆 。");
        System.out.println(words.toString());
    }
}
