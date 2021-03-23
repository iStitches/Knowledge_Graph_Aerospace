package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;

import java.util.List;

public interface AstronautService {
    List<Astronaut> getNodesByName(String name);

    Astronaut getNodeByName(String name);
}
