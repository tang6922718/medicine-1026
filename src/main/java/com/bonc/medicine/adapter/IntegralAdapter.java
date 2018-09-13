package com.bonc.medicine.adapter;

import com.bonc.medicine.utils.IntegralKeyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: medicine-hn
 * @description: 积分部分操作缓存
 * @author: hejiajun
 * @create: 2018-09-06 17:43
 **/
@Component
public class IntegralAdapter {

    private final int OUT_TIME = 43200;

    @Autowired
    private JedisAdapter jedisAdapter;

    public void addIntegral(String userId, String integral) {
        String integralKey = IntegralKeyUtil.getIntegralKey(userId);

        jedisAdapter.set(integralKey, integral);
    }

    public String getIntegral(String userId) {
        String integralKey = IntegralKeyUtil.getIntegralKey(userId);
        String score = jedisAdapter.get(integralKey);

        if (StringUtils.isEmpty(score)) {
            return "";
        }
        return score;
    }

    public boolean existsIntegralKey(String userId) {
        String integralKey = IntegralKeyUtil.getIntegralKey(userId);

        return jedisAdapter.exists(integralKey);
    }

    public long expireKey(String userId) {
        String integralKey = IntegralKeyUtil.getIntegralKey(userId);

        return jedisAdapter.expire(integralKey);
    }


}
