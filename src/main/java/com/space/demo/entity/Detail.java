package com.space.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 详细航天产品
 */
@Data
@NoArgsConstructor
public class Detail {
    private Integer id;
    private String type;
    private String name;
    private String content;
    private String imgsrc;

    public Detail(String type, String name, String toString, String toString1) {
        this.type=type;
        this.name=name;
        this.content=toString;
        this.imgsrc=toString1;
    }
}
