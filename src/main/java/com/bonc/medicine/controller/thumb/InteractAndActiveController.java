package com.bonc.medicine.controller.thumb;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.user.UserManagerService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: medicine
 * @description: 互动数获取和活跃天数获取的控制类
 * @author: hejiajun
 * @create: 2018-09-30 17:05
 **/
@RestController
public class InteractAndActiveController {

    @Autowired
    private UserManagerService userManagerService;

    /**
    * @Description:
    * @Param: [queryId]：要查询的用户的id，必须参数
    * @return: com.bonc.medicine.entity.Result
     *  data{
     *      acDays : 0
     *  }
    * @Author: hejiajun
    * @Date: 2018/9/30
    */
    @GetMapping("/info/active/v1.0/{queryId}")
    public Result queryActiveDays (@PathVariable String queryId){

        //      {
        //           acDays : 0
        //       }
        return ResultUtil.success(userManagerService.activeDays(queryId));
    }

    /**
     * @Description:
     * @Param: [queryId]：要查询的用户的id  ‘1,2,3,4’ 多个这样用都逗号隔开，必须参数 queryType:目前已知  1：直播 2：专家
     * @return: com.bonc.medicine.entity.Result
     *  [{
     *        proId : 10,
     *        interactNumber : 9
     *      }]
     *
     * @Author: hejiajun
     * @Date: 2018/9/30
     */
    @GetMapping("/info/active/v1.0")
    public Result queryInteractTimes (@RequestParam String queryId){

        //      [{
        //           proId : 10,
             //      interactNumber : 9
        //       }]
        return ResultUtil.success(userManagerService.queryInteractTimes(queryId));
    }


}
