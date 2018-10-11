package com.bonc.medicine.service.thumb.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.adapter.IntegralAdapter;
import com.bonc.medicine.entity.thumb.IntegralRule;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.thumb.IntegralMapper;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.service.thumb.IntegralService;
import com.bonc.medicine.utils.IntegralKeyUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: medicine-hn
 * @description: 积分的实现类
 * @author: hejiajun
 * @create: 2018-09-06 20:42
 **/
@Repository
public class IntegralServiceImpl implements IntegralService {

    private long OUT_TIME = 21600L;

    @Autowired
    private IntegralMapper integralMapper;

    @Autowired
    private IntegralAdapter integralAdapter;

    @Autowired
    private RedisService redisService;

    @Override
    public Map<String, String> queryIntegralByUserId(String userId) {
        boolean exists = integralAdapter.existsIntegralKey(userId);
        Map<String, String> reMap = new HashMap<>();
        String reKey = "integral";
        if (exists) {
            String score = integralAdapter.getIntegral(userId);
            if (!StringUtils.isEmpty(score)) {
                reMap.put(reKey, score);
                return reMap;
            } else {
                return dontKnowHowToName(userId);
            }
        }

        return dontKnowHowToName(userId);
    }

    @Override
    public List<IntegralRule> getIntegralRuleByPage() throws Exception {

        List<IntegralRule> lists = redisService.getList(IntegralKeyUtil.getIntegralRuleKey(), IntegralRule.class);

        if (null != lists && lists.size() > 0) {

            return lists;
        } else {
            List<IntegralRule> reList = new ArrayList<>();
            synchronized (reList) {
                reList = integralMapper.getIntegralRules();
                if (reList.size() != 0) {
                    redisService.setList(IntegralKeyUtil.getIntegralRuleKey(), reList);
                    redisService.expire(IntegralKeyUtil.getIntegralRuleKey(), OUT_TIME);//6xiaoshi
                    return reList;
                } else {
                    return reList;
                }
            }
        }
    }

    /**
     * @Description:修改积分规则的
     * @Param: [paramMap]
     * @return: java.util.Map<java.lang.String       ,       java.lang.String>
     * @Author: hejiajun
     * @Date: 2018/9/7
     */
    @Override
    public Map<String, String> updateIntegralRule(Map<String, String> paramMap) throws Exception {

        int updateRowNumber = integralMapper.updateIntegralRule(paramMap);
        if (updateRowNumber != 1) {
            throw new MedicineRuntimeException(ResultEnum.ERROE);
        }
        Map<String, String> reMap = new HashMap<>();
        String key = "succeed";
        reMap.put(key, "1");
        List<IntegralRule> integralList = redisService.getList(IntegralKeyUtil.getIntegralRuleKey(), IntegralRule.class);

        //如果缓存中不存在
        if (null == integralList || integralList.size() <= 0) {
            synchronized (integralList) {
                redisService.setList(IntegralKeyUtil.getIntegralRuleKey(), integralList);
                redisService.expire(IntegralKeyUtil.getIntegralRuleKey(), OUT_TIME);//6xiaoshi
            }
            return reMap;
        }

        //缓存中存在，更新缓存
        for (IntegralRule ruleObject : integralList) {
            if (ruleObject.getId() == Integer.parseInt(paramMap.get("id"))) {
                synchronized (reMap) {
                    ruleObject.setActionCode(StringUtils.isEmpty(paramMap.get("actionCode"))
                            ? ruleObject.getActionCode() : paramMap.get("actionCode"));
                    ruleObject.setPoint(StringUtils.isEmpty(paramMap.get("point"))
                            ? ruleObject.getPoint() : Integer.parseInt(paramMap.get("point")));
                    ruleObject.setIsEffect(StringUtils.isEmpty(paramMap.get("isEffect"))
                            ? ruleObject.getIsEffect() : paramMap.get("isEffect").charAt(0));
                    ruleObject.setUpperBound(StringUtils.isEmpty(paramMap.get("upperBound"))
                            ? ruleObject.getUpperBound() : Integer.parseInt(paramMap.get("upperBound")));
                    ruleObject.setUpperBound(StringUtils.isEmpty(paramMap.get("chineseName"))
                            ? ruleObject.getUpperBound() : Integer.parseInt(paramMap.get("chineseName")));

                    redisService.setList(IntegralKeyUtil.getIntegralRuleKey(), integralList);
                    redisService.expire(IntegralKeyUtil.getIntegralRuleKey(), OUT_TIME);//6xiaoshi
                }
                return reMap;
            }
        }
        throw new MedicineRuntimeException(ResultEnum.ERROE);
    }

    @Override
    public List<Map<String, Object>> queryIntegralHistory(Map<String, String> paramMap) {

        PageHelper.startPage(Integer.parseInt(paramMap.get("pageIndex")), Integer.parseInt(paramMap.get("pageSize")));

        return integralMapper.queryIntegralHistory(paramMap);
    }

