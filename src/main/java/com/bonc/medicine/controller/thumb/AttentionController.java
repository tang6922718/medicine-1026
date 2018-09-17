package com.bonc.medicine.controller.thumb;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.adapter.JedisAdapter;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.utils.IntegralKeyUtil;
import com.bonc.medicine.utils.RedisKeyUtil;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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



    /**
    * @Description:查询当前用户是否关注了某用户 type=1：被专注用户是专家 = 0：被关注用户是普通用户
    * @Param: [userId：当前用户, attedUserId：被关注的用户, type]
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/5
    */
    @GetMapping("/attention/relation/v1.0")
    public Result attentionRelation(@RequestParam(required = true,name = "userId") String userId,
                                    @RequestParam(required = true,name = "attedUserId") String attedUserId,
                                    @RequestParam(required = false,name = "type",defaultValue = "0") String type){

        /*if(null == userid || null == attedUserId){
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }

        long isFollowed = attentionAdapter.getAttentionStatus(userid, type, attedUserId);

        Map reMap = new HashMap();

        reMap.put("followed", isFollowed);*/
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("attedUserId", attedUserId);
        map.put("type", type);
        validatePram(map);
        return ResultUtil.success(attentionService.attentionRelation(map));
        //return ResultUtil.success(reMap);
    }

    /**
    * @Description: 关注的请求
    * @Param: [paramMap]  key: attedUserId,  userid ,  type
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/4
    */
    @PostMapping("/attention/v1.0")
    public Result giveAttention(@RequestBody Map<String, String> paramMap){

        //String attedUserId, String userid , String type
        validatePram(paramMap);

        long succeed = attentionService.giveAttention(paramMap);
        Map reMap = new HashMap();

        reMap.put("succeed", succeed);
        return ResultUtil.success(reMap);
    }

    /**
     * @Description: 取消关注的请求
     * @Param: [paramMap]  key: attedUserId,  userid ,  type
     * @return: com.bonc.thumb.entity.Result
     * @Author: hejiajun
     * @Date: 2018/9/4
     */
    @PutMapping("/attention/v1.0")
    public Result removeAttention(@RequestBody Map<String, String> paramMap){

        //String attedUserId, String userid , String type
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
    public Result attentionList(@PathVariable String userId, @PathVariable String type){

        //String userid , String type
        Map paramMap = new HashMap();
        paramMap.put("userId", userId);
        paramMap.put("type", type);

        Map succeed = attentionService.attentionList(paramMap);

        return ResultUtil.success(succeed);
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

    @RequestMapping("/keys")
    public Set keys (){
        return jedisAdapter.keys();
    }

   /* @RequestMapping("/get")
    public long keys222 (){
        long p = redisService.redisService(IntegralKeyUtil.getIntegralRuleKey());
        return p;
    }*/


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

}
