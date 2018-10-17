package com.bonc.medicine.service.thumb.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.adapter.JedisAdapter;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.thumb.ViewNumberMapper;
import com.bonc.medicine.service.thumb.ThumbService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.ViewNumberKeyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description:
 * @author: hejiajun
 * @create: 2018-09-09 19:24
 **/
@Repository
public class ViewNumberServiceImpl implements ViewNumberService {

    @Autowired
    private ViewNumberMapper viewNumberMapper;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Autowired
    private ThumbService thumbService;


    @Override
    public Map<String, Object> queryViewNumber(Map<String, String> map) {
        String objectId = map.get("objectId");
        String objectType = map.get("objectType");
        boolean exists = jedisAdapter.exists(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));
        Map<String, Object> reMap = new HashMap<>();
        String key = "viewNumber";
        if (exists) {
            String viewNumber = jedisAdapter.get(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));
            jedisAdapter.expire(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));//12h
            reMap.put(key, viewNumber);
            return reMap;
        } else {
            synchronized (reMap) {
                List<Map<String, Object>> list = viewNumberMapper.queryViewNumber(map);
                if (null == list || list.size() < 1 || null == list.get(0) || null == list.get(0).get("view_num")) {
                    reMap.put(key, "0");
                    return reMap;
                }

                jedisAdapter.set(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType), list.get(0).get("view_num") + "");
                jedisAdapter.expire(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));//12h
                reMap.put(key, list.get(0).get("view_num"));
                return reMap;
            }
        }
    }

    public boolean queryExistsObject(Map<String, String> map) {
        List<Map<String, Object>> list = viewNumberMapper.queryExistsObject(map);
        if (list.size() < 1 || null == list.get(0) || null == list.get(0).get("id")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Map<String, Object> addOrUpdateViewNumberCord(Map<String, String> map) {
        if (StringUtils.isEmpty(map.get("viewNumber"))) {
            map.put("viewNumber", "1");
        }

        String objectId = map.get("objectId");
        String objectType = map.get("objectType");
        boolean exists = queryExistsObject(map);
        if (exists) {
            int row = viewNumberMapper.updateViewNumber(map);
            if (row < 1) {
                throw new MedicineRuntimeException(ResultEnum.ERROE);
            }
            boolean existsKeyinRedis = jedisAdapter.exists(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));
            if (existsKeyinRedis) {
                jedisAdapter.del(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));
                return queryViewNumber(map);
            }
            return queryViewNumber(map);
        } else {
            int row = viewNumberMapper.addViewNumberCord(map);
            boolean existsKeyinRedis = jedisAdapter.exists(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));
            if (existsKeyinRedis) {
                jedisAdapter.del(ViewNumberKeyUtil.getViewNumberKey(objectId, objectType));
                return queryViewNumber(map);
            }
            return queryViewNumber(map);
        }
    }

    //videoType ：必须--1： 线下培训  2： 直播  0：视频
    public List<Map<String, Object>> videoDetailNumberStatistical(String videoIds, String videoType){
        if (StringUtils.isBlank(videoIds) || StringUtils.isBlank(videoType)){

            //如果参数不全就返回null；如果在调用该接口的时候
            return new ArrayList<>();
        }
        String [] idArrays = videoIds.split(",");
        if (idArrays == null || idArrays.length < 1){
            return new ArrayList<>();
        }


        Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("type", videoType);
        queryParamMap.put("videoIds", videoIds);
        if (StringUtils.equals("1", videoType)){
            queryParamMap.put("table" , "train_offline");
        }else if (StringUtils.equals("2", videoType)){
            queryParamMap.put("table" , "train_live");
        }
        List<Map<String, Object>> reQueryList = new ArrayList<>();
        //如果没有预约数的情况下 初始化定义的reQueryList对象将id放在里面的map中
        for (String inIds : idArrays){
            Map<String, Object> mmappp = new HashMap<>();
            mmappp.put("id" , inIds);
            reQueryList.add(mmappp);
        }

        // key appoint_number
        if (!StringUtils.equals("0", videoType)){
            List<Map<String, Object>> queryResultMap =  viewNumberMapper.videoDetailNumberStatistical(queryParamMap);

            if (queryResultMap != null && queryResultMap.size() != 0 && null != queryResultMap.get(0)
                    && null != queryResultMap.get(0).get("id")){
                reQueryList = queryResultMap;
            }
        }

        // 将别人定的TYPE和观看数里面的type对应起来
        if (!StringUtils.equals("1", videoType)){

            Map<String, String> paramaMap = new HashMap<>();
            if (StringUtils.equals("0", videoType)){
                paramaMap.put("objectType", "4");
            }else if (StringUtils.equals("2", videoType)){
                paramaMap.put("objectType", "8");
            }

            for (Map<String, Object> onlyIdMap : reQueryList) {
                paramaMap.put("objectId", onlyIdMap.get("id") + "");
                Map<String, Object> viewNumberMap = queryViewNumber(paramaMap);
                onlyIdMap.put("viewNumber", viewNumberMap.get("viewNumber"));

                Map<String, String> param = new HashMap<>();
                param.put("type", paramaMap.get("objectType"));
                param.put("acceptThumbId", onlyIdMap.get("id") + "");
                Map<String, Object> thumgNumberMap = thumbService.thumbNumber(param);
                // thumbNumber
                onlyIdMap.put("thumbNumber", thumgNumberMap.get("thumbNumber"));
                onlyIdMap.put("commentNumber", "faker"); // 这是伪代码
            }
        }

        // TODO 线下培训的评论回复还没有

        return reQueryList;
    }
}