    @Override
    @Transactional
    public Map<String, String> addIntegralHistory(Map<String, String> paramMap) throws Exception {

        //paramMap 里面有userId,actionCode
        IntegralRule ruleDemo = null;
        try {
            List<IntegralRule> ruleList = getIntegralRuleByPage();

            for (IntegralRule rule : ruleList) {
                if (StringUtils.equals(rule.getActionCode(), paramMap.get("actionCode"))) {
                    ruleDemo = rule;
                    paramMap.put("point", String.valueOf(rule.getPoint()));
                }
            }

        } catch (Exception e) {
            throw new MedicineRuntimeException(ResultEnum.ERROE);
        }
        boolean goingDown = checkIntegralTimes(paramMap, ruleDemo);
        Map<String, String> scoreMap  =  new HashMap<>();
        if(!goingDown){
            scoreMap.put("succeedScore", "0");
            scoreMap.put("msg", "亲！今天你也元气满满呢，操作次数上限了哦");
            return scoreMap;
        }
        scoreMap = updateIntegralUtil(paramMap);
        //添加历史记录表
        int impactNumberScore = integralMapper.addIntegralHistory(paramMap);//如果失败等待再次执行时比较好的。

        //更新积分总数并且跟新缓存

        //scoreMap.get("integral");
        if (impactNumberScore > 0) {
            scoreMap.put("succeedScore", paramMap.get("point"));
        } else {
            scoreMap.put("succeedScore", "0");
        }
        /*if(impactNumberScore < 1){
            这里暂时不处理
        }*/
        return scoreMap;

    }

    @Override
    public boolean queryClockInStatus(String userId, String actionCode) {
        Map<String, String> paramMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeNow = sdf.format(new Date());
        paramMap.put("timeNow", timeNow);
        paramMap.put("userId", userId);
        paramMap.put("actionCode", actionCode);
        Map<String, Object> resultMap =  integralMapper.queryTodayIntegralOpTimes(paramMap);
        if(null == resultMap || null == resultMap.get("timesToday")
                || StringUtils.equals("0",resultMap.get("timesToday")+"")){

            return true;
        }
        return false;
    }

    /**
     * @Description:更新积分总数表和当前用户缓存的工具类
     * @Param: [map]
     * @return: java.util.Map<java.lang.String       ,       java.lang.String>
     * @Author: hejiajun
     * @Date: 2018/9/8
     */
    public Map<String, String> updateIntegralUtil(Map<String, String> map) throws Exception {

        int impactNumber = integralMapper.updateUserIntergal(map);
        if(impactNumber < 1){
            impactNumber = integralMapper.addIntegralRecord(map);
        }

        boolean exists = integralAdapter.existsIntegralKey(map.get("userId"));
        if (exists && impactNumber > 0) {
            redisService.del(IntegralKeyUtil.getIntegralKey(map.get("userId")));
            Map<String, String> currentScore = queryIntegralByUserId(map.get("userId"));
            return currentScore;
        } else if (impactNumber < 1) {
            throw new MedicineRuntimeException(ResultEnum.ERROE);
        }

        return queryIntegralByUserId(map.get("userId"));
    }


    /**
     * @Description: 只是个帮助的方法
     * @Param: [userId]
     * @return: java.util.Map<java.lang.String       ,       java.lang.String>
     * @Author: hejiajun
     * @Date: 2018/9/7
     */
    public Map<String, String> dontKnowHowToName(String userId) {
        Map<String, String> reMap = new HashMap<>();
        String reKey = "integral";
        synchronized (reMap) {
            List<Map<String, Object>> queMap = integralMapper.queryIntegralByUserId(userId);
            if (queMap.size() != 0 && null != queMap.get(0).get("current_integral")) {
                String score = queMap.get(0).get("current_integral").toString();
                reMap.put(reKey, score);

                integralAdapter.addIntegral(userId, score);
                integralAdapter.expireKey(userId);
                return reMap;
            } else {
                reMap.put("reKey", "0");
                return reMap;
            }
        }
    }

    /**
    * @Description: 检查积分增加的次数，比如，签到每天只能签到一次
    * @Param: [paramMap]
    * @return: boolean true:还可以操作。false:操作到上限次数了
    * @Author: hejiajun
    * @Date: 2018/9/25 
    */ 
    private boolean checkIntegralTimes(Map<String, String> paramMap, IntegralRule ruleDemo){

        // map 里面有；userId;actionCode
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeNow = sdf.format(new Date());
        paramMap.put("timeNow", timeNow);
        Map<String, Object> resultMap =  integralMapper.queryTodayIntegralOpTimes(paramMap);
        if(null == resultMap || null == resultMap.get("timesToday")
                ||  StringUtils.equals("0",resultMap.get("timesToday")+"")){

            return true;
        }
        int times = ruleDemo.getUpperBound() / ruleDemo.getPoint();

        if(Integer.parseInt(resultMap.get("timesToday") + "") < times){
            return true;
        }

        return  false;
    }

   /* public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));
    }*/


}
