package com.bonc.medicine.mapper.management;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@Component
public interface CollectionMapper {

    public int collect(Map map);

    public List<Map<String,Object>> searchInfoByCollect(String user_id);

    public List<Map<String,Object>> searchSupplyByCollect(String user_id);

    public List<Map<String,Object>> searchPurchaseByCollect(String user_id);

    public List<Map<String,Object>> searchVideoByCollect(String user_id);

    public Map<String,Object> infoBasicDetail(String id);

    public Map<String,Object> videoCourseDetail(String id);
    
    public Map<String,Object> trainOfflineDetail(String id);
    
    public Map<String,Object> trainLiveDetail(String id);

    public Map<String,Object> mallSupplyDetail(String id);

    public Map<String,Object> mallPurchaseDetail(String id);

    public Map<String,Object> specCaseDetail(String id);

    public int isCollect(Map map);

    public int undoCollect(Map map);

    public int collectCount(Map map);

}
