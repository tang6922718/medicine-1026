package com.bonc.medicine.controller.user;

import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.constants.Constants;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.TokenModel;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.user.UserService;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.VerificationUtils;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * @desc: UserOperController  用户操作的类
 * @createTime: 2018年6月12日 上午10:19:19
 * @Auth hejiajun
 */
@RestController
public class UserOperController {

    @Autowired
    private UserService userService;

  /*  @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
       // DateFormat dateFormat = new SimpleDateFormat("MM ,DD, YYYY");
        //CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(TokenModel.class, orderDateEditor);
    }
*/

    /**
   * @Description: 
   * @Param: [phone, password, request, response]
   * @return: com.bonc.user.entity.Result
   * @Author: hejiajun
   * @Date: 2018/8/30 
   */   
    @PostMapping("/user/login/v1.0")
    public Result login(@RequestBody Map<String, String> paramMap,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        // 1、接收两个参数。
        // 2、调用Service进行登录。
        if(StringUtils.isEmpty(paramMap.get("phone")) ){
            return ResultUtil.error(000,"手机号码不能是空哦");
        }

        if(StringUtils.isEmpty(paramMap.get("password")) ){
            return ResultUtil.error(000,"密码不能是空哦");
        }

        Result result = userService.login(paramMap.get("phone"), paramMap.get("password"));
        // 3、从返回结果中取token，写入cookie。Cookie要跨域。
       /* String token = result.getData().toString();
        //设置cookie的key和value，key随便字符串，value为token值
        Cookie cookie = new Cookie("kkk", token);
        Cookie[] ss = request.getCookies();*/

        return result;

    }

    @RequestMapping("/user/token/v1.0/{token}")
    public Result getUserByToken(@PathVariable String token) throws Exception {
        Result result = userService.getUserByToken(token);
        return result;
    }

    /**
     * @Description: 用户注册方法，包含验证码验证, 用户默认头像和用户名还未实现
     * @Param: [paramMap]-->keys:phone;verification;password
     * @return: com.bonc.user.entity.Result
     * @Author: hejiajun
     * @Date: 2018/8/31
     */
    @PostMapping("/user/signup/v1.0")
    public Result signUp(@RequestBody Map<String, String> paramMap) {

        if (null == paramMap) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        //boolean isCodeValidated = VerificationUtils.validateVerification(paramMap.get("verification"), paramMap.get("phone"));

       /* if (!isCodeValidated){
            return ResultUtil.error(ResultEnum.ERROR_VERIFI);
        }*/
        int succesNum = userService.signUp(paramMap);

        Map reMap = new HashMap();
        reMap.put("succeed", succesNum);

        return ResultUtil.success(reMap);
    }

    /**
     * @Description:修改密码或者忘记密码接口
     * @Param: [paramMap] keys:phone;verification;password
     * @return: com.bonc.user.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/1
     */
    @PutMapping("/password/forget/v1.0")
    public Result forgetPassword(@RequestBody Map<String, String> paramMap) {
        if (null == paramMap) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        if (StringUtils.isEmpty(paramMap.get("phone")) || StringUtils.isEmpty(paramMap.get("password"))){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

       /* boolean isCodeValidated = VerificationUtils.validateVerification(paramMap.get("verification"), paramMap.get("phone"));

        if (!isCodeValidated){
            return ResultUtil.error(ResultEnum.ERROR_VERIFI);
        }
*/
        int succesNum = userService.updatePassword(paramMap);

        if(succesNum < 1){
            ResultUtil.error(ResultEnum.NET_ERROR);
        }
        Map reMap = new HashMap();
        reMap.put("succeed", succesNum);

        return ResultUtil.success(reMap);

    }

    @DeleteMapping("/user/logout/v1.0")
    @Authorization
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public Result logout(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        userService.deleteToken(authorization);
        return ResultUtil.success();
    }

  /*  @Authorization
    @GetMapping("/testt")
    public String test(@CurrentUser String userid) {
        //
        System.out.println("$$$$$$$$$$$" + userid);
        return "chenggong";
    }

    @GetMapping("/testt.jpg")
    public String testt() {
        //
        System.out.println("$$$$$$$$$$$");
        return "chenggong.jpg";
    }*/




    /**
     * @Description:用户通过旧密码修改新密码 通过id或者telephone都可以修改密码
     * @Param: [paramMap] keys:newPassword;oldPassword,secNewPassword,telephone
     * @return: com.bonc.user.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/1
     */
    @PutMapping("/password/update/v1.0")
    public Result changePassword(@RequestBody Map<String, String> paramMap) {
        if (null == paramMap) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        // TODO 校验账号是不是有效获取用户电话号码

        Map map = userService.changePassword(paramMap);

        return ResultUtil.success(map);

    }

    /**
     * @Description:
     * @Param: [phone, password,equipment：APP和门户登陆--APP 后台登陆--BACK         request, response]
     * @return: com.bonc.user.entity.Result
     * @Author: hejiajun
     * @Date: 2018/8/30
     */
    @PostMapping("/user/login/v2.0")
    public Result loginSecond(@RequestBody Map<String, String> paramMap,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        // 1、接收两个参数。
        // 2、调用Service进行登录。
        if(StringUtils.isEmpty(paramMap.get("phone")) ){
            return ResultUtil.error(000,"手机号码不能是空哦");
        }

        if(StringUtils.isEmpty(paramMap.get("password")) ){
            return ResultUtil.error(000,"密码不能是空哦");
        }

        //默认设置为 APP和门户登陆
        if(StringUtils.isEmpty(paramMap.get("equipment")) ){
            paramMap.put("equipment", "APP");
        }

        Result result = userService.loginSecond(paramMap.get("phone"), paramMap.get("password"), paramMap.get("equipment"));
        // 3、从返回结果中取token，写入cookie。Cookie要跨域。
       /* String token = result.getData().toString();
        //设置cookie的key和value，key随便字符串，value为token值
        Cookie cookie = new Cookie("kkk", token);
        Cookie[] ss = request.getCookies();*/

        return result;

    }


}
