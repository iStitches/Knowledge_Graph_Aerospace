package com.space.demo.service.impl.graph;

import com.space.demo.dao.graph.CategoryDao;
import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.service.graph.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao dao;

    @Override
    public List<Category> getNodesByName(String name) {
        String tmp = ".*"+name+".*";
        return dao.getNodesByBlurName(tmp);
    }

    @Override
    public Category getNodeByName(String name) {
        return dao.findByName(name);
    }
}
