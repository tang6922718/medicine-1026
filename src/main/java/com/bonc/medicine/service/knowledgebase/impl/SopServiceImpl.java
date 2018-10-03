package com.bonc.medicine.service.knowledgebase.impl;

import com.bonc.medicine.mapper.knowledgebase.SopMapper;
import com.bonc.medicine.service.knowledgebase.SopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SopServiceImpl implements SopService {

    @Autowired
    SopMapper sopMapper;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Map<String, Object>> sopStandardList(Integer sop_type, Integer record_status) {
        Map map = new HashMap();
        map.put("sop_type",sop_type);
        map.put("record_status",record_status);
        return sopMapper.getSopStandards(map);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Map<String, Object>> sopPlantList(Integer id) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("id", id);
        return sopMapper.getSopPlants(map);
    }

    @Override
    public int sopAdd(Map map) {
        return sopMapper.sopAdd(map);
    }

    @Override
    public int sopStepAdd(List list) {
        return sopMapper.sopStepAdd(list);
    }

    @Override
    public int sopUpdata(Map map) {
        return sopMapper.sopUpdata(map);
    }

    @Override
    public int sopStepUpdata(List list) {
        return sopMapper.sopStepUpdata(list);
    }

    @Override
    public int sopDelete(Integer id) {
        return sopMapper.sopDelete(id);
    }

    @Override
    public int sopCancle(Integer id) {
        return sopMapper.sopCancel(id);
    }

    @Override
    public List<Map<String, Object>> getSopLists() {
        return sopMapper.getSopLists();
    }

    @Override
    public Map<String, Object> getStep(Integer id, Integer step_order) {
        return sopMapper.getStep(id, step_order);
    }

    @Override
    public int delStep(Integer id, Integer step_order) {
        return sopMapper.delStep(id, step_order);
    }

    @Override
    public int addStep(Map map) {
        return sopMapper.addStep(map);
    }

    @Override
    public int tombstoneStep(Integer id) {
        return sopMapper.tombstoneStep(id);
    }

//    @Override
//    public int sopStepDelete(Integer id) {
//        return sopMapper.sopStepDelete(id);
//    }
}
