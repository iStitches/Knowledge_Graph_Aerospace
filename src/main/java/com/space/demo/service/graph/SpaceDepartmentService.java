package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.SpaceDepartment;

import java.util.List;

public interface SpaceDepartmentService {
    List<SpaceDepartment> getNodesByName(String name);

    SpaceDepartment getNodeByName(String name);
}
