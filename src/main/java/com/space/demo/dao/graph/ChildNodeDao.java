package com.space.demo.dao.graph;

import com.space.demo.common.ChildNode;

import java.util.List;

public interface ChildNodeDao {
    //批量插入
    void batchInsert(List<ChildNode> childNodes);
    //查询所有子节点名称
    List<String> getAllName();
    //根据名称查询相关联的所有子节点信息
    List<ChildNode> getUnionNodes(String name);

}
