package com.space.demo.service.impl.graph;

import com.space.demo.dao.graph.CountryDao;
import com.space.demo.dao.graph.TransportRocketDao;
import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.TransportRocket;
import com.space.demo.service.graph.CountryService;
import com.space.demo.service.graph.TransportRocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransportRocketServiceImpl implements TransportRocketService {
    @Autowired
    TransportRocketDao dao;

    @Override
    public List<TransportRocket> getNodesByName(String name) {
        String tmp = ".*"+name+".*";
        return dao.getNodesByBlurName(tmp);
    }

    @Override
    public TransportRocket getNodeByName(String name) {
        return dao.findByName(name);
    }
}
