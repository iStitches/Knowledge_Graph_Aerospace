package com.space.demo.service.impl.robat;

import com.space.demo.service.robat.RobatService;
import com.space.demo.util.JSONUtil;
import com.space.demo.util.WordParser;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class RobatServiceImpl implements RobatService {
    @Override
    public String returnAnswer(String question){
        List<String> text=null,answer=null;
        // String path = RobatServiceImpl.class.getClassLoader().getResource("/config/answer1.json").getPath();
        InputStream fileInputStream = RobatServiceImpl.class.getResourceAsStream("/config/answer1.json");
        String jsonStr=JSONUtil.readJsonFile(fileInputStream);
        text = JSONUtil.parseJSONArray(jsonStr, "question");
        answer = JSONUtil.parseJSONArray(jsonStr,"answer");
        return getAnswer(question, text, answer);
    }

    @Override
    public String getAnswer(String question, List<String> text, List<String> answer) {
        Map<String,String> resMap=match(question,text);
        Integer maxIndex=Integer.parseInt(resMap.get("maxIndex"));
        Float maxScore=Float.parseFloat(resMap.get("maxScore"));

        //根据匹配程度判断问题是否有答案
        if(maxScore <= 0.4){
             return "我好像不明白";
        }
        else if(maxScore>0.4 && maxScore<0.7){
            return "我猜您想问的是:\n"+text.get(maxIndex)+"\n"+answer.get(maxIndex);
        }
        else{
            return answer.get(maxIndex);
        }
    }

    @Override
    public Map<String, String> match(String question, List<String> text) {
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

    //生成text根据wordSet为标准生成的匹配向量
    @Override
    public List<Integer> wordVector(List<String> text, List<String> wordSet) {
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

    @Override
    public Float cosVector(List<Integer> x, List<Integer> y) {
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
        RobatServiceImpl service=new RobatServiceImpl();
        Scanner sc = new Scanner(System.in);
        String question = null;
        while((question=sc.nextLine())!=null){
            String str = service.returnAnswer(question);
            System.out.println(str);
        }
    }
}
