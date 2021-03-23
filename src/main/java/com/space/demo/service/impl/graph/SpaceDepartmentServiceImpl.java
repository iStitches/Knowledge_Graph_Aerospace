package com.space.demo.service.impl.graph;

import com.space.demo.dao.graph.CountryDao;
import com.space.demo.dao.graph.SpaceDepartmentDao;
import com.space.demo.entity.knowledgeGraph.Country;
import com.space.demo.entity.knowledgeGraph.SpaceDepartment;
import com.space.demo.service.graph.CountryService;
import com.space.demo.service.graph.SpaceDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SpaceDepartmentServiceImpl implements SpaceDepartmentService {
    @Autowired
    SpaceDepartmentDao dao;

    @Override
    public List<SpaceDepartment> getNodesByName(String name) {
        String tmp = ".*"+name+".*";
        return dao.getNodesByBlurName(tmp);
    }

    @Override
    public SpaceDepartment getNodeByName(String name) {
        return dao.findByCNname(name);
    }
}
