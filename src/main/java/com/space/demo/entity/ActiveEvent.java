package com.space.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航天知识最新动态
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveEvent {
    private Integer id;
    private Integer typeId;           //对应的分类
    private String eventTitle;
    private String eventContent;
    private String eventImg;

    public ActiveEvent(Integer typeId, String eventTitle, String eventContent, String eventImg) {
        this.typeId = typeId;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImg = eventImg;
    }
}
