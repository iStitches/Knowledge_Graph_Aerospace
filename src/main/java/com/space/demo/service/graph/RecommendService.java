package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.Recommend;

import java.util.List;

public interface RecommendService {
    List<Recommend> getNodesByName(String name);

    Recommend getNodeByName(String name);
}
