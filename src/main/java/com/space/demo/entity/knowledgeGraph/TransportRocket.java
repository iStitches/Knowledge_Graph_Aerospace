package com.space.demo.entity.knowledgeGraph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Set;

@Data
@NoArgsConstructor
@Node(labels = "transportrocket")
public class TransportRocket {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "country")
    private String country;

    @Property(name = "weight")
    private String weight;

    @Property(name = "capicity")
    private String capicity;

    @Property(name = "countryImg")
    private String countryImg;

    @Property(name = "height")
    private String height;
}
