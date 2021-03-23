package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.TransportRocket;

import java.util.List;

public interface TransportRocketService {
    List<TransportRocket> getNodesByName(String name);

    TransportRocket getNodeByName(String name);
}
