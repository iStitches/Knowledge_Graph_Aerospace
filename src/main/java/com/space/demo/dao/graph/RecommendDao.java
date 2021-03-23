package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.Recommend;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface RecommendDao extends Neo4jRepository<Recommend,Long> {
    @Query("MATCH (m:recommend) where m.name=~{name} return m")
    List<Recommend> getNodesByBlurName(@Param("name") String name);

    Recommend findByName(@Param("name")String name);
}
