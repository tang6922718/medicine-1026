package com.bonc.medicine.service.information.impl;

import com.bonc.medicine.mapper.information.LiveMapper;
import com.bonc.medicine.service.information.LiveService;
import com.bonc.medicine.utils.TecentCloudUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class LiveServiceImpl implements LiveService {
    @Autowired(required = false)
    private LiveMapper liveMapper;

    @Override
    public int createLive(Map<String, Object> map) {

        map.put("room_id", TecentCloudUtils.bizid+"_"+map.get("user_id").toString());
        map.put("push_url",TecentCloudUtils.getPushUrl(map.get("user_id").toString()));
        map.put("pull_url",TecentCloudUtils.getPullUrl(map.get("user_id").toString()));
        map.put("pull_rtmp_url",TecentCloudUtils.getPullRtmpUrl(map.get("user_id").toString()));

     /*   try {
            map.put("live_start",new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").parse((String) map.get("live_start")));
            map.put("live_end",new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").parse((String) map.get("live_end")));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        return liveMapper.addLive(map);
    }

    @Override
    public int updateLiveStatus(String roomId, String status) {
        return liveMapper.updateLiveStatus(roomId,status);
    }

    @Override
    public List<Map<String, Object>> selectAllLive(Map map) {
        return liveMapper.selectAllLive(map);
    }

    @Override
    public int updateWatchPeople(Map map) {
        return liveMapper.updateWatchPeople(map);
    }

    @Override
    public int editLive(Map<String, Object> map) {
        return liveMapper.editLive(map);
    }

    @Override
    public int repealLive(Map<String, Object> map) {
        return liveMapper.repealLive(map);
    }
}
