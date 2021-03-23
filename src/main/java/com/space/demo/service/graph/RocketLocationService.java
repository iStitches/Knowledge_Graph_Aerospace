package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.entity.knowledgeGraph.RocketLocation;

import java.util.List;

public interface RocketLocationService {
    List<RocketLocation> getNodesByName(String name);

    RocketLocation getNodeByName(String name);
}
