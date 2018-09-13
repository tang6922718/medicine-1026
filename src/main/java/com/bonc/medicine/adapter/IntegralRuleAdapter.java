package com.bonc.medicine.adapter;

import com.bonc.medicine.entity.thumb.IntegralRule;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.utils.IntegralKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: medicine-hn
 * @description: 积分部分操作缓存
 * @author: hejiajun
 * @create: 2018-09-06 17:43
 **/
@Component
public class IntegralRuleAdapter {

    private final int OUT_TIME = 21600;//6xiaoshi


    @Autowired
    private RedisService redisService;

    public long rpush(IntegralRule integralRule) throws Exception {

        return redisService.rpush(IntegralKeyUtil.getIntegralRuleKey(), integralRule);
    }

    public List<IntegralRule> getList() throws Exception {

        return redisService.getList(IntegralKeyUtil.getIntegralRuleKey(), IntegralRule.class);
    }


}
