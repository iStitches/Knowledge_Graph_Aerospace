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
    private Integer accumulateAll;  //累计发射次数
    private Integer accumulateSuccess;     //成功次数累计
    private Integer accumulateFailure;  //失败次数累计
}
