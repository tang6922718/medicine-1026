package com.bonc.medicine.mapper.knowledgebase;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface SopMapper {
    //获取sop标准
    List<Map<String,Object>> getSopStandards(Map map);
    //获取sop标准种植详细信息
    List<Map<String,Object>> getSopPlants(LinkedHashMap map);

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
    int sopCancel(Integer id);

    //获取品种sop名列表
    List<Map<String, Object>> getSopLists();
}
