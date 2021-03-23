package com.space.demo.dao.locationDetail;

import com.space.demo.entity.visualData.localtion.LocationDetail;

import java.util.List;

public interface LocationDetailDao {
    void batchInsert(List<LocationDetail> locationDetailList);

    List<LocationDetail> getAllByName(String name);
}
