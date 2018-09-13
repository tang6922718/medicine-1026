package com.bonc.medicine.controller.thumb;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.service.thumb.IntegralService;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: 用户积分控制类，向外提供接口
 * @author: hejiajun
 * @create: 2018-09-06 20:29
 **/
@RestController
public class IntegralController {

    @Autowired
    private IntegralService integralService;

    @Autowired
    private RedisService redisService;

    /**
     * @Description: 根据用户id查积分
     * @Param: [userId]
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/6
     */
    @GetMapping("/integral/v1.0/{userId}")
    public Result queryIntegralByUserId(@PathVariable String userId) {

        if (StringUtils.isEmpty(userId)) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(integralService.queryIntegralByUserId(userId));
    }


    /**
     * @Description:查询全部的积分规则。如果要分页进一步实现，考虑到积分规则不算多
     * @Param: []
     * @return: boolean
     * @Author: hejiajun
     * @Date: 2018/9/6
     */
    @GetMapping("/integral/rule/v1.0")
    public Result getIntegralRuleByFakerPage() throws Exception {

        return ResultUtil.success(integralService.getIntegralRuleByPage());
    }

    /**
     * @Description:修改积分规则接口
     * @Param: [Map]keys:id;actionCode;point;isEffect;upperBound
     * @return: boolean
     * @Author: hejiajun
     * @Date: 2018/9/6
     */
    @PutMapping("/integral/rule/v1.0")
    public Result updateIntegralRule(@RequestBody Map<String, String> ruleParam) throws Exception {
        if (null == ruleParam || StringUtils.isEmpty(ruleParam.get("id")) || ruleParam.size() <= 1) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        //如果数据库中本来没有数据
        List reList = integralService.getIntegralRuleByPage();
        if (reList.size() <= 0) {
            ResultUtil.success(ResultEnum.NO_CONTENT);//返回没有数据的信息
        }
        return ResultUtil.success(integralService.updateIntegralRule(ruleParam));
    }

    /**
     * @Description:查詢積分歷史記錄的接口
     * @Param: []userId;userName;startTime;endTime
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/8
     */
    @GetMapping("/integral/history/v1.0")
    public Result queryIntegralHistory(@RequestParam(required = false) String userId,
                                       @RequestParam(required = false) String userName,
                                       @RequestParam(required = false) String startTime,
                                       @RequestParam(required = false) String endTime,
                                       @RequestParam(required = false, defaultValue = "1") String pageIndex,
                                       @RequestParam(required = false, defaultValue = "16") String pageSize) {
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("userId", userId);
        parmMap.put("userName", userName);
        parmMap.put("startTime", startTime);
        parmMap.put("endTime", endTime);
        parmMap.put("pageIndex", pageIndex);
        parmMap.put("pageSize", pageSize);

        return ResultUtil.success(integralService.queryIntegralHistory(parmMap));
    }

    /**
     * @Description:用户有相应的操作，增加积分。actionCode:LOGIN_FIRST_DAY;PUBLISH_SUPPLY;GIVE_THUMB(暂定)
     * @Param: keys:userId;actionCode
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/8
     */
    @PostMapping("/integral/v1.0")
    public Result addIntegralHistoryUpdateScore(@RequestBody Map<String, String> paramMap) {
        if (StringUtils.isEmpty(paramMap.get("userId")) || StringUtils.isEmpty(paramMap.get("actionCode"))) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        Map<String, String> reMap = null;
        try {
            reMap = integralService.addIntegralHistory(paramMap);
            return ResultUtil.success(reMap);
        } catch (Exception e) {
            reMap = integralService.queryIntegralByUserId(paramMap.get("userId"));
            reMap.put("succeedScore", "0");
            return ResultUtil.success(reMap);
        }
    }

}
