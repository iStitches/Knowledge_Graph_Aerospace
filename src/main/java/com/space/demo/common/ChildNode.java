package com.space.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildNode {
    private Integer id;
    private String name;    //名称
    private Integer size;   //点大小
    private Integer value;  //点击量
    private String url;     //超链接
    private Integer categoryId; //所属分类的序号

    public ChildNode(String name, Integer size, Integer value, String url, Integer categoryId) {
        this.name = name;
        this.size = size;
        this.value = value;
        this.url = url;
        this.categoryId = categoryId;
    }
}
