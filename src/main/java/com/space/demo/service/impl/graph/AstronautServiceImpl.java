package com.space.demo.service.impl.graph;

import com.space.demo.dao.graph.AstronautDao;
import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.service.graph.AstronautService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AstronautServiceImpl implements AstronautService {
    @Autowired
    AstronautDao dao;

    @Override
    public List<Astronaut> getNodesByName(String name) {
        String tmp = ".*"+name+".*";
        return dao.getNodesByBlurName(tmp);
    }

    @Override
    public Astronaut getNodeByName(String name) {
        return dao.findByName(name);
    }
}
