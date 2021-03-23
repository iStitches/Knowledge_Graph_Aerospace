package com.space.demo.entity.knowledgeGraph;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@NoArgsConstructor
@Node(labels = "spacenews")
public class SpaceNews {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "newsId")
    private String newsId;

    @Property(name = "newsTime")
    private String newsTime;

    @Property(name = "title")
    private String title;

    @Property(name = "content")
    private String content;

    @Property(name = "views")
    private String views;
}
