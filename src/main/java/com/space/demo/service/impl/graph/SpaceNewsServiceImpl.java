package com.space.demo.service.impl.graph;

import com.space.demo.dao.graph.CountryDao;
import com.space.demo.dao.graph.SpaceNewsDao;
import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.SpaceNews;
import com.space.demo.service.graph.CountryService;
import com.space.demo.service.graph.SpaceNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SpaceNewsServiceImpl implements SpaceNewsService {
    @Autowired
    SpaceNewsDao dao;

    @Override
    public List<SpaceNews> getNodesByTitle(String title) {
        String tmp = ".*"+title+".*";
        return dao.getNodesByBlurName(tmp);
    }

    @Override
    public SpaceNews getNodeByNewsId(int newsid) {
        return dao.getNodeById(newsid);
    }
}
