package com.space.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomNode {
    private String id;
    private int group;

    public CustomNode(String id){
        this.id=id;
        this.group=1;
    }
}
