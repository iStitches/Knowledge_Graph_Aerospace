package com.space.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.space.demo.common.Constant;
import com.space.demo.common.ResultObj;
import com.space.demo.entity.visualData.*;
import com.space.demo.entity.visualData.localtion.LocationDetail;
import com.space.demo.entity.visualData.localtion.ShotLocation;
import com.space.demo.service.visual.LocationDetailService;
import com.space.demo.util.JSONUtil;
import com.space.demo.util.ReadCSV;
import com.space.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/space/visual")
public class VisualData {
    @Autowired
    RedisUtil util;
    @Autowired
    LocationDetailService locationDetailService;

    /**
     * 获取 绕行轨道总次数
     * @return
     */
    @GetMapping("/chinaTrackCount")
    public ResultObj getTrackData(){
        List<TrackCount> res = new ArrayList<>();
        String json = util.getValue(Constant.REDIS_TRACK_COUNT);
        JSONArray jsonArray = JSONUtil.parseJsonToArray(json);
        for(int i=0;i<jsonArray.size();i++)
            res.add(jsonArray.getObject(i, TrackCount.class));
        return ResultObj.success(res);
    }

    /**
     * 获取 发射场发射总次数
     */
    @GetMapping("/chinaLocationCount")
    public ResultObj getLocationData(){
        List<ShotLocation> res = new ArrayList<>();
        String json = util.getValue(Constant.REDIS_SHOT_LOCATION);
        JSONArray jsonArray = JSONUtil.parseJsonToArray(json);
        for(int i=0;i<jsonArray.size();i++)
            res.add(jsonArray.getObject(i,ShotLocation.class));
        return ResultObj.success(res);
    }

    /**
     * 获取 2021年运载火箭发射情况
     */
    @GetMapping("/chinaTransportData")
    public ResultObj getTransportData(){
        List<ChinaTransport> res = new ArrayList<>();
        String json = util.getValue(Constant.REDIS_CHINA_TRANSPORT);
        JSONArray jsonArray = JSONUtil.parseJsonToArray(json);
        for(int i=0;i<jsonArray.size();i++)
            res.add(jsonArray.getObject(i,ChinaTransport.class));
        return ResultObj.success(res);
    }

    /**
     * 获取中国发射记录
     */
    @GetMapping("/chinaShotData")
    public ResultObj getShotData(){
        List<ChinaShotStatue> res = new ArrayList<>();
        String json = util.getValue(Constant.REDIS_SHOT_SUCCESS_FAILURE);
        JSONArray jsonArray = JSONUtil.parseJsonToArray(json);
        for(int i=0;i<jsonArray.size();i++)
            res.add(jsonArray.getObject(i,ChinaShotStatue.class));
        return ResultObj.success(res);
    }

    /**
     * 获取各个国家航天投入
     */
    @GetMapping("/worldEconomy")
    public ResultObj getWorldEconomy(){
        List<CountryGPA> res = new ArrayList<>();
        String json = util.getValue(Constant.REDIS_WORLD_ECONOMY);
        JSONArray jsonArray = JSONUtil.parseJsonToArray(json);
        for(int i=0;i<jsonArray.size();i++)
            res.add(jsonArray.getObject(i,CountryGPA.class));
        return ResultObj.success(res);
    }

    /**
     * 各个国家发射成功率
     */
    @GetMapping("/worldStatue")
    public ResultObj getWorldStatue(){
        List<WorldShotStatue> res = new ArrayList<>();
        String json = util.getValue(Constant.REDIS_WORLD_SHOTSTATUS);
        JSONArray jsonArray = JSONUtil.parseJsonToArray(json);
        for(int i=0;i<jsonArray.size();i++)
            res.add(jsonArray.getObject(i,WorldShotStatue.class));
        return ResultObj.success(res);
    }

    /**
     * 根据发射场名称分页查找发射记录信息
     */
    @GetMapping("/locationShotStatics")
    public ResultObj getLocationShotStatics(@RequestParam(value = "name")String name,
                                            @RequestParam(value = "page")Integer page,
                                            @RequestParam(value = "size",defaultValue = "5")String size){
        PageHelper.startPage(page,Integer.valueOf(size));
        List<LocationDetail> list = locationDetailService.getAllByName(name);
        PageInfo<LocationDetail> pageInfo = new PageInfo<>(list);
        return ResultObj.success(pageInfo);
    }

    /**
     * 持久化煤油产量数据到 Redis
     */
    @GetMapping("/saveCoalToRedis")
    public ResultObj saveCoal(){
        ArrayList list = new ArrayList();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/coal.csv");
        ReadCSV.readCSV(inputStream,list,EnergyProvice.class);
        util.setValue(Constant.REDIS_PROVINCE_COAL_PRODUCT,JSONUtil.parseArrayToJson(list));
        return ResultObj.success(null);
    }

    /**
     * 持久化生钢产量数据到 Redis
     */
    @GetMapping("/saveSteelToRedis")
    public ResultObj saveSteel(){
        ArrayList list = new ArrayList();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/steel.csv");
        ReadCSV.readCSV(inputStream,list,EnergyProvice.class);
        util.setValue(Constant.REDIS_PROVINCE_STEEL_PRODUCT,JSONUtil.parseArrayToJson(list));
        return ResultObj.success(null);
    }

    /**
     * 获得能源产品产量
     * @return
     */
    @GetMapping("/getEnergyProduct/{name}")
    public ResultObj getCoalEnergy(@PathVariable String name){
        String json = null;
        if(name.equals("煤油"))
            json = util.getValue(Constant.REDIS_PROVINCE_COAL_PRODUCT);
        else if(name.equals("生钢"))
            json = util.getValue(Constant.REDIS_PROVINCE_STEEL_PRODUCT);
        ArrayList<EnergyProvice> res = new ArrayList<>();
        JSONArray jsonArray = JSONUtil.parseJsonToArray(json);
        for(int i=0;i<jsonArray.size();i++)
            res.add(jsonArray.getObject(i,EnergyProvice.class));
        return ResultObj.success(res);
    }
}
