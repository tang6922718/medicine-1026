package com.bonc.medicine.controller.user;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.user.RoleManagerService;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: medicine
 * @description: 后台角色管理
 * @author: hejiajun
 * @create: 2018-09-15 21:02
 **/
@RestController
public class RoleManagerController {

    @Autowired
    private RoleManagerService roleManagerService;

    /**
    * @Description: 新建一个角色,默认是有效的is_work=1
    * @Param: []keys:  // roleName; createUserId;isWork
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/15 
    */
    @PostMapping("/role/new/v1.0")
    public Result createNewRole(@RequestBody Map<String, String> param){
        if(null == param){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }


        if (StringUtils.isEmpty(param.get("roleName")) || StringUtils.isEmpty(param.get("createUserId"))){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        if(StringUtils.isEmpty(param.get("isWork"))){
            param.put("isWork", "1");
        }

        Map<String, Object> reMap = roleManagerService.createNewRole(param);

        return ResultUtil.success(reMap);
    }

    /**
    * @Description:条件查询角色列表 如果没有分页参数的话默认pageIndex=1；pageSize=20
    * @Param: []
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/15 
    */
    @GetMapping("/role/query/v1.0")
    public Result getRolesByCondition(@RequestParam(required = false,defaultValue = "") String roleName,
                                      @RequestParam(required = false) String pageIndex,
                                      @RequestParam(required = false) String pageSize){

        Map<String, String> param = new HashMap<>();
        param.put("roleName", roleName.trim());
        if(StringUtils.isEmpty(pageIndex)){
            param.put("pageIndex", "1");
        }
        if(StringUtils.isEmpty(pageSize)){
            param.put("pageSize", "20");
        }

        return ResultUtil.success(roleManagerService.getRolesByCondition(param));
    }

    /**
    * @Description: 删除一个角色，目前还不知道删除之前有什么条件，比如：有账号在用这个角色时不能删除。或者给出提示
    * @Param: []
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/15 
    */
    @DeleteMapping("/role/remove/v1.0/{id}")
    public Result removeRole(@PathVariable(required = true,name = "id") String id){

        if (StringUtils.isEmpty(id)){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(roleManagerService.removeRole(id));
    }

    /**
    * @Description: 修改这个角色的基本信息
    * @Param: []keys:  //roleName;isWork;id
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/15 
    */
    @PutMapping("/role/update/v1.0")
    public Result updateRoleInfo(@RequestBody Map<String, String> param){
        //roleName;isWork;id
        if(StringUtils.isEmpty(param.get("id"))){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }



        if(StringUtils.isEmpty(param.get("isWork")) && StringUtils.isEmpty(param.get("roleName"))){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success( roleManagerService.updateRoleInfo(param));
    }

    /**
    * @Description: 修改角色能访问的菜单
    * @Param: []
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/15 
    */ 
    public Result updateRolePermissions(){

        // TODO 这个方法还需要思考一下怎么实现菜单

        return null;
    }

}
