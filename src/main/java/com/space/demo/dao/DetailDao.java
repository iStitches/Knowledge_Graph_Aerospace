package com.space.demo.dao;

import com.space.demo.entity.Detail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailDao {
    Integer addDetail(Detail detail);
    Integer deleteByName(String name);
    Integer deleteById(Integer id);
    Detail getOne(String name);
    List<Detail> getAll();
}
