package com.space.demo.entity.visualData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryGPA {
    private String country;
    private Map<Integer,Float> gpa;
}
