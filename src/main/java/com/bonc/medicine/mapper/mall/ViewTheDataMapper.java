package com.bonc.medicine.mapper.mall;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ViewTheDataMapper {

    public List<Map<String, Object>> interaction(Map map);
    
    public List<Map<String, Object>> thisWeekInteraction(Map map);
    
    public List<Map<String, Object>> historyInteraction(Map map);
    
    public List<Map<String, Object>> rankInteraction(Map map);
    
    public List<Map<String, Object>> myResources(Map map);

    public List<Map<String, Object>> problemStatistics(Map map);
    
    public List<Map<String, Object>> queryInteractTimesDontName(Map map);

}
