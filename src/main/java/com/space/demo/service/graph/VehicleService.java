package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getNodesByName(String name);

    Vehicle getNodeByName(String name);
}
