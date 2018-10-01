package com.bonc.medicine.service.thumb;

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


}
