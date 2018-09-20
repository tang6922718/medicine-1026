package com.bonc.medicine.controller.user;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.VerificationUtils;
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

    /**
    * @Description: 通过手机号码获取短信验证码的接口..发送验证码的接口，返回的验证码可以关心也可以不关心
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

        Map reMap = new HashMap();
        reMap.put("code", code);

        return ResultUtil.success(reMap);
    }

}
