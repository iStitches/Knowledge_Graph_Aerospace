package com.space.demo.entity.knowledgeGraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Node(labels = "category")
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;
    @Property(name = "des")
    private String des;

    @Relationship(type = "属于",direction = Relationship.Direction.INCOMING)
    @JsonProperty("属于")
    private Set<Vehicle> vehicleSet = new HashSet<>();
}
