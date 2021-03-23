package com.space.demo.entity.knowledgeGraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Set;

@Data
@NoArgsConstructor
@Node(labels = "spacedepartment")
public class SpaceDepartment {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "CNname")
    private String CNname;

    @Property(name = "ENname")
    private String ENname;

    @Property(name = "country")
    private String country;

    @Property(name = "countryImg")
    private String countryImg;

    @Property(name = "address")
    private String address;

    @Property(name = "des")
    private String des;

    @Property(name = "departmentImg")
    private String departmentImg;

    @Property(name = "internet")
    private String internet;

    @Property(name = "setTime")
    private String setTime;

    @JsonProperty("发布")
    @Relationship(type = "发布",direction = Relationship.Direction.OUTGOING)
    private Set<SpaceNews> newsSet;

    private String relationNews = "发布";
}
