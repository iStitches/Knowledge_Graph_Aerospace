package com.space.demo.entity.visualData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldCountry {
    private String country;
    private Integer success;
    private Integer failure;
}
