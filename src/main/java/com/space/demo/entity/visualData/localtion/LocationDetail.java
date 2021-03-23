package com.space.demo.entity.visualData.localtion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDetail {
    private Integer id;
    private String rocketType;
    private String carriage;
    private String shotTime;
    private Integer workNumber;
    private String targetTrack;
    private String result;
    private String locationName;

    public LocationDetail(String rocketType, String carriage, String shotTime, Integer workNumber, String targetTrack, String result, String locationName) {
        this.rocketType = rocketType;
        this.carriage = carriage;
        this.shotTime = shotTime;
        this.workNumber = workNumber;
        this.targetTrack = targetTrack;
        this.result = result;
        this.locationName = locationName;
    }
}
