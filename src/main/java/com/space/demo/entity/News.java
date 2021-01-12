package com.space.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private List<String> tags;
    private String title;
    private String date;
    private String url;
    private String imgsrc;
    private String describe;
}
