package com.space.demo.service.graph;

import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.SpaceNews;

import java.util.List;

public interface SpaceNewsService {
    List<SpaceNews> getNodesByTitle(String title);

    SpaceNews getNodeByNewsId(int newsid);
}
