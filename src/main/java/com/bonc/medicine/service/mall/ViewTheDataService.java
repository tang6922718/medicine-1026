package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;


public interface ViewTheDataService {

    public List<Map<String, Object>> interaction(Integer user_id);

    public List<Map<String, Object>> problemStatistics(Integer user_id);

}
