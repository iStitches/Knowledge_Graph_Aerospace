package com.space.demo.util;

import com.space.demo.entity.visualData.EnergyProvice;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 从csv文件中读取数据并且生成对象
 */
public class ReadCSV {
    public static void readCSV(InputStream inputStream, ArrayList<Object> list, Class cls){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            boolean flag = false;
            ArrayList<String> headerList = new ArrayList();
            while(reader.ready()){
                String line = reader.readLine();
                StringTokenizer st = new StringTokenizer(line,",");
                        //处理当前行数据
                if(st.hasMoreTokens() && flag){
                    String typeName = cls.getSimpleName();
                    //如果文件中存储的是 EnergyProvince类信息
                    if(typeName.equals("EnergyProvice")){
                        String provinceName = st.nextToken();
//                        Float year2019 = Float.valueOf(st.nextToken());
//                        Float year2018 = Float.valueOf(st.nextToken());
                        Float year2017 = Float.valueOf(st.nextToken());
                        Float year2016 = Float.valueOf(st.nextToken());
                        Float year2015 = Float.valueOf(st.nextToken());
                        Float year2014 = Float.valueOf(st.nextToken());
                        Float year2013 = Float.valueOf(st.nextToken());
                        Float year2012 = Float.valueOf(st.nextToken());
                        Float year2011 = Float.valueOf(st.nextToken());
                        Map<String,Float> dataMap = new HashMap();
//                        dataMap.put(headerList.get(1),year2019);
//                        dataMap.put(headerList.get(2),year2018);
                        dataMap.put(headerList.get(1),year2017);
                        dataMap.put(headerList.get(2),year2016);
                        dataMap.put(headerList.get(3),year2015);
                        dataMap.put(headerList.get(4),year2014);
                        dataMap.put(headerList.get(5),year2013);
                        dataMap.put(headerList.get(6),year2012);
                        dataMap.put(headerList.get(7),year2011);
                        list.add(new EnergyProvice(provinceName,dataMap));
                    }
                }
                else{   //添加表头到 List 集合
                    while(st.hasMoreTokens()){
                        headerList.add(st.nextToken());
                    }
                    flag=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader!=null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList list = new ArrayList();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/coal.csv");
        ReadCSV.readCSV(inputStream,list,EnergyProvice.class);
    }
}
