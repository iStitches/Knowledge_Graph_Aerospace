package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getNodesByName(String name);

    Category getNodeByName(String name);
}
