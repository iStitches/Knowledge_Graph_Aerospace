package com.space.demo.entity.visualData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldShotStatue {
    private String year;
    private List<WorldCountry> shotStatue;
}
