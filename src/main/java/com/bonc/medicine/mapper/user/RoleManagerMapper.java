package com.bonc.medicine.mapper.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine
 * @description: ${description}
 * @author: hejiajun
 * @create: 2018-09-15 21:15
 **/
@Component
public interface RoleManagerMapper {

    public int createNewRole(Map<String, String> map);

    public List<Map<String, Object>> getRolesByCondition(Map<String, String> map);

    public int removeRole(String id);

    public int updateRoleInfo(Map<String, String> map);

    public int updateRolePermissions(List<Map<String, String>> idMap);
    
    public int deleteRoleMeunRelation(@Param("roleId")String roleId);
    
    public Map<String, Object> queryRolesMenuNumber(@Param("roleId")String roleId);

    public List<Map<String, Object>> queryAllMenu();
    
    public List<Map<String, Object>> queryRoleMenu(@Param("roleId")String roleId);
}
