package com.space.demo.entity.knowledgeGraph;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@NoArgsConstructor
@Node(labels = "recommend")
public class Recommend {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "des")
    private String des;

    @Property(name = "origin")
    private String origin;

    @Property(name = "time")
    private String time;

    @Property(name = "pic")
    private String pic;

    @Property(name = "publishLanguage")
    private String publishLanguage;

    @Property(name = "type")
    private String type;

    @Property(name = "point")
    private String point;
}
