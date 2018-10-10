package com.bonc.medicine.adapter;

import com.bonc.medicine.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: medicine-hn
 *
 * @description: 点赞部分操作缓存
 *
 * @author: hejiajun
 *
 * @create: 2018-09-05 17:43
 **/
@Component
public class ThumbAdapter {

    private final int OUT_TIME =  43200;

    @Autowired
    JedisAdapter jedisAdapter;

    public long giveThumb (String acceptThumbId, String type, String... giveThumbId){

        return jedisAdapter.sadd(RedisKeyUtil.getThumbKey(acceptThumbId, type), giveThumbId);
    }

    public long removeThumb (String acceptThumbId, String type, String removeThumbId){

        return jedisAdapter.srem(RedisKeyUtil.getThumbKey(acceptThumbId, type), removeThumbId);
    }

    public long expire (String acceptThumbId, String type){

        return jedisAdapter.expire(RedisKeyUtil.getThumbKey(acceptThumbId, type));
    }

    public long giveThumbLive (String acceptThumbId, String type, String... giveThumbId){

        return jedisAdapter.lpush(RedisKeyUtil.getThumbKey(acceptThumbId, type), giveThumbId);
    }



}
