package com.bonc.medicine.controller.user;

import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.constants.Constants;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.User;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.mall.GongQiuSystemMapper;
import com.bonc.medicine.service.thumb.LogsService;
import com.bonc.medicine.service.user.UserService;
import com.bonc.medicine.utils.ResultUtil;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private LogsService logsService;

    @Autowired
    private  VerificationUtilsController verificationUtilsController;

    @Autowired
    private GongQiuSystemMapper gongQiuSystemMapper;

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
    @Deprecated
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
       /*if(null != result.getData()){
           String uuuid =  ((Map)result.getData()).get("userId")== null ? "0" :
                   ((Map)result.getData()).get("userId")+ "";
           Map map = new HashMap();
           map.put("userId", uuuid);
           map.put("status", "1");
           map.put("ip", request.getRemoteAddr());
           //System.out.println( request.getRequestURL());
          // System.out.println(request.getRemoteAddr());
           //System.out.println(request.getHeader("X-Real-IP"));
           logsService.addLoginLogs(map);
       }
*/

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

        if(succesNum < 1){
            return ResultUtil.error(ResultEnum.NET_ERROR);
        }
        Map reMap = new HashMap();
        reMap.put("succeed", succesNum);

        return ResultUtil.success(reMap);
    }

    /**
     * @Description:修改密码 -- 通过短信验证码重置密码  门户用
     * @Param: [paramMap] keys:  phone;verification;password; 后太管理多传一个equipment=BACK
     * @return: com.bonc.user.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/1
     */
    //@Authorization
    @PutMapping("/password/forget/v1.0")
    public Result forgetPassword(@RequestBody Map<String, String> paramMap) {
        if (null == paramMap) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        if (StringUtils.isBlank(paramMap.get("phone")) || StringUtils.isBlank(paramMap.get("password"))
                 || StringUtils.isBlank(paramMap.get("verification"))){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        paramMap.put("code", paramMap.get("verification"));

       Result re =  verificationUtilsController.validateCode(paramMap);

        if(re.getCode() != 200 || re.getData() == null){
            return ResultUtil.error(ResultEnum.ERROR_CODE);
        }

        int succesNum = userService.forgetPassword(paramMap);

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
    public Result logout( @CurrentUser String userId, HttpServletRequest request) throws Exception {
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        userService.deleteToken(authorization);

        Map map = new HashMap();
        map.put("userId", userId);
        map.put("status", "1");
        map.put("ip", request.getRemoteAddr());
        //System.out.println( request.getRequestURL());
        // System.out.println(request.getRemoteAddr());
        //System.out.println(request.getHeader("X-Real-IP"));
        logsService.addLogoutLogs(map);

        return ResultUtil.success();
    }

  /*  @Authorization---> 需要登陆验证没有token直接返回登陆超时
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
     * @Description:用户通过旧密码修改新密码 通过id或者telephone都可以修改密码  门户/APP用
     * @Param: [paramMap] keys:newPassword;oldPassword,secNewPassword,telephone;
     * @return: com.bonc.user.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/1
     */
    @Authorization
    @PutMapping("/password/update/v1.0")
    public Result changePassword(@RequestBody Map<String, String> paramMap, @CurrentUser String userId) {
        if (null == paramMap) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        if(StringUtils.isBlank(paramMap.get("telephone")) && StringUtils.isBlank(userId)){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        // TODO 校验账号是不是有效获取用户电话号码

        if (!StringUtils.isBlank(userId)){
            paramMap.put("userId", userId);
        }

        Map map = userService.changePassword(paramMap);

        return ResultUtil.success(map);

    }

    /**
     * @Description:用户通过旧密码修改新密码 通过id或者telephone都可以修改密码  后台用的
     * @Param: [paramMap] keys:newPassword;oldPassword,secNewPassword,telephone ;
     * @return: com.bonc.user.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/1
     */
    @Authorization
    @PutMapping("/password/update/back/v1.0")
    public Result changePasswordBack(@RequestBody Map<String, String> paramMap, @CurrentUser String backId) {
        if (null == paramMap) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        // TODO 校验账号是不是有效获取用户电话号码

        if(StringUtils.isBlank(paramMap.get("telephone")) && StringUtils.isBlank(backId)){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        if(!StringUtils.isBlank(backId)){

            paramMap.put("backId", backId);
        }
        Map map = userService.changePasswordBack(paramMap);

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
        if(null != result.getData()){
            String uuuid =  ((Map)result.getData()).get("userId")== null ? "0" :
                    ((Map)result.getData()).get("userId")+ "";
            Map map = new HashMap();
            map.put("userId", uuuid);
            map.put("status", "1");
            map.put("ip", request.getRemoteAddr());
            //System.out.println( request.getRequestURL());
            // System.out.println(request.getRemoteAddr());
            //System.out.println(request.getHeader("X-Real-IP"));
            logsService.addLoginLogs(map);
        }

        return result;

    }

    /**
    * @Description: 通过userId获取用户的信息
    * @Param: [UserId]
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/25 
    */ 
    @GetMapping("/info/user/v1.0/{userId}")
    public Result getUserInfoById(@PathVariable String UserId){

        User user = userService.getUserInfoById(UserId);

       return  ResultUtil.success(user);
    }

    /**
    * @Description:后台管理系统登陆之后获取当前登录用户的简要信息
    * @Param: [userId] ： 必须 通过token获取的
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/29
    */
    @Authorization
    @GetMapping("/after/login/info/v1.0")
    public Result interfaceForBackAfterLogin (@CurrentUser String userId) throws Exception{


        return  ResultUtil.success(userService.interfaceForBackAfterLogin(userId));
    }

/*
    public static void main(String[] args) {
        String p = "13855559999";
        String pre = p.substring(0, 3);
        String sub = p.substring(7);
        System.out.println(pre);
        System.out.println(sub);
    }
*/

    @Authorization
    @GetMapping("/user/info/mall/number/v1.0")
    public Result querySupplyPurchNumber (@CurrentUser String userId){
        Map gongqiuMap = new HashMap();
        gongqiuMap.put("user_id", userId);
        List<Map> whatNumber = gongQiuSystemMapper.my_supply_statistics(gongqiuMap);
        Map<String, String> map = new HashMap<>();
        int supplyNumber = 0;
        for (Object coll  :whatNumber.get(0).values()) {
            supplyNumber += Integer.parseInt(coll + "");
        }
        List<Map> whatPurchase_Number = gongQiuSystemMapper.my_purchase_statistics(gongqiuMap);

        int purchaseNumber = 0;
        for (Object coll  :whatPurchase_Number.get(0).values()) {
            purchaseNumber += Integer.parseInt(coll + "");
        }

        map.put("supplyNumber", supplyNumber + "");
        map.put("purchaseNumber", purchaseNumber + "");
        return ResultUtil.success(map);
    }

    /**
    * @Description: 普通用户经过了登陆验证之后，可以修改当前用户的手机号码
    * @Param: reqMap ---key: phone:电话号码; code:验证码
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/10/3 
    */ 
    @Authorization
    @PutMapping("/user/update/phone/v1.0")
    public Result updateUserTelephoneNumber (@CurrentUser String userId, @RequestBody Map<String, String> reqMap){

        if (StringUtils.isBlank(userId)){
            return ResultUtil.error(ResultEnum.PERMISSION);
        }

        if (StringUtils.isBlank(reqMap.get("phone")) || StringUtils.isBlank(reqMap.get("code"))){
            return ResultUtil.error(ResultEnum.PERMISSION);
        }

        /*if (StringUtils.isBlank(paramMap.get("phone")) || StringUtils.isBlank(paramMap.get("password"))
                || StringUtils.isBlank(paramMap.get("verification"))){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        paramMap.put("code", paramMap.get("verification"));*/

        Result re =  verificationUtilsController.validateCode(reqMap);

        if(re.getCode() != 200 || re.getData() == null){
            return ResultUtil.error(ResultEnum.ERROR_CODE);
        }

        // 上面就是验证码已经通过验证了

        reqMap.put("userId", userId);
        return ResultUtil.success(userService.updateUserTelephoneNumber(reqMap));
        
    }

}
