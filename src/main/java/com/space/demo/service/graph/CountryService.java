package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.Country;

import java.util.List;

public interface CountryService {
    List<Country> getNodesByName(String name);

    Country getNodeByName(String name);
}
