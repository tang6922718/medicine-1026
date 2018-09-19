package com.bonc.medicine.service.user.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.user.OperatorManagementMapper;
import com.bonc.medicine.service.user.OperatorManagementService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: medicine
 * @description:
 * @author: hejiajun
 * @create: 2018-09-19 16:31
 **/
@Repository
public class OperatorManagementServiceImpl implements OperatorManagementService {

    @Autowired
    private OperatorManagementMapper operatorManagementMapper;

    @Override
    public List<Map<String, Object>> queryOperaterTable(Map<String, String> map) {
        PageHelper.startPage(Integer.valueOf(map.get("pageIndex")), Integer.valueOf(map.get("pageSize")));
        List<Map<String, Object>> reList = operatorManagementMapper.queryOperaterTable(map);

        if (null == reList || null == reList.get(0).get("telephone")){
            throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
        }

        return reList;
    }

    @Override
    public List<Map<String, Object>> queryAllRoles() {
        List<Map<String, Object>> reList = operatorManagementMapper.queryAllRoles();

        // 如果没有角色的时候会，，数据是下面这样的
        if (null == reList || null == reList.get(0).get("role_name")){
            reList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("id", -1);
            map.put("role_name", "未发现有角色的相关数据，您可以先去创建一下啦！");
            reList.add(map);
        }

        return reList;
    }

    @Override
    @Transactional
    public Map<String, Object> createNewOperationUser(Map<String, Object> map) {
         //=operatorManagementMapper.createNewOperationUserInfo(map);
        int aaa  =operatorManagementMapper.createNewOperationUserInfo(map);
        //System.out.println(aaa);
        if (aaa < 1 || StringUtils.isEmpty(map.get("id") + "")){
            throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }
        String backendUserid = map.get("id") + "";
        map.put("backendUserid", backendUserid);
        int row = operatorManagementMapper.createUserRoleRelation(map);

        if (row < 1){
            throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }

        Map reMap = new HashMap();
        reMap.clear();
        reMap.put("succeed", 1);

        return reMap;
    }

    @Override
    public Map<String, Object> updateOperationUser(Map<String, String> map) {
        int row = operatorManagementMapper.updateOperationUserInfo(map);
        if (row < 1){
            throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }


        if (!StringUtils.isEmpty(map.get("backendRoleid"))){
           int seRow = operatorManagementMapper.updateOerationRelation(map);
           if (seRow < 1){
               throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
           }
        }

        Map reMap = new HashMap();
        reMap.put("succeed", 1);
        return reMap;
    }
}
