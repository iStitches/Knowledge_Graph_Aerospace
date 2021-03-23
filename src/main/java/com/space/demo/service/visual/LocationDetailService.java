package com.space.demo.service.visual;

import com.space.demo.entity.visualData.localtion.LocationDetail;

import java.util.List;

public interface LocationDetailService {
    void batchInsert(List<LocationDetail> list);

    List<LocationDetail> getAllByName(String name);
}
