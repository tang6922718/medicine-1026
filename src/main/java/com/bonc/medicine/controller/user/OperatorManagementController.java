package com.bonc.medicine.controller.user;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.user.OperatorManagementService;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: medicine
 * @description:系统操作员管理控制类
 * @author: hejiajun
 * @create: 2018-09-17 22:00
 **/
@RestController
public class OperatorManagementController {


    @Autowired
    private OperatorManagementService operatorManagementService;

    /**
    * @Description: 查询操作员列表or操作员列表条件查询;keyWord支持账号和姓名的模糊匹配
    * @Param: [roleId, keyWord, pageIndex, pageSize]
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/19 
    */ 
    @GetMapping("/role/oper/v1.0")
    public Result queryOperaterTable(@RequestParam(required = false, defaultValue = "") String roleId,
                                     @RequestParam(required = false, defaultValue = "") String keyWord,
                                     @RequestParam(required = false, defaultValue = "1") String pageIndex,
                                     @RequestParam(required = false, defaultValue = "20") String pageSize) {

        Map<String, String> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("keyWord", keyWord);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        return ResultUtil.success(operatorManagementService.queryOperaterTable(map));
    }

    /**
    * @Description: 查询当前的所有的角色列表（不要参数）
     *     {
     *         id：-1，
     *         role_name： 未发现有角色的相关数据，您可以先去创建一下啦
     *     }
     *     是这种情况的时候：说明数据库没有被创建的角色。
    * @Param: []
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/19 
    */ 
    @GetMapping("/role/all/v1.0")
    public Result queryAllRoles(){

        return ResultUtil.success(operatorManagementService.queryAllRoles());
    }

    /**
    * @Description: 新增操作员，在新增的时候就要创建角色的关系。也就是要指定这个操作员是什么角色
    * @Param: [map] (0, #{name}, #{loginid}, #{password}, #{telephone}, #{email}, #{age}, #{sex},
     * 	     #{address}, #{isAllowed}, #{status}, now(), #{isBackendUser}, #{headPortrait} ),
     *
     * 	     #{backendUserid}, #{backendRoleid}
     *
     * 	     TODO 默认的头像性别，等，还没处理
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/19 
    */ 
    @PostMapping("/oper/new/v1.0")
    public Result createNewOperationUser(@RequestBody Map<String, String> map){
        if (StringUtils.isEmpty(map.get("name")) || StringUtils.isEmpty(map.get("loginid"))
                || StringUtils.isEmpty(map.get("password")) || StringUtils.isEmpty(map.get("telephone"))
                || StringUtils.isEmpty(map.get("isAllowed"))){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        if (StringUtils.isEmpty(map.get("backendRoleid"))){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(operatorManagementService.createNewOperationUser((Map)map));
    }

    /**
    * @Description: 修改操作员的基本信息；修改操作员zhi定的角色
    * @Param: [map]id = #{id}
     *               , login_id = #{loginId}
     *                 , password = #{password}
     *                 , name = #{name}
     *                 , telephone = #{telephone}
     *                 , is_work = #{isWork}
     *
     *                 backendRoleid:修改角色
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/19 
    */ 
    @PutMapping("/oper/update/v1.0")
    public Result updateOperationUser(@RequestBody Map<String, String> map){
        if (StringUtils.isEmpty(map.get("id"))){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(operatorManagementService.updateOperationUser(map));
    }

    @GetMapping("/oper/info/one/v1.0")
    public Result getOperatorInfo(String oprid){

        if (StringUtils.isEmpty(oprid)){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(operatorManagementService.getOperatorInfo(oprid));
    }



}
