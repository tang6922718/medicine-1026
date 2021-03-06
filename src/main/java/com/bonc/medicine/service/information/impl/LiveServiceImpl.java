package com.bonc.medicine.service.information.impl;

import com.bonc.medicine.mapper.information.LiveMapper;
import com.bonc.medicine.service.information.LiveService;
import com.bonc.medicine.utils.TecentCloudUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        map.put("pull_url",TecentCloudUtils.getPullHlsrl(map.get("user_id").toString()));
        map.put("pull_rtmp_url",TecentCloudUtils.getPullRtmpUrl(map.get("user_id").toString()));




        return liveMapper.addLive(map);
    }

    @Override
    public int updateLiveStatus(String roomId, String status) {
        return liveMapper.updateLiveStatus(roomId,status);
    }

    @Override
    public List<Map> selectAllLive(Map map,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
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

    @Override
    public int delLive(Map<String, Object> map) {
        return liveMapper.delLive(map);
    }

    @Override
    public Integer updateWatchNum(String room_id) {
        return liveMapper.updateWatchNum(room_id);
    }

    @Override
    public int replayUpload(Map map) {
        return liveMapper.replayUpload(map);
    }

    @Override
    public int updateLiveReplay(Map map) {
        return liveMapper.updateLiveReplay(map);
    }
}
