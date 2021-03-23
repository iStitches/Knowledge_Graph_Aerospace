package com.space.demo.service.impl.graph;

import com.space.demo.dao.graph.CountryDao;
import com.space.demo.dao.graph.RecommendDao;
import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.Recommend;
import com.space.demo.service.graph.CountryService;
import com.space.demo.service.graph.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    RecommendDao dao;

    @Override
    public List<Recommend> getNodesByName(String name) {
        String tmp = ".*"+name+".*";
        return dao.getNodesByBlurName(tmp);
    }

    @Override
    public Recommend getNodeByName(String name) {
        return dao.findByName(name);
    }
}
