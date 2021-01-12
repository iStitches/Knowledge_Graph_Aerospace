package com.space.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class News {
    private List<String> tags;
    private String title;
    private String date;
    private String url;
    private String imgsrc;
    private String describe;

    public News(List<String> tags, String title, String date, String url, String imgsrc, String toString) {
        this.tags=tags;
        this.title=title;
        this.date=date;
        this.url=url;
        this.imgsrc=imgsrc;
        this.describe=toString;
    }
}
