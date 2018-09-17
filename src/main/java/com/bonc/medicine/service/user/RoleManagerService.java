package com.bonc.medicine.service.user;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine
 * @description: ${description}
 * @author: hejiajun
 * @create: 2018-09-15 21:11
 **/
public interface RoleManagerService {

    /**
     * @Description: 新建一个角色
     * @Param: []
     * @return: com.bonc.medicine.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/15
     */
    public Map<String, Object> createNewRole(Map<String, String> map);

    /**
     * @Description:条件查询角色列表
     * @Param: []
     * @return: com.bonc.medicine.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/15
     */
    public List<Map<String, Object>> getRolesByCondition(Map<String, String> map);

    /**
     * @Description: 删除一个角色，目前还不知道删除之前有什么条件，比如：有账号在用这个角色时不能删除。或者给出提示
     * @Param: []
     * @return: com.bonc.medicine.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/15
     */
    public Map<String, Object> removeRole(String id);

    /**
     * @Description: 修改这个角色的基本信息
     * @Param: []
     * @return: com.bonc.medicine.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/15
     */
    public Map<String, Object> updateRoleInfo(Map<String, String> map);
    /**
     * @Description: 修改角色能访问的菜单
     * @Param: []
     * @return: com.bonc.medicine.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/15
     */
    public Map<String, Object> updateRolePermissions();

}
