package com.space.demo.service.impl.visual;

import com.space.demo.dao.locationDetail.LocationDetailDao;
import com.space.demo.entity.visualData.localtion.LocationDetail;
import com.space.demo.service.visual.LocationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationDetailServiceImpl implements LocationDetailService {
    @Autowired
    LocationDetailDao locationDetailDao;

    @Override
    public void batchInsert(List<LocationDetail> list) {
       if(list.size()>0)
           locationDetailDao.batchInsert(list);
    }

    @Override
    public List<LocationDetail> getAllByName(String name) {
        return locationDetailDao.getAllByName(name);
    }

}
