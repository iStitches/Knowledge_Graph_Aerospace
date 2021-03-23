package com.space.demo.entity.visualData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnergyProvice {
    private String province;
    private Map<String,Float> data;
}
