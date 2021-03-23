package com.space.demo.service.impl.graph;

import com.space.demo.dao.graph.CountryDao;
import com.space.demo.dao.graph.RocketLocationDao;
import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.RocketLocation;
import com.space.demo.service.graph.CountryService;
import com.space.demo.service.graph.RocketLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RocketLocationServiceImpl implements RocketLocationService {
    @Autowired
    RocketLocationDao dao;

    @Override
    public List<RocketLocation> getNodesByName(String name) {
        String tmp = ".*"+name+".*";
        return dao.getNodesByBlurName(tmp);
    }

    @Override
    public RocketLocation getNodeByName(String name) {
        return dao.findByCNname(name);
    }
}
