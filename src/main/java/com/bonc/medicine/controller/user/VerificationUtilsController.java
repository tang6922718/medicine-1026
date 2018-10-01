package com.bonc.medicine.controller.user;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.adapter.JedisAdapter;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.utils.CodeKeyUtil;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.VerificationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: medicine
 * @description:
 * @author: hejiajun
 * @create: 2018-09-20 09:40
 **/
@RestController
public class VerificationUtilsController {


    @Autowired
    private RedisService redisService;

    @Autowired
    private JedisAdapter jedisAdapter;

    /**
    * @Description: 通过手机号码获取短信验证码的接口..发送验证码的接口，返回的验证码可以关心也可以不关心
     * 验证码5分钟有效
    * @Param: [map]key:phone
    * @return: com.bonc.medicine.entity.Result： Result里面的data，data里面的code就是验证码
     * {
     *     "code": 200,
     *     "msg": "成功",
     *     "data": {
     *         "code": "5954"
     *     }
     * }
    * @Author: hejiajun
    * @Date: 2018/9/20 
    */ 
    @PostMapping("/verification/code/v1.0")
    public Result getVerificationCode(@RequestBody Map<String, String> map){


        String code = VerificationUtils.getVerifByPhone(map.get("phone"));

        //目前设置的验证码过期时间是5分钟-考虑要发送会延迟
        long outTime = 300L;

        try {
            redisService.set(CodeKeyUtil.getCodeKey(map.get("phone")), code);
            redisService.expire(CodeKeyUtil.getCodeKey(map.get("phone")),outTime );
        }catch (Exception e){
            return ResultUtil.error(ResultEnum.NET_ERROR);
        }

        Map reMap = new HashMap();
        reMap.put("code", code);

        return ResultUtil.success(reMap);
    }


    /**
    * @Description:校验验证码的接口。可以在登陆之前调用。先校验验证么，也可以由登陆接口里面调用
    * @Param: [map] key:phone;code
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/20 
    */ 
    @PostMapping("/verification/validate/v1.0")
    public Result validateCode (@RequestBody Map<String, String> map){
        if(null == map || StringUtils.isBlank(map.get("phone"))
                || StringUtils.isBlank(map.get("code"))){

            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        boolean ex = jedisAdapter.exists(CodeKeyUtil.getCodeKey(map.get("phone")));

        if (ex){
            try {

                String code = redisService.get(CodeKeyUtil.getCodeKey(map.get("phone")));

                if(StringUtils.equals(code, map.get("code"))){
                    Map ma = new HashMap();
                    ma.put("valiate", "pass");
                    return ResultUtil.success(ma);
                }
                return ResultUtil.error(ResultEnum.ERROR_CODE);
            }catch (Exception e){
                return ResultUtil.error(ResultEnum.NET_ERROR);
            }
        }else{
            return ResultUtil.error(222, "亲！！请先获取验证码啦");
        }

    }



}
