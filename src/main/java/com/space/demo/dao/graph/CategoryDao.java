package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CategoryDao extends Neo4jRepository<Category,Long> {
    @Query("MATCH (m:category) where m.name=~{name} return m")
    List<Category> getNodesByBlurName(@Param("name") String name);

    Category findByName(@Param("name")String name);
}
