package com.bonc.medicine.service.management;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/6.
 */
public interface CollectionService {

    public int collect(Map map);

    public List<Map<String,Object>> searchInfoByCollect();

    public List<Map<String,Object>> searchSupplyByCollect();

    public List<Map<String,Object>> searchVideoByCollect();

    public Map<String,Object> infoBasicDetail(String id);

    public Map<String,Object> videoCourseDetail(String id);

    public Map<String,Object> mallSupplyDetail(String id);

    public Map<String,Object> mallPurchaseDetail(String id);

    public Map<String,Object> specCaseDetail(String id);
}
