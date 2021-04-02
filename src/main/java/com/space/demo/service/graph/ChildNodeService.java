package com.space.demo.service.graph;

import com.space.demo.common.ChildNode;

import java.util.List;


public interface ChildNodeService {
    //批量插入子节点
    void batchInsertChildNodes(List<ChildNode> childNodeList);

    //查询出所有子节点的名称
    List<String> getAllNodesName();

    //根据子节点名称查询父结点下所有的子节点
    List<ChildNode> getAllChildNodes(String name);
}
