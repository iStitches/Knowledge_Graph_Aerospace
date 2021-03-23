package com.space.demo.entity.knowledgeGraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Set;

@Data
@NoArgsConstructor
@Node(labels = "rocketlocation")
public class RocketLocation {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "CNname")
    private String CNname;

    @Property(name = "ENname")
    private String ENname;

    @Property(name = "country")
    private String country;

    @Property(name = "belong")
    private String belong;

    @Property(name = "location")
    private String location;

    @Property(name = "inventTime")
    private String inventTime;

    @Property(name = "locationImg")
    private String locationImg;

    @Property(name = "countryImg")
    private String countryImg;
}
