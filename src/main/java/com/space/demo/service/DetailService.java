package com.space.demo.service;

import com.space.demo.entity.Detail;

public interface DetailService {
    void saveDetailToDB(Detail detail);
    void deleteDetail(String name);
}
