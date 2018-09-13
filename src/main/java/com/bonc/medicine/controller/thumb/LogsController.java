package com.bonc.medicine.controller.thumb;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.thumb.LogsService;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: 用户日志的控制类
 * @author: hejiajun
 * @create: 2018-09-08 19:42
 **/
@RestController
public class LogsController {

    @Autowired
    private LogsService logsService;

    /**
     * @Description:添加登陆日志userId是必须的，ip不是必须的，status没有就默认1：正常的
     * @Param: [paramMap]userId;ip;status
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @PostMapping("/logs/login/v1.0")
    public Result addLoginLogs(@RequestBody Map<String, String> paramMap) {

        //没有userId是不行的
        if (StringUtils.isEmpty(paramMap.get("userId"))) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        //如果没有status参数就默认给1
        if (StringUtils.isEmpty(paramMap.get("status"))) {
            paramMap.put("status", "1");
        }

        return ResultUtil.success(logsService.addLoginLogs(paramMap));
    }

    /**
     * @Description:添加登chu日志userId是必须的，ip不是必须的，status没有就默认1：正常的
     * @Param: [paramMap]userId;ip;status
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @PostMapping("/logs/logout/v1.0")
    public Result addLogoutLogs(@RequestBody Map<String, String> paramMap) {

        //没有userId是不行的
        if (StringUtils.isEmpty(paramMap.get("userId"))) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        //如果没有status参数就默认给1
        if (StringUtils.isEmpty(paramMap.get("status"))) {
            paramMap.put("status", "1");
        }

        return ResultUtil.success(logsService.addLogoutLogs(paramMap));
    }

    /**
     * @Description:标记操作日志正常 logId：是必须的没有返回缺失参数
     * @Param: [paramMap]
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @PutMapping("/logs/normal/v1.0/{logId}")
    public Result updateOperLogsNormal(@PathVariable String logId) {
        if (StringUtils.isEmpty(logId)) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(logsService.updateOperLogsNormal(logId));
    }

    /**
     * @Description:标记操作日志不正常 logId：是必须的没有返回缺失参数
     * @Param: [paramMap]
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @PutMapping("/logs/unnormal/v1.0/{logId}")
    public Result updateOperLogsUnnormal(@PathVariable String logId) {
        if (StringUtils.isEmpty(logId)) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(logsService.updateOperLogsUnnormal(logId));
    }

    /**
     * @Description:查询用户登陆或者登出的日志；如果分页参数没有。默认显示最近20条
     * @Param: [userName:模糊, starTime, endTime, ip：模糊, onlyLogin:不是空就只查询登入日志,
     * onlyLogout：不是空就只查询登出日志, pageIndex, pageSize]
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @GetMapping("/logs/login/logout/v1.0")
    public Result queryLoginOutLogs(@RequestParam(required = false) String userName,
                                    @RequestParam(required = false) String starTime,
                                    @RequestParam(required = false) String endTime,
                                    @RequestParam(required = false) String ip,
                                    @RequestParam(required = false) String onlyLogin,
                                    @RequestParam(required = false) String onlyLogout,
                                    @RequestParam(required = false) String pageIndex,
                                    @RequestParam(required = false) String pageSize) {
        Map<String, String> paramMap = new HashMap<>();

        //如果分页参数没有。默认显示最近20条
        if (StringUtils.isEmpty(pageIndex)) {
            paramMap.put("pageIndex", "1");
        }
        if (StringUtils.isEmpty(pageSize)) {
            paramMap.put("pageIndex", "20");
        }
        paramMap.put("userName", userName);
        paramMap.put("starTime", starTime);
        paramMap.put("endTime", endTime);
        paramMap.put("ip", ip);
        paramMap.put("onlyLogin", onlyLogin);
        paramMap.put("onlyLogout", onlyLogout);

        return ResultUtil.success(logsService.queryLoginOutLogs(paramMap));
    }

    /**
     * @Description:查询用户操作日志的接口
     * @Param: [userName：模糊, starTime, endTime, status：1正常0异常, pageIndex, pageSize]
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @GetMapping("/logs/operation/v1.0")
    public Result queryOperLogs(@RequestParam(required = false) String userName,
                                @RequestParam(required = false) String starTime,
                                @RequestParam(required = false) String endTime,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) String pageIndex,
                                @RequestParam(required = false) String pageSize) {

        Map<String, String> paramMap = new HashMap<>();

        //如果分页参数没有。默认显示最近20条
        if (StringUtils.isEmpty(pageIndex)) {
            paramMap.put("pageIndex", "1");
        }
        if (StringUtils.isEmpty(pageSize)) {
            paramMap.put("pageIndex", "20");
        }
        paramMap.put("userName", userName);
        paramMap.put("starTime", starTime);
        paramMap.put("endTime", endTime);
        paramMap.put("status", status);

        return ResultUtil.success(logsService.queryOperLogs(paramMap));
    }


    /**
     * @Description:添加用户操作日志的接口userId;opeType;opeResource;opModule;status,这些是paramMap的key
     * @Param: [paramMap]
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @PostMapping("/logs/operation/v1.0")
    public Result addOperLogs(Map<String, String> paramMap) {
        //userId;opeType;opeResource;opModule;status,这些是paramMap的key
        //如果没有用户的userId参数就返回缺失参数的错误；
        if (StringUtils.isEmpty(paramMap.get("userId"))) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        //如果没有status参数就默认给1
        if (StringUtils.isEmpty(paramMap.get("status"))) {
            paramMap.put("status", "1");
        }

        // 其他参数先不校验,如果校验的话就像下面这样。
       /* if (StringUtils.isEmpty(paramMap.get("opeType"))){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }*/

        return ResultUtil.success(logsService.addOperLogs(paramMap));
    }

    /**
     * @Description:查询用户登陆次数的接口
     * @Param: []starTime：endTime：
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    @GetMapping("/logs/login/times/v1.0")
    public Result queryUserLoginTimes(@RequestParam(required = false) String starTime,
                                      @RequestParam(required = false) String endTime) {
        Map<String, String> param = new HashMap<>();

        //如果 没有时间参数默认查询7天之内的数据
        if (StringUtils.isEmpty(starTime)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
            Date today = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String result = format.format(today);
            param.put("starTime", result);
        }
        param.put("endTime", endTime);
        return ResultUtil.success(logsService.queryUserLoginTimes(param));
    }

}
