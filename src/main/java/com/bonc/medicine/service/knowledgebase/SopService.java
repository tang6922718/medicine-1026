package com.bonc.medicine.service.knowledgebase;

import java.util.List;
import java.util.Map;

public interface SopService {
    //sop标准
    List<Map<String,Object>> sopStandardList(Integer sop_type, Integer record_status);
    //sop标准种植详细信息
    List<Map<String, Object>> sopPlantList(Integer variety_id);

    List<Map<String, Object>> sopPlantListBack(Integer sop_id);

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
    //int sopStepDelete(Integer id);

    //sop撤销
    int sopCancle(Integer id);

    //获取品种sop名列表
    List<Map<String, Object>> getSopLists();

    //获取某一步种植信息
    Map<String, Object> getStep(Integer id, Integer step_order);

    //删除某一步种植信息
    int delStep(Integer id, Integer step_order);

    //添加某一步种植步骤
    int addStep(Map map);

    //更新sop前删除所有步骤，再重建所有步骤
    int tombstoneStep(Integer id);
}
