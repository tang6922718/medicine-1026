package com.bonc.medicine.mapper.knowledgebase;


import com.bonc.medicine.entity.knowledgebase.VarietyEncyclopedia;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@Component
public interface VarietyEncyclopediaMapper {
    public int insertVarietyEncyclopedia(VarietyEncyclopedia tempData);
    public List<Map<String,Object>> selectBreed(Map map);
    public Map<String,Object> selectBreedDetail(String id);

    public int addBreed(Map map);

    public int updateBreedInfo(Map map);

    public int undoBreedStatus(String id);

    public List<Map<String,Object>> showHotBreed();

    public List<Map<String,Object>> kmAuditList(Map map);

    public int kmAudit(Map map);

    public List<Map<String,Object>> sourceDistribution();
}
