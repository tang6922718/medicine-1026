package com.bonc.medicine.controller.thumb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.adapter.JedisAdapter;
import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.User;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.service.user.UserManagerService;
import com.bonc.medicine.service.user.UserService;
import com.bonc.medicine.utils.ResultUtil;

/**
 * @program: medicine-hn
 *
 * @description: 用户关注控制类
 *
 * @author: hejiajun
 *
 * @create: 2018-09-03 17:20
 **/
@RestController
public class AttentionController {

    @Autowired
    AttentionService attentionService;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    private UserService userService;
    

    /**
    * @Description:查询当前用户是否关注了某用户 现在关注只有专家和用户，，，0：普通用户，1：专家
    * @Param: [userId：当前用户, attedUserId：被关注的用户, type]
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/5
    */
    @GetMapping("/attention/relation/v1.0")
    public Result attentionRelation(@CurrentUser String userid,
                                    @RequestParam(required = true,name = "userId") String userId,
                                    @RequestParam(required = true,name = "attedUserId") String attedUserId,
                                    @RequestParam(required = false,name = "type",defaultValue = "0") String type){

        /*if(null == userid || null == attedUserId){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        long isFollowed = attentionAdapter.getAttentionStatus(userid, type, attedUserId);

        Map reMap = new HashMap();

        reMap.put("followed", isFollowed);*/
        String uuuid = "";
        if (StringUtils.equals("", userid)){
            uuuid = userId;
        }
        if (!StringUtils.equals("", userid)){
            uuuid = userid;
        }
        Map<String, String> map = new HashMap<>();
        map.put("userId", uuuid);
        map.put("attedUserId", attedUserId);
        map.put("type", type);
        validatePram(map);
        return ResultUtil.success(attentionService.attentionRelation(map));
        //return ResultUtil.success(reMap);
    }

    /**
    * @Description: 关注的请求
    * @Param: [paramMap]  key: attedUserId,  userId ,  type
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/4
    */
    @PostMapping("/attention/v1.0")
    public Result giveAttention(@CurrentUser String userid ,@RequestBody Map<String, String> paramMap){

        //String attedUserId, String userid , String type
        String uuuid = "";

        if (!StringUtils.equals("", userid)){
            paramMap.put("userId", userid);
        }

        validatePram(paramMap);

        long succeed = attentionService.giveAttention(paramMap);
        Map reMap = new HashMap();

        reMap.put("succeed", succeed);
        return ResultUtil.success(reMap);
    }

    /**
     * @Description: 取消关注的请求
     * @Param: [paramMap]  key: attedUserId,  userId ,  type
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/4
     */
    @PutMapping("/attention/v1.0")
    public Result removeAttention(@CurrentUser String userid ,@RequestBody Map<String, String> paramMap){

        //String attedUserId, String userid , String type
        if (!StringUtils.equals("", userid)){
            paramMap.put("userId", userid);
        }
        validatePram(paramMap);

        long succeed = attentionService.removeAttention(paramMap);
        Map reMap = new HashMap();

        reMap.put("succeed", succeed);
        return ResultUtil.success(reMap);
    }


    /**
    * @Description: 查询用户的关注列表，userId：要查询的用户的id，type=0关注的普通用户 =1关注的专家
    * @Param: [userId, type]
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/5 
    */ 
    @GetMapping("/attention/list/v1.0/{userId}/{type}")
    public Result attentionList(@CurrentUser String userid, @PathVariable String userId,
                                @PathVariable String type){

        //String userid , String type
        Map paramMap = new HashMap();
        if (!StringUtils.equals("", userid)){
            paramMap.put("userId", userid);
        }else{

            paramMap.put("userId", userId);
        }
        paramMap.put("type", type);

        Map succeed = attentionService.attentionList(paramMap);
        Set<String> idSet = (Set<String>)(succeed.get("attentionedUsers"));


        List<Map<String,Object>> outList = new ArrayList();
        if (null == idSet || idSet.size() < 1){
            idSet = (Set<String>)(succeed.get("attentionedPro"));
            if (null == idSet || idSet.size() < 1){

                return ResultUtil.error(ResultEnum.NO_CONTENT);
            }
        }

        for (String ids: idSet) {
            //Map<String, String> map = new HashMap<>();
            //map.put("userId", userId);
            paramMap.put("attedUserId", ids);
           // map.put("type", type);
            Map isFlow = attentionService.attentionRelation(paramMap);

            Map<String,Object> outMap = new HashMap();
            User user = userService.getUserInfoById(ids);
            outMap.put("headPortrait", user.getHeadPortrait());
            outMap.put("name", user.getName());
            Map<String, Object> fansMap =  attentionService.fansNum(ids);
            outMap.put("fansNumber", fansMap.get("fansNum"));
            outMap.put("expertise_field", user.getExpertise_field());
            outMap.put("employment_age", user.getEmployment_age());
            outMap.put("loveVariety", user.getCaresVarieties());
            outMap.put("followed",   isFlow.get("followed"));
            outMap.put("id",   ids);
          
            outMap.put("active_count",   user.getActive_count());
            outMap.put("interactiveNumber",   user.getInteractiveNumber());
            outList.add(outMap);
        }

        return ResultUtil.success(outList);
    }

