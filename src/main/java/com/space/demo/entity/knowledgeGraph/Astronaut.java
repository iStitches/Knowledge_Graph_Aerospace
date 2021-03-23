package com.space.demo.entity.knowledgeGraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@Node(labels = "astronaut")
public class Astronaut implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "birth")
    private String birth;

    @Property(name = "countryImg")
    private String countryImg;

    @Property(name = "firstExp")
    private String firstExp;

    @Property(name = "country")
    private String country;

    @Property(name = "des")
    private String des;

    @Property(name = "astImg")
    private String astImg;
}
