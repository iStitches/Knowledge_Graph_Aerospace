package com.space.demo.entity.visualData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChinaTransport {
    private String rocketName;
    private Integer successCount;
    private Integer vehicleCount;
    private Integer allCount;
}
