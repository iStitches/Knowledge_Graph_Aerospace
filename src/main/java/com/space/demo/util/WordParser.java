package com.space.demo.util;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * 分词器
 */
public class WordParser {
    private static final Logger LOG= LoggerFactory.getLogger(WordParser.class);

//    static{
//        //重新配置word分词的自定义配置文件
//        String confpath=WordParser.class.getResource("/config/word.local.conf").getPath();
//        if(new File(confpath).exists()){
//            WordConfTools.forceOverride(confpath);
//            LOG.info("Word分词的自定义配置文件"+confpath);
//        }
//        else{
//            LOG.info("不存在Word分词的自定义配置文件");
//        }
//    }

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

    public static void main(String[] args) {
        List<String> words=parseWithoutStopWords("第一个登上月球的人是美国宇航员尼尔•奥尔登•阿姆斯特朗 。1969年7月20日，阿姆斯特朗和巴兹•奥尔德林乘“阿波罗11号”飞船在月球表面着陆 。");
        System.out.println(words.toString());
    }
}
