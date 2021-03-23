package com.space.demo.entity.knowledgeGraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Set;

@Data
@NoArgsConstructor
@Node(labels = "country")
public class Country {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @JsonProperty("建立")
    @Relationship(type = "建于",direction = Relationship.Direction.INCOMING)
    private Set<SpaceDepartment> spaceDepartmentSet;

    private String relationDept = "建立";
}
