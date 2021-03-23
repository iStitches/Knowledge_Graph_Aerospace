package com.space.demo.entity.knowledgeGraph;

import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Set;

@Data
@NoArgsConstructor
@Node(labels = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "des")
    private String des;

    @Property(name = "vehicleImg")
    private String vehicleImg;

    @Property(name = "shotTime")
    private String shotTime;

    @Property(name = "honor")
    private String honor;

    @JsonProperty("研制于")
    @Relationship(type = "研制于",direction = Relationship.Direction.OUTGOING)
    private Country country;

    @JsonProperty("发射于")
    @Relationship(type = "发射于",direction = Relationship.Direction.OUTGOING)
    private RocketLocation rocketLocation;

    @JsonProperty("被运输")
    @Relationship(type = "被运输",direction = Relationship.Direction.OUTGOING)
    private TransportRocket transportRocket;

    @JsonProperty("搭载")
    @Relationship(type = "搭载",direction = Relationship.Direction.INCOMING)
    private Set<Astronaut> astronautSet;

    private String relationCountry = "研制于";
    private String relatoinLocation = "发射于";
    private String relationTransport = "被运输";
    private String relationAstronaut = "搭载";
}
