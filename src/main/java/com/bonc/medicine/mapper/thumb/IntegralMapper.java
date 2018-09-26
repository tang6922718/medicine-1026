
package com.bonc.medicine.mapper.thumb;

import com.bonc.medicine.entity.thumb.IntegralRule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface IntegralMapper {

    public List<Map<String, Object>> queryIntegralByUserId(String userId);

    public List<IntegralRule> getIntegralRules();

    public int updateIntegralRule(Map<String, String> paramMap);

    public List<Map<String, Object>> queryIntegralHistory(Map<String, String> paramMap);

    public int addIntegralHistory(Map<String, String> paramMap);

    public int updateUserIntergal(Map<String, String> paramMap);

    public Map<String, Object> queryTodayIntegralOpTimes(Map<String, String> paramMap);


}

