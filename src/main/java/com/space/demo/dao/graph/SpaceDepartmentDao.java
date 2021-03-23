package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.entity.knowledgeGraph.SpaceDepartment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface SpaceDepartmentDao extends Neo4jRepository<SpaceDepartment,Long> {
    @Query("MATCH (m:spacedepartment) where m.CNname=~{name} return m")
    List<SpaceDepartment> getNodesByBlurName(@Param("name") String name);

    SpaceDepartment findByCNname(@Param("name")String name);
}
