package com.bonc.medicine.service.management;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/6.
 */
public interface CollectionService {

    public int collect(Map map);

    public List<Map<String,Object>> searchInfoByCollect(String user_id);

    public List<Map<String,Object>> searchSupplyByCollect(String user_id);

    public List<Map<String,Object>> searchPurchaseByCollect(String user_id);

    public List<Map<String,Object>> searchVideoByCollect(String user_id);

    public Map<String,Object> infoBasicDetail(String id);

    public Map<String,Object> videoCourseDetail(String id);

    public Map<String,Object> mallSupplyDetail(String id);

    public Map<String,Object> mallPurchaseDetail(String id);

    public Map<String,Object> specCaseDetail(String id);

    public int isCollect(String collect_type,String collect_object_id,String user_id);

    public int undoCollect(String collect_type,String collect_object_id,String user_id);

    public int collectCount(String collect_type, String collect_object_id);
}
