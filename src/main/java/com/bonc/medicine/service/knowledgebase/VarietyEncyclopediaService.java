package com.bonc.medicine.service.knowledgebase;


import com.bonc.medicine.entity.knowledgebase.VarietyEncyclopedia;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
public interface VarietyEncyclopediaService {
    public int insertVarietyEncyclopedia(VarietyEncyclopedia tempData);
    public List<Map<String,Object>> selectBreed(String search_name,String type_code, String record_status);
    public Map<String,Object> selectBreedDetail(String id);

    public Map<String,Object> breedInfoByVariety(String variety_code,String variety_name);

    public int addBreed(Map map);

    public int updateBreedInfo(Map map);

    public int undoBreedStatus(String id);

    public int undoPharStatus(String id);

    public List<Map<String,Object>> showHotBreed();

    public List<Map<String,Object>> kmAuditList(Map map);

    public int kmAudit(Map map);

    public int changeStatus(Map map);

    public int changePhaStatus(Map map);

    public int changeSopStatus(Map map);

    public int changeInfoStatus(Map map);

    public int changeVedioStatus(Map map);
    
    public int changeTrainOfflineStatus(Map map);
    
    public int changeTrainLiveStatus(Map map);

    public List<Map<String,Object>> sourceDistribution();

    public int  historyTextIsExist(String search_text);

    public int updateHistoryTextDate(String search_text);

    public int addHistoryText(String search_text);

    public List<Map<String,Object>> searchHistoryText();

    public String selectUserCare(String user_id);
}
