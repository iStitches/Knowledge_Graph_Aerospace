package com.space.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomLink {
    private String source;
    private String target;
    private String relationship;
    private int value;

    public CustomLink(String source,String target,String relationship){
        this.source=source;
        this.target=target;
        this.relationship= relationship;
        this.value=2;
    }
}
