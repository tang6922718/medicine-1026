package com.bonc.medicine.service.thumb.impl;

import com.bonc.medicine.adapter.AttentionAdapter;
import com.bonc.medicine.adapter.JedisAdapter;
import com.bonc.medicine.mapper.thumb.AttentionMapper;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.utils.RedisKeyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @program: medicine-hn
 *
 * @description: 用户关注实现类
 *
 * @author: hejiajun
 *
 * @create: 2018-09-04 16:10
 **/
@Repository
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    private AttentionMapper attentionMapper;

    @Autowired
    private AttentionAdapter attentionAdapter;

    @Autowired
    private JedisAdapter jedisAdapter;


    @Override
    public Map<String, String> attentionRelation(Map<String, String> param) {

        Map<String, String> reMap = new HashMap<>();
        reMap.put("followed", "1");

        long isFollowed = attentionAdapter.getAttentionStatus(param.get("userId"), param.get("type"), param.get("attedUserId"));
        if(isFollowed == 1L){
            jedisAdapter.expire(RedisKeyUtil.getAttentionKey(param.get("userId"), param.get("type")));
            return reMap;
        }else {
            List<Map<String, Object>> queMap = new ArrayList<>();
            synchronized (queMap) {
                queMap = attentionMapper.attentionRelation(param);
                if(queMap.size() != 0 && null != queMap.get(0).get("id")){
                    attentionAdapter.giveAttention(param.get("userId"), param.get("type"), queMap.get(0).get("id").toString());
                    return reMap;
                }else{
                    reMap.put("followed", "0");
                    return reMap;
                }
            }
        }
    }

    @Override
    public Map<String, Object> attentionList(Map<String, String> param) {
        Map<String, Object> reMap = new HashMap<>();
        String key = "";
        if(StringUtils.equals("1", param.get("type"))){
            key = "attentionedPro";
        }else if(StringUtils.equals("0", param.get("type"))){
            key = "attentionedUsers";
        }
        Set<String> set = new HashSet<>();
        set = attentionAdapter.getAttedListId(param.get("userId"), param.get("type"));

        if(set != null && set.size() > 0){
            jedisAdapter.expire(RedisKeyUtil.getAttentionKey(param.get("userId"), param.get("type")));
            reMap.put(key, set);
            return reMap;
        }else {
            List<Map<String, Object>> queMap = new ArrayList<>();
            synchronized (queMap) {
                queMap = attentionMapper.attentionList(param);
                if(queMap.size() != 0 && null != queMap.get(0).get("object_id")){
                    List<String> ids = new ArrayList();
                    String [] arrays = new String[queMap.size()];
                    for (Map<String, Object> map: queMap) {
                        set.add(map.get("object_id") == null ? "" : map.get("object_id").toString());
                        ids.add(map.get("object_id") == null ? "" : map.get("object_id").toString());
                    }

                    attentionAdapter.giveAttention(param.get("userId"), param.get("type"), ids.toArray(arrays));

                    reMap.put(key, set);
                    return reMap;
                }else{
                    reMap.put("key", new HashSet<>());
                    return reMap;
                }
            }

        }
    }

    @Override
    public long giveAttention(Map<String, String> param) {
        String userid = param.get("userId");
        String attedUserId = param.get("attedUserId");
        String type = param.get("type");
        synchronized (param){
            attentionMapper.giveAttention(param);
            attentionAdapter.addFans(attedUserId, userid);
            return attentionAdapter.giveAttention(userid, type, attedUserId);
        }

    }

    @Override
    public Map<String, Object> fansNum(String attedUserId) {
        Map<String, Object> map = new HashMap<>();
        boolean exists = jedisAdapter.exists(RedisKeyUtil.getFansKey(attedUserId));

        if(exists){
            long num = jedisAdapter.scard(RedisKeyUtil.getFansKey(attedUserId));
            jedisAdapter.expire(RedisKeyUtil.getFansKey(attedUserId));
            /*if(num == 0){

            }*/
            map.put("fansNum", num);
            return map;
        }else {
            synchronized (map) {
                List<Map<String, Object>> queMap = attentionMapper.fansNum(attedUserId);
                if(queMap.size() != 0 && null != queMap.get(0).get("follow_user_id")){

                    map.put("fansNum", queMap.get(0).size());
                    List<String> ids = new ArrayList();
                    String [] arrays = new String[queMap.size()];
                    for (Map<String, Object> inMap : queMap ) {
                        ids.add(inMap.get("follow_user_id") == null ? "" : inMap.get("follow_user_id").toString());
                    }
                    attentionAdapter.addFans(attedUserId, ids.toArray(arrays));
                    return map;
                }else{
                    map.put("fansNum", 0);
                    return map;
                }
            }

        }
    }

    public Map<String, Object> fansList(String userId) {
        Map<String, Object> reMap = new HashMap<>();
        String key = "fansList";

        Set<String> set = new HashSet<>();
        set = attentionAdapter.getFansListId(userId);

        if(set != null && set.size() > 0){
            jedisAdapter.expire(RedisKeyUtil.getFansKey(userId));
            reMap.put(key, set);
            return reMap;
        }else {
            List<Map<String, Object>> queMap = new ArrayList<>();
            synchronized (queMap) {
                queMap = attentionMapper.fansList(userId);
                if(queMap.size() != 0 && null != queMap.get(0) && null != queMap.get(0).get("follow_user_id")){
                    List<String> ids = new ArrayList();
                    String [] arrays = new String[queMap.size()];
                    for (Map<String, Object> map: queMap) {
                        set.add(map.get("follow_user_id") == null ? "" : map.get("follow_user_id").toString());
                        ids.add(map.get("follow_user_id") == null ? "" : map.get("follow_user_id").toString());
                    }

                    attentionAdapter.addFans(userId,  ids.toArray(arrays));

                    reMap.put(key, set);
                    return reMap;
                }else{
                    reMap.put(key, new HashSet<>());
                    return reMap;
                }
            }

        }

    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public long removeAttention(Map<String, String> param) {
        String userid = param.get("userId");
        String attedUserId = param.get("attedUserId");
        String type = param.get("type");
        synchronized (param){
            attentionMapper.removeAttention(param);
            attentionAdapter.removeFans(attedUserId, userid);
            return attentionAdapter.removeAttention(userid, type, attedUserId);
        }
    }

}
