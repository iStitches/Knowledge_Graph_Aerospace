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
@AllArgsConstructor
public class Detail {
    private String type;
    private String name;
    private String content;
    private String imgsrc;
}