    /**
    * @Description:查询用户的粉丝列表
    * @Param: [userId]
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/26
    */
    @GetMapping("/attention/fans/list/v1.0/{userId}")
    //public Result fansList(@PathVariable String userId, @CurrentUser String headUserId){
    public Result fansList(@PathVariable String userId){

        String uuuserId = "";
        //if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(headUserId)){
        if (StringUtils.isEmpty(userId) ){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        if (!StringUtils.isEmpty(userId)){
            uuuserId = userId;
        }
        /*if (!StringUtils.isEmpty(headUserId)){
            uuuserId = headUserId;
        }
*/
        Map succeed = attentionService.fansList(uuuserId);

        Set<String> idSet = (Set<String>)(succeed.get("fansList"));
        if (null == idSet || idSet.size() < 1){
            return ResultUtil.error(ResultEnum.NO_CONTENT);
        }
        List<Map<String,Object>> outList = new ArrayList();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", uuuserId);
        for (String ids: idSet) {
            paramMap.put("attedUserId", ids);
            paramMap.put("type", "0");
            Map isFlow = attentionService.attentionRelation(paramMap);

            Map<String,Object> outMap = new HashMap();
            User user = userService.getUserInfoById(ids);
            outMap.put("headPortrait", user.getHeadPortrait());
            outMap.put("name", user.getName());
            Map<String, Object> fansMap =  attentionService.fansNum(ids);
            outMap.put("fansNumber", fansMap.get("fansNum"));
            outMap.put("expertise_field", user.getExpertise_field());
            outMap.put("employment_age", user.getEmployment_age());
            outMap.put("loveVariety", user.getCaresVarieties());
            outMap.put("followed",   isFlow.get("followed"));
            outMap.put("id",   ids);
            outMap.put("active_count",   user.getActive_count());
            outMap.put("interactiveNumber",   user.getInteractiveNumber());
            outList.add(outMap);
        }

        return ResultUtil.success(outList);

    }

    /**
    * @Description: 根据userId获取这个用户的被关注的数量，如果需要获取这个用户被关注的列表还需要进一步实现
    * @Param: [userId]
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/5 
    */ 
    @GetMapping("/attention/fans/{userId}")
    public Result fansNum (@PathVariable String userId){
        if (StringUtils.isEmpty(userId)){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        return ResultUtil.success(attentionService.fansNum(userId));
    }

    /**
    * @Description: 通过用户的id获取当前用户关注了多少用户，数量
    * @Param: [userId]
     * data:{
     *      attNumber : 100
     * }
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/29 
    */
    @Authorization
    @GetMapping("/attention/number/attention/v1.0")
    public Result myAttentionNumber (@CurrentUser String userId){

        //attNumber
        Map<String, String> reMap = new HashMap<>();
        if (StringUtils.equals("", userId)){
            reMap.put("attNumber", "0");
        }
        reMap = attentionService.myAttentionNumber(userId);
        //reMap.put("attNumber", "0");

        return ResultUtil.success(reMap);
    }

    @RequestMapping("/keys/{pre}")
    public Set keys (@PathVariable String pre){
        return jedisAdapter.keys(pre);
    }

    @RequestMapping("/del/{key}")
    public long keys222 (@PathVariable String key){
        jedisAdapter.del(key);
        return 111L;
    }


    public void validatePram( Map<String, String> paramMap){
        if (null == paramMap){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }

        String userid = paramMap.get("userId");
        String attedUserId = paramMap.get("attedUserId");
        String type = paramMap.get("type");
        if(null == type ){
            paramMap.put("type", "0");
        }

        if(null == userid || null == attedUserId){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }
    }

    /**
     * @Description: 关注和取消关注都是用这个接口
     * @Param: [paramMap] keys：attedUserId；userid；type专家 "1" or非专家不传就是非专家
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/3
     */
    //@SuppressWarnings("SpellCheckingInspection")
    /*@PostMapping("/attention/v1.0")
    public Result attentionAndRemove(@RequestBody Map<String, String> paramMap){

        //String attedUserId, String userid , String type
        if (null == paramMap){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        String userid = paramMap.get("userid");
        String attedUserId = paramMap.get("attedUserId");
        String type = paramMap.get("type");

        if(null == userid || null == attedUserId){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        long likeCount = attentionAdapter.attention(userid, type, attedUserId);

        Map reMap = new HashMap();
        reMap.put("succeed", likeCount);
        return ResultUtil.success(reMap);
    }*/
    public static void main(String[] args) {
        List<Map> ll = new ArrayList();
        Map hh = new HashMap();
        hh.put("iii", 123);
        ll.add(hh);
        for (Map yy: ll) {
            yy.put("iii",345);
            yy.put("444",444);
        }
        System.out.println(ll.toString());
    }

}
