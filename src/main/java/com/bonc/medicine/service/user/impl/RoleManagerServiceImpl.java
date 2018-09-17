package com.bonc.medicine.service.user.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.user.RoleManagerMapper;
import com.bonc.medicine.service.user.RoleManagerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: medicine
 * @description:
 * @author: hejiajun
 * @create: 2018-09-15 21:50
 **/
@Repository
public class RoleManagerServiceImpl implements RoleManagerService {

    private String key = "succeed";

    @Autowired
    private RoleManagerMapper roleManagerMapper;


    @Override
    @Transactional
    public Map<String, Object> createNewRole(Map<String, String> map) {

        int row = roleManagerMapper.createNewRole(map);
        if(row < 1){
            throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }

        Map reMap = new HashMap();
        reMap.put(key, row);

        return reMap;
    }

    @Override
    public List<Map<String, Object>> getRolesByCondition(Map<String, String> map) {

        PageHelper.startPage(Integer.parseInt(map.get("pageIndex")), Integer.parseInt(map.get("pageSize")));
        List<Map<String, Object>> reList = roleManagerMapper.getRolesByCondition(map);

        if(null == reList || reList.size() < 1){
            throw  new MedicineRuntimeException(ResultEnum.NO_CONTENT);
        }

        return reList;
    }

    @Override
    @Transactional
    public Map<String, Object> removeRole(String id) {

        int row = roleManagerMapper.removeRole(id);
        if (row < 1){
            throw  new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }
        if(row > 1){
            throw  new MedicineRuntimeException(ResultEnum.ERROE);
        }

        Map reMap = new HashMap();
        reMap.put(key, row);

        return reMap;
    }

    @Override
    public Map<String, Object> updateRoleInfo(Map<String, String> map) {

        int row = roleManagerMapper.updateRoleInfo(map);

        if (row < 1){
            throw  new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }
        if(row > 1){
            throw  new MedicineRuntimeException(ResultEnum.ERROE);
        }
        Map reMap = new HashMap();
        reMap.put(key, row);

        return reMap;
    }

    @Override
    public Map<String, Object> updateRolePermissions() {
        return null;
    }
}
