package com.space.demo.service.impl.graph;

import com.space.demo.common.ChildNode;
import com.space.demo.dao.graph.ChildNodeDao;
import com.space.demo.service.graph.ChildNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildNodeServiceImpl implements ChildNodeService {
    @Autowired
    ChildNodeDao dao;

    //批量插入子节点
    @Override
    public void batchInsertChildNodes(List<ChildNode> childNodeList) {
        dao.batchInsert(childNodeList);
    }

    //获取所有子节点名称
    @Override
    public List<String> getAllNodesName() {
        return dao.getAllName();
    }

    //
    @Override
    public List<ChildNode> getAllChildNodes(String name) {
        return dao.getUnionNodes(name);
    }
}
