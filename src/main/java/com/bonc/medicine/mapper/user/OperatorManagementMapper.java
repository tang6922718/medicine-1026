package com.bonc.medicine.mapper.user;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine
 * @description: ${description}
 * @author: hejiajun
 * @create: 2018-09-17 22:34
 **/
@Component
public interface OperatorManagementMapper {

    /**
    * @Description:查询操作员列表/条件查询
    * @Param: [map]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @Author: hejiajun
    * @Date: 2018/9/19
    */
    public List<Map<String, Object>> queryOperaterTable(Map<String, String> map);

    public List<Map<String, Object>> queryAllRoles();

    public int createNewOperationUserInfo(Map<String, Object> map);

    public int createUserRoleRelation(Map<String, Object> map);

    public int updateOperationUserInfo(Map<String, String> map);

    public int updateOerationRelation(Map<String, String> map);


}
