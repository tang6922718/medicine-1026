package com.bonc.medicine.mapper.management;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@Component
public interface BroadcastMapper {
    public List<Map<String,Object>> searchBroadcast(Map map);

    public int enableBroadcast(String id);

    public int stopBroadcast(String id);

    public int deleteBroadcast(String id);

    public int addBroadcast(Map map);

    public int editBroadcast(Map map);

    public Map showBroadcastById(String id);
}
