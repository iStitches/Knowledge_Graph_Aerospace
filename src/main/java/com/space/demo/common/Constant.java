package com.space.demo.common;

public class Constant {
    //绕行轨道统计
    public static String REDIS_TRACK_COUNT="aerospace_trackcount";
    //中国发射成功失败
    public static String REDIS_SHOT_SUCCESS_FAILURE="aerospace_shotsuccessandfailure";
    //发射场
    public static String REDIS_SHOT_LOCATION="aerospace_shotlocation";
    //世界发射
    public static String REDIS_WORLD_SHOTSTATUS="aerospace_worldstatus";
    //中国
    public static String REDIS_CHINA_TRANSPORT="aerospace_chinatransport";
    //世界经济
    public static String REDIS_WORLD_ECONOMY="aerospace_worldeconomy";

    public static String REDIS_PROVINCE_COAL_PRODUCT="coalProduct";

    public static String REDIS_PROVINCE_STEEL_PRODUCT="steelProduct";

    public static String REDIS_SPACE_GRAPH_WORDS_TITLE="graphWordsTitle";

    public static String REDIS_SPACE_GRAPH_WORDS_CONTENT="graphWordsContent";

    public static String relationVehicle="属于";
    public static String relationCountry="研制于";
    public static String relationLocation="发射于";
    public static String relationTransport="被运输";
    public static String relationAstronaut="搭载";
    public static String relationDept="建立";
    public static String relationNews="发布";
}
