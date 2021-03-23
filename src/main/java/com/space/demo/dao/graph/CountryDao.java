package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.entity.knowledgeGraph.Country;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CountryDao extends Neo4jRepository<Country,Long> {
    @Query("MATCH (m:country) where m.name=~{name} return m")
    List<Country> getNodesByBlurName(@Param("name") String name);

    Country findByName(@Param("name")String name);
}
