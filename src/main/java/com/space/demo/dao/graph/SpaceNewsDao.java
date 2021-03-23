package com.space.demo.dao.graph;

import com.space.demo.entity.knowledgeGraph.Astronaut;
import com.space.demo.entity.knowledgeGraph.Category;
import com.space.demo.entity.knowledgeGraph.SpaceNews;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface SpaceNewsDao extends Neo4jRepository<SpaceNews,Long> {
    @Query("MATCH (m:spacenews) where m.title=~{name} return m")
    List<SpaceNews> getNodesByBlurName(@Param("name") String name);

    @Query("MATCH (m:spacenews) where m.newsId={newid} return m")
    SpaceNews getNodeById(@Param("newid") Integer newid);

    SpaceNews findByTitle(@Param("title")String title);
}
