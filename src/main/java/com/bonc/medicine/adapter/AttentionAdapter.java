package com.bonc.medicine.adapter;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @program: medicine-hn
 *
 * @description: 关注部分操作缓存
 *
 * @author: hejiajun
 *
 * @create: 2018-09-03 17:43
 **/
@Component
public class AttentionAdapter {

    private final int OUT_TIME =  43200;

    @Autowired
    JedisAdapter jedisAdapter;

    /*
     * 判断是关注还是取消关注
     *
     * @param userId  关注人id
     * @param entityType  关注对象类型（目前专家/普通用户）
     * @param entityId     被关注对象id
     * @return 1: 在当前用户的关注列表内，0：不在当前用户的关注列表内
     */
    public int getAttentionStatus(String userId, String entityType, String entityId) {
        // 根据当前用户的userid分别生成一个likeKey 和
        // disLikeKey,再分别判断这两个值是否在对应的Like集合中和disLikeKey集合中

        // 比如如果在likeKey集合中，就返回一个1，否则返回-1
        String attKey = RedisKeyUtil.getAttentionKey(userId, entityType);

        // entityId 的用户是否在key为attKey 的集合中
        if (jedisAdapter.sismember(attKey, entityId)) {
            return 1;
        }
        //String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        //return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
        return 0;
    }

    /*
     * 关注：即当前用户关注后，放入一条信息
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     * */
    public long giveAttention(String userId, String entityType, String... entityId) {
        // 在当前news上点赞后获取key: LIKE:ENTITY_NEWS:2
        String attKey = RedisKeyUtil.getAttentionKey(userId, entityType);
        // 在喜欢集合中添加当前操作用户的userId(即当前用户点赞后，被点赞用户的like集合中就会加上一个点赞的用户信息)

        //jedisAdapter.sadd(attKey, userId);

        //String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        //jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        jedisAdapter.sadd(attKey, entityId);
        return jedisAdapter.expire(attKey);
    }

    public Set<String> getAttedListId(String userId, String entityType) {
        // 在当前news上点赞后获取key: LIKE:ENTITY_NEWS:2
        String attKey = RedisKeyUtil.getAttentionKey(userId, entityType);
        // 在喜欢集合中添加当前操作用户的userId(即当前用户点赞后，被点赞用户的like集合中就会加上一个点赞的用户信息)

        //jedisAdapter.sadd(attKey, userId);

        //String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        //jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        //jedisAdapter.sadd(attKey, entityId);
        return jedisAdapter.sdiff(attKey);
    }

    /*
     * 反对 ：即当前用户取消关注后，移除一个信息
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long removeAttention(String userId, String entityType, String entityId) {

        // 谁点击反对，谁就出现在key为dislikeKey的Set集合中
        String attKey = RedisKeyUtil.getAttentionKey(userId, entityType);
        //jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        // 从赞中删除
        //String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        //jedisAdapter.srem(attKey, String.valueOf(userId));

        jedisAdapter.srem(attKey, entityId);
        return jedisAdapter.expire(attKey);
    }

    public long addFans(String attedUserId, String... entityId) {

        String fasKey = RedisKeyUtil.getFansKey(attedUserId);

        jedisAdapter.sadd(fasKey, entityId);
        return jedisAdapter.expire(fasKey);
    }

    public long removeFans(String attedUserId, String entityId) {

        String fasKey = RedisKeyUtil.getFansKey(attedUserId);

        jedisAdapter.srem(fasKey, entityId);
        return jedisAdapter.expire(fasKey);
    }







    /**
    * @Description: 用户guanzhu 的操作
    * @Param: [userId, entityType, entityId]
    * @return: long
    * @Author: hejiajun
    * @Date: 2018/9/3 
    */ 
    public long attention(String userId, String entityType, String entityId) {
        int status = getAttentionStatus(userId, entityType, entityId);

        // 1 表在在里面
        long succeed = 0L;
        if (status == 0){
            long succeed1 = giveAttention(userId, entityType, entityId);
        }else if (status == 1){
            succeed = removeAttention(userId, entityType, entityId);
        }else {
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }
        return succeed;
    }
}
