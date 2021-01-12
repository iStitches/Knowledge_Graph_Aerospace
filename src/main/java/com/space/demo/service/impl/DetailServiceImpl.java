package com.space.demo.service.impl;

import com.space.demo.dao.DetailDao;
import com.space.demo.entity.Detail;
import com.space.demo.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl implements DetailService {
    @Autowired
    DetailDao detailDao;

    @Override
    public void saveDetailToDB(Detail detail) {
        Integer change=detailDao.addDetail(detail);
        if(change==0)
            System.out.println("存储航天技术信息失败，请检查！");
        else{
            System.out.println("存储成功！");
        }
    }

    @Override
    public void deleteDetail(String name) {

    }
}
