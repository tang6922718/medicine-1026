package com.bonc.medicine.service.thumb;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description:
 * @author: hejiajun
 * @create: 2018-09-09 19:23
 **/
public interface ViewNumberService {

    public Map<String, Object> queryViewNumber(Map<String, String> map);


    /**
    * @Description:
    * @Param: [map] objectId；objectType 动态：0，视频：4 ；资讯：2；商品：3；文章：5；求购：9
     * ；viewNumber默认1
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: hejiajun
    * @Date: 2018/9/30
    */
    public Map<String, Object> addOrUpdateViewNumberCord(Map<String, String> map);

    /**
    * @Description:获取直播、视频、线下培训 什么数什么数等的统计接口
    * @Param: [videoIds:必须--可以传入多个 多个用“，”分开,
     *         videoType ：必须--1 线下培训  2 直播  0：视频 ]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *     返回 预约数；观看数；互动数；点赞数；
     *
     *     如果参数不全就返回null；如果在调用该接口的时候先判断返回值是不是null
    * @Author: hejiajun
    * @Date: 2018/10/1
    */
    public List<Map<String, Object>> videoDetailNumberStatistical(String videoIds, String videoType);


}
