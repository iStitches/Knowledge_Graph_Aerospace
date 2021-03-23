package com.space.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apdplat.word.vector.T;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    /**
     * 读取 .json文件，生成字符串
     * @param filename
     * @return
     */
    public static String readJsonFile(String filename){
        String result="";
        BufferedReader reader=null;
        StringBuilder sb=null;
        try {
            File jsonFile= new File(filename);
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile),"utf-8"));
            sb=new StringBuilder();
            String temp=null;
            while((temp=reader.readLine())!=null){
                sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String readJsonFile(InputStream inputStream){
        String result="";
        BufferedReader reader=null;
        StringBuilder sb=null;
        try {
            reader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            sb=new StringBuilder();
            String temp=null;
            while((temp=reader.readLine())!=null){
                sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    /**
     * 存储json数据到文件
     */
    public static void saveJsonDataToFile(String path,Object jsonStr){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path,true));
            writer.write(jsonStr.toString()+",\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer!=null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 获取json文件中指定属性值的集合
     */
    public static List<String> parseJSONArray(String str,String itemName){
        List<String> res=new ArrayList<>();
//        JSONObject obj=JSON.parseArray(str);
//        JSONArray jsonArray = obj.getJSONArray("question");
        JSONArray jsonArray = JSON.parseArray(str);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject key=(JSONObject) jsonArray.get(i);
            res.add(key.get(itemName).toString());
        }
        return res;
    }

    /**
     * 转换集合为json字符串
     */
    public static String parseArrayToJson(Object obj){
        return JSON.toJSONString(obj);
    }

    /**
     * 将 json 字符串转回集合
     */
    public static JSONArray parseJsonToArray(String json){
        return JSON.parseArray(json);
    }
}
