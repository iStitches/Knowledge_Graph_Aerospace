package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface AstronautDao extends Neo4jRepository<Astronaut,Long> {
    @Query("MATCH (m:astronaut) where m.name=~{name} return m")
    List<Astronaut> getNodesByBlurName(@Param("name") String name);

    Astronaut findByName(@Param("name")String name);
}
