package com.space.demo.entity.visualData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChinaShotStatue {
    private Integer year;
    private Integer success;
    private Integer failure;
    private Integer other;
}
