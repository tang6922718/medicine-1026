package com.bonc.medicine.service.management;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
public interface BroadcastService {
    public List<Map<String,Object>> searchBroadcast(Map map);

    public int enableBroadcast(String id);

    public int stopBroadcast(String id);

    public int deleteBroadcast(String id);

    public int addBroadcast(Map map);

    public int editBroadcast(Map map);

    public Map showBroadcastById(String id);
}
