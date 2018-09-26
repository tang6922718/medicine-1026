package com.bonc.medicine.service.thumb.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.adapter.JedisAdapter;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.thumb.ViewNumberMapper;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ViewNumberKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        if (list.size() < 1 || null == list.get(0).get("id")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Map<String, Object> addOrUpdateViewNumberCord(Map<String, String> map) {
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
}
