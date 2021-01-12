package com.space.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forecast implements Serializable {
    private String year;
    private String month;
    private String day;
    private String week;
    private String time;
    private String eventTitle;
    private String hoverTime;    //鼠标悬浮显示的时间
    private String hoverImgsrc;  //鼠标悬浮显示的图片
    private String hoverContent; //鼠标悬浮显示的内容
    private String url;
}
