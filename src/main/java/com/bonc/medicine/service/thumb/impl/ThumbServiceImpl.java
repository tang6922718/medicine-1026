package com.bonc.medicine.service.thumb.impl;

import com.bonc.medicine.adapter.JedisAdapter;
import com.bonc.medicine.adapter.ThumbAdapter;
import com.bonc.medicine.mapper.thumb.ThumbMapper;
import com.bonc.medicine.service.thumb.ThumbService;
import com.bonc.medicine.utils.RedisKeyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 *
 * @description: 用户点赞实现类
 *
 * @author: hejiajun
 *
 * @create: 2018-09-05 19:07
 **/
@Repository
public class ThumbServiceImpl implements ThumbService {

    @Autowired
    private ThumbMapper thumbMapper;

    @Autowired
    private ThumbAdapter thumbAdapter;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public Map<String, Object> giveThumb(Map<String, String> paramMap) {
        String giveThumbId = paramMap.get("giveThumbId");
        String acceptThumbId = paramMap.get("acceptThumbId");
        String type = paramMap.get("type");
        Map<String, Object> reMap = new HashMap<>();
        synchronized (reMap){
            thumbMapper.giveThumb(paramMap);
            reMap.put("succeed", thumbAdapter.giveThumb(acceptThumbId, type, giveThumbId));
            thumbAdapter.expire(acceptThumbId, type);
            return reMap;
        }
    }

    @Override
    public Map<String, Object> removeThumb(Map<String, String> paramMap) {
        String giveThumbId = paramMap.get("giveThumbId");
        String acceptThumbId = paramMap.get("acceptThumbId");
        String type = paramMap.get("type");
        Map<String, Object> reMap = new HashMap<>();
        synchronized (reMap){
            thumbMapper.removeThumb(paramMap);
            reMap.put("succeed", thumbAdapter.removeThumb(acceptThumbId, type, giveThumbId));
            thumbAdapter.expire(acceptThumbId, type);
            return reMap;
        }
    }

    public Map<String, Object> thumbNumber(Map<String, String> paramMap) {
        String type = paramMap.get("type");
        String acceptThumbId = paramMap.get("acceptThumbId");
        Map<String, Object> reMap = new HashMap<>();

        boolean exists = jedisAdapter.exists(RedisKeyUtil.getThumbKey(acceptThumbId, type));

        if(exists){
            long num = jedisAdapter.scard(RedisKeyUtil.getThumbKey(acceptThumbId, type));
            thumbAdapter.expire(acceptThumbId, type);
            /*if(num == 0){

            }*/
            reMap.put("thumbNumber", num);
            return reMap;
        }else {
            synchronized (reMap) {
                List<Map<String, Object>> queMap = thumbMapper.thumbNumber(paramMap);
                if(queMap.size() != 0 && null != queMap.get(0).get("praise_user_id")){
                    reMap.put("thumbNumber", queMap.size());
                    List<String> ids = new ArrayList();
                    String [] arrays = new String[queMap.size()];
                    for (Map<String, Object> inMap : queMap ) {
                        ids.add(inMap.get("praise_user_id") == null ? "" : inMap.get("praise_user_id").toString());
                    }
                    thumbAdapter.giveThumb(acceptThumbId,type, ids.toArray(arrays));
                    thumbAdapter.expire(acceptThumbId, type);
                    return reMap;
                }else{
                    reMap.put("thumbNumber", 0);
                    return reMap;
                }
            }

        }
    }

    @Override
    public Map selectThumbNumber(Map<String, String> map) {
        return  thumbMapper.selectThumbNumber(map);
    }

    @Override
    public int thumbStatus(String userid, String type, String acceptThumbId) {

        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(type) || StringUtils.isEmpty(acceptThumbId)){
            return -999;
        }
        String key = RedisKeyUtil.getThumbKey(acceptThumbId, type);
        boolean exists = jedisAdapter.sismember(key, acceptThumbId);
        if (exists){
            return  1;
        }
        Map<String, String> paramMap = new HashMap();
        paramMap.put("userid", userid);
        paramMap.put("type", type);
        paramMap.put("acceptThumbId", acceptThumbId);
        List<Map<String, Object>> reList = thumbMapper.thumbStatus(paramMap);
        if (null == reList || reList.size() < 1){
            return 0;
        }

        return 1;
    }


}
