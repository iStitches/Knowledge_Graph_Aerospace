package com.space.demo.controller;

import com.space.demo.common.*;
import com.space.demo.entity.knowledgeGraph.*;
import com.space.demo.service.graph.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/space/graph")
public class KnowledgeGraphController {
    @Autowired
    AstronautService astronautService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CountryService countryService;
    @Autowired
    RecommendService recommendService;
    @Autowired
    RocketLocationService rocketLocationService;
    @Autowired
    SpaceDepartmentService spaceDepartmentService;
    @Autowired
    SpaceNewsService spaceNewsService;
    @Autowired
    TransportRocketService transportRocketService;
    @Autowired
    VehicleService vehicleService;

    /**
     * 查询结点+关系
     * @param nodeName
     * @param depth
     * @param maxNums
     * @return
     */
    @GetMapping("/nodeList")
    public ResultObj getRelationAnswer(@RequestParam(value = "nodeName") String nodeName,
                                       @RequestParam(value = "depth") int depth,
                                       @RequestParam(value = "maxNums") int maxNums){

         Set<CustomLink> linkList = new HashSet<>();          //结果关系集合
         Set<CustomNode> nodeList = new HashSet<>();          //结果结点集合
         LinkedList<Object> childNodes = new LinkedList<>();     //队列
         Set<Object> midVarbs = new HashSet<>();              //临时存储
         depth = depth>=4?4:depth;
         int allNums = 0,perlevelNums=maxNums/depth;
         int vehicleCount=0,countryCount=0,locationCount=0,transportCount=0,astronautCount=0,departmentCount=0,newsCount=0;
         switch (depth){
             case 1:perlevelNums=maxNums;break;
             case 2:perlevelNums=maxNums/4;break;
             case 3:perlevelNums=maxNums/5;break;
             case 4:perlevelNums=maxNums/5;break;
//             case 5:perlevelNums=maxNums/3;break;
         }
         for(int i=1;i<=depth;i++){
             //如果是分类(第一层   包括分类+航天器)
             if(linkList.size() == 0){
                 Category rootNode = categoryService.getNodeByName(nodeName);
                 nodeList.add(new CustomNode(rootNode.getName(),0));
                 allNums++;
                 Set<Vehicle> vehicleSet = rootNode.getVehicleSet();
                 for(Vehicle v:vehicleSet){
                     if(vehicleCount>perlevelNums)
                         break;
                     vehicleCount++;
                     childNodes.offer(v);
                     //每添加一个关系同时添加对应的两个节点
                     linkList.add(new CustomLink(v.getName(),rootNode.getName(), Constant.relationVehicle));
                     nodeList.add(new CustomNode(v.getName(),1));
                     allNums++;
                 }
             }
             else{
                 while(!childNodes.isEmpty()){
                     Object obj = childNodes.poll();
                     //如果这一层是航天器（第二层   包括航天器+国家+发射场+运输火箭+航天员）
                     if(obj instanceof Vehicle){
                             String vehicleName = ((Vehicle) obj).getName();
//                             nodeList.add(new CustomNode(vehicleName,1));
//                             allNums++;
                             Vehicle tmpNode = vehicleService.getNodeByName(vehicleName);
                             Country tmpCountry = tmpNode.getCountry();
                             RocketLocation tmpLocation = tmpNode.getRocketLocation();
                             TransportRocket tmpTransport = tmpNode.getTransportRocket();
                             Set<Astronaut> tmpAst = tmpNode.getAstronautSet();
                             //添加进关系集合、同时添加国家节点
                             if(tmpCountry != null){
                                 if(countryCount<perlevelNums){
                                     linkList.add(new CustomLink(vehicleName,tmpCountry.getName(),Constant.relationCountry));
                                     nodeList.add(new CustomNode(tmpCountry.getName(),2));
                                     allNums++;
                                     midVarbs.add(tmpCountry);
                                     countryCount++;
                                 }
                             }
                             if(tmpLocation != null){
                                 if(locationCount<perlevelNums){
                                     linkList.add(new CustomLink(vehicleName,tmpLocation.getCNname(),Constant.relationLocation));
                                     nodeList.add(new CustomNode(tmpLocation.getCNname(),3));
                                     allNums++;
                                     locationCount++;
                                 }
                             }
                             if(tmpTransport != null){
                                 if(transportCount<perlevelNums*2){
                                     linkList.add(new CustomLink(vehicleName,tmpTransport.getName(),Constant.relationTransport));
                                     nodeList.add(new CustomNode(tmpTransport.getName(),4));
                                     allNums++;
                                     transportCount++;
                                 }
                             }
                             if(tmpAst != null)
                                 for(Astronaut ast: tmpAst){
                                     if(astronautCount>perlevelNums)
                                         break;
                                     linkList.add(new CustomLink(ast.getName(),vehicleName,Constant.relationAstronaut));
                                     nodeList.add(new CustomNode(ast.getName(),5));
                                     astronautCount++;
                                     allNums++;
                                 }
                     }
                     //如果这一层是国家（第三层  包括航天机构）
                     else if(obj instanceof Country){
                         String countryName = ((Country) obj).getName();
//                         CustomNode node = new CustomNode(countryName,2);
//                         nodeList.add(node);
//                         allNums++;
                         Country tmpCountry = countryService.getNodeByName(countryName);
                         Set<SpaceDepartment> spaceDepartmentSet = tmpCountry.getSpaceDepartmentSet();
                         if(spaceDepartmentSet != null && !spaceDepartmentSet.isEmpty())
                             for(SpaceDepartment dept: spaceDepartmentSet){
                                 if(departmentCount>perlevelNums)
                                     break;
                                 linkList.add(new CustomLink(countryName,dept.getCNname(),Constant.relationDept));
                                 nodeList.add(new CustomNode(dept.getCNname(),6));
                                 allNums++;
                                 midVarbs.add(dept);
                                 departmentCount++;
                         }
                     }
                     //如果这一层是航天机构（第四层  包括航天新闻）
                     else if(obj instanceof SpaceDepartment){
                         String deptName = ((SpaceDepartment) obj).getCNname();
//                         nodeList.add(new CustomNode(deptName,6));
//                         allNums++;
                         SpaceDepartment tmpDept = spaceDepartmentService.getNodeByName(deptName);
                         Set<SpaceNews> newsSet = tmpDept.getNewsSet();
                         if(newsSet != null)
                             for(SpaceNews news: newsSet){
                                 if(newsCount>perlevelNums*2)
                                     break;
                                 linkList.add(new CustomLink(deptName,news.getTitle(),Constant.relationNews));
                                 nodeList.add(new CustomNode(news.getTitle(),7));
                                 allNums++;
                                 newsCount++;
                             }
                     }
                 }
                 childNodes.addAll(midVarbs);
                 midVarbs.clear();
             }
         }
        System.out.println(perlevelNums);
        System.out.println("总个数"+allNums);
        System.out.println("航天器个数："+vehicleCount);
        System.out.println("国家个数："+countryCount);
        System.out.println("发射场个数："+locationCount);
        System.out.println("运输火箭个数："+transportCount);
        System.out.println("航天员个数："+astronautCount);
        System.out.println("航天机构个数："+departmentCount);
        System.out.println("航天新闻个数："+newsCount);
         Map<String,Set> resMap = new HashMap<>();
         resMap.put("nodes",nodeList);
         resMap.put("links",linkList);
         return ResultObj.success(resMap);
    }

    /**
     * 查询单个结点信息
     * @param name
     * @param tag
     * @return
     */
    @GetMapping("/oneNode")
    public ResultObj getOneNodeByName(@RequestParam(value = "name")String name,
                                      @RequestParam(value = "tag")int tag){
        Object obj = null;
        switch(tag){
            case 0:obj = categoryService.getNodeByName(name);break;
            case 1:obj = vehicleService.getNodeByName(name);break;
            case 2:obj = countryService.getNodeByName(name);break;
            case 3:obj = rocketLocationService.getNodeByName(name);break;
            case 4:obj = transportRocketService.getNodeByName(name);break;
            case 5:obj = astronautService.getNodeByName(name);break;
            case 6:obj = spaceDepartmentService.getNodeByName(name);break;
            case 7:obj = spaceNewsService.getNodesByTitle(name);break;
        }
        if(obj != null)
            return ResultObj.success(obj);
        else
            return ResultObj.error(CommonEnum.NOT_FOUND.getResultCode(),"未找到对应结点数据");
    }
}
