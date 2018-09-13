package com.bonc.medicine.service.knowledgebase;

import java.util.List;
import java.util.Map;

public interface SopService {
    //sop标准
    List<Map<String,Object>> sopStandardList(Integer sop_type, Integer record_status);
    //sop标准种植详细信息
    List<Map<String,Object>> sopPlantList(Integer variety_id);

    //sop添加
    int sopAdd(Map map);
    //sop步骤添加
    int sopStepAdd(List list);

    //sop修改
    int sopUpdata(Map map);
    //sop步骤修改
    int sopStepUpdata(List list);

    //sop删除
    int sopDelete(Integer id);
    //sop步骤删除
    int sopStepDelete(Integer id);
}
