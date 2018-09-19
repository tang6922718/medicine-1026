package com.bonc.medicine.service.user;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine
 * @description: ${description}
 * @author: hejiajun
 * @create: 2018-09-19 16:30
 **/
public interface OperatorManagementService {

    /**
     * @Description:查询操作员列表/条件查询
     * @Param: [map]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: hejiajun
     * @Date: 2018/9/19
     */
    public List<Map<String, Object>> queryOperaterTable(Map<String, String> map);

    public List<Map<String, Object>> queryAllRoles();


    /**
    * @Description: 创建一个操作员，包含操作员基本信息和角色关系
    * @Param: [map]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: hejiajun
    * @Date: 2018/9/19 
    */ 
    public Map<String, Object> createNewOperationUser(Map<String, Object> map);


    /**
     * @Description: 修改一个操作员，包含操作员基本信息和角色关系
     * @Param: [map]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: hejiajun
     * @Date: 2018/9/19
     */
    public Map<String, Object> updateOperationUser(Map<String, String> map);


}
