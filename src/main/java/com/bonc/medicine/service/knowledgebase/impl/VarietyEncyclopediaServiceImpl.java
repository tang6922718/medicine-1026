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
    public Map<String, Object> breedInfoByVariety(String variety_code,String variety_name) {
        Map map = new HashMap();
        map.put("variety_code",variety_code);
        map.put("variety_name", variety_name);
        return varietyEncyclopediaMapper.breedInfoByVariety(map);
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
    public int undoPharStatus(String id){return varietyEncyclopediaMapper.undoPharStatus(id);}

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
    public int changeStatus(Map map) {
        return varietyEncyclopediaMapper.changeStatus(map);
    }

    @Override
    public int changePhaStatus(Map map) {
        return varietyEncyclopediaMapper.changePhaStatus(map);
    }

    @Override
    public int changeSopStatus(Map map){return varietyEncyclopediaMapper.changeSopStatus(map);}

    @Override
    public int changeInfoStatus(Map map){return varietyEncyclopediaMapper.changeInfoStatus(map);}

    @Override
    public int changeVedioStatus(Map map){return varietyEncyclopediaMapper.changeVedioStatus(map);}
    
    @Override
    public int changeTrainOfflineStatus(Map map){return varietyEncyclopediaMapper.changeTrainOfflineStatus(map);}
    
    @Override
    public int changeTrainLiveStatus(Map map){return varietyEncyclopediaMapper.changeTrainLiveStatus(map);}

    @Override
    public List<Map<String, Object>> sourceDistribution() {
        return varietyEncyclopediaMapper.sourceDistribution();
    }

    @Override
    public int  historyTextIsExist(String search_text){return varietyEncyclopediaMapper.historyTextIsExist(search_text);}

    @Override
    public int updateHistoryTextDate(String search_text){return varietyEncyclopediaMapper.updateHistoryTextDate(search_text);}

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
