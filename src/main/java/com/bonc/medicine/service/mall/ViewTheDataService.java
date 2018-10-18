package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;


public interface ViewTheDataService {

    public List<Map<String, Object>> interaction(Integer user_id);
    
    public List<Map<String, Object>> thisWeekInteraction(Integer user_id);
    
    public List<Map<String, Object>> historyInteraction(Integer user_id);
    
    public List<Map<String, Object>> rankInteraction(Integer user_id);
    
    public List<Map<String, Object>> myResources(Integer user_id);

    public List<Map<String, Object>> problemStatistics(Integer user_id);

}
