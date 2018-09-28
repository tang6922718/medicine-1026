package com.bonc.medicine.mapper.user;

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

    public int updateRolePermissions();

    public List<Map<String, Object>> queryAllMenu();
}
