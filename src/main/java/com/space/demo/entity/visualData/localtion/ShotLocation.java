package com.space.demo.entity.visualData.localtion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShotLocation implements Serializable {
    private String locationName;
    private int shotCount;
}
