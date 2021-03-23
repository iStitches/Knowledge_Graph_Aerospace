package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.Vehicle;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface VehicleDao extends Neo4jRepository<Vehicle,Long> {
    @Query("MATCH (m:vehicle) where m.name=~{name} return m")
    List<Vehicle> getNodesByBlurName(@Param("name") String name);

    Vehicle findByName(@Param("name")String name);
}
