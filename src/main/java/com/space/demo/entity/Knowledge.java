package com.space.demo.entity;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Knowledge implements Serializable {
    private Integer id;
    private String type;
    private String imgsrc;
    private String title;
    private String describe;
    private List<ActiveEvent> activeEvents=new ArrayList<>();   //一条知识分类对应多条最新动态

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<ActiveEvent> getActiveEvents() {
        return activeEvents;
    }

    public void setActiveEvents(List<ActiveEvent> activeEvents) {
        this.activeEvents = activeEvents;
    }
}
