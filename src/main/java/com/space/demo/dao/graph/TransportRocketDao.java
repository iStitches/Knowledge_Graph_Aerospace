package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.entity.knowledgeGraph.TransportRocket;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface TransportRocketDao extends Neo4jRepository<TransportRocket,Long> {
    @Query("MATCH (m:transportrocket) where m.name=~{name} return m")
    List<TransportRocket> getNodesByBlurName(@Param("name") String name);

    TransportRocket findByName(@Param("name")String name);
}
