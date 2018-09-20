package com.bonc.medicine.service.management.impl;

import com.bonc.medicine.mapper.management.BroadcastMapper;
import com.bonc.medicine.service.management.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/5.
 */
@Repository
public class BroadcastServiceImpl implements BroadcastService {

    @Autowired
    private BroadcastMapper broadcastMapper;

    @Override
    public List<Map<String, Object>> searchBroadcast(Map map) {
        return  broadcastMapper.searchBroadcast(map);
    }

    @Override
    public int enableBroadcast(String id) {
        return broadcastMapper.enableBroadcast(id);
    }

    @Override
    public int stopBroadcast(String id) {
        return broadcastMapper.stopBroadcast(id);
    }

    @Override
    public int deleteBroadcast(String id) {
        return broadcastMapper.deleteBroadcast(id);
    }

    @Override
    public int addBroadcast(Map map) {
        return broadcastMapper.addBroadcast(map);
    }

    @Override
    public int editBroadcast(Map map) {
        return broadcastMapper.editBroadcast(map);
    }

    @Override
    public Map showBroadcastById(String id) {
        return broadcastMapper.showBroadcastById(id);
    }
}
