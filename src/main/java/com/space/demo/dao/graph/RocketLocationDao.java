package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.RocketLocation;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface RocketLocationDao extends Neo4jRepository<RocketLocation,Long> {
    @Query("MATCH (m:rocketlocation) where m.CNname=~{name} return m")
    List<RocketLocation> getNodesByBlurName(@Param("name") String name);

    RocketLocation findByCNname(@Param("name")String name);
}
