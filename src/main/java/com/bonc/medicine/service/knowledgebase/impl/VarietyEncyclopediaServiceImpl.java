package com.bonc.medicine.service.knowledgebase.impl;

import com.bonc.medicine.entity.knowledgebase.VarietyEncyclopedia;
import com.bonc.medicine.mapper.knowledgebase.VarietyEncyclopediaMapper;
import com.bonc.medicine.service.knowledgebase.VarietyEncyclopediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@Service
public class VarietyEncyclopediaServiceImpl implements VarietyEncyclopediaService {

    @Autowired
    private VarietyEncyclopediaMapper varietyEncyclopediaMapper;

    @Override
    public int insertVarietyEncyclopedia(VarietyEncyclopedia tempData) {
        return varietyEncyclopediaMapper.insertVarietyEncyclopedia(tempData);
    }

    @Override
    public List<Map<String, Object>> selectBreed(String search_name,String type_code, String record_status) {
        Map map = new HashMap();
        map.put("search_name",search_name);
        map.put("type_code", type_code);
        map.put("record_status", record_status);
        return varietyEncyclopediaMapper.selectBreed(map);
    }

    @Override
    public Map<String, Object> selectBreedDetail(String id) {
        return varietyEncyclopediaMapper.selectBreedDetail(id);
    }

    @Override
    public Map<String, Object> breedInfoByVarietyId(String variety_code) {
        return varietyEncyclopediaMapper.breedInfoByVarietyId(variety_code);
    }

    @Override
    public int addBreed(Map map) {
        return varietyEncyclopediaMapper.addBreed(map);
    }

    @Override
    public int updateBreedInfo(Map map) {
        return varietyEncyclopediaMapper.updateBreedInfo(map);
    }

    @Override
    public int undoBreedStatus(String id) {
        return varietyEncyclopediaMapper.undoBreedStatus(id);
    }

    @Override
    public List<Map<String, Object>> showHotBreed() {
        return varietyEncyclopediaMapper.showHotBreed();
    }

    @Override
    public List<Map<String, Object>> kmAuditList(Map map) {
        return varietyEncyclopediaMapper.kmAuditList(map);
    }

    @Override
    public int kmAudit(Map map) {
        return varietyEncyclopediaMapper.kmAudit(map);
    }

    @Override
    public List<Map<String, Object>> sourceDistribution() {
        return varietyEncyclopediaMapper.sourceDistribution();
    }

    @Override
    public int addHistoryText(String search_text) {
        return varietyEncyclopediaMapper.addHistoryText(search_text);
    }

    @Override
    public List<Map<String, Object>> searchHistoryText() {
        return varietyEncyclopediaMapper.searchHistoryText();
    }

    @Override
    public String selectUserCare(String user_id) {
        return varietyEncyclopediaMapper.selectUserCare(user_id);
    }


}
