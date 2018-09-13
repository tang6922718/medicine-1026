package com.bonc.medicine.service.thumb;


import com.bonc.medicine.entity.thumb.IntegralRule;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: 用户积分接口
 * @author: hejiajun
 * @create: 2018-09-04 16:10
 **/
public interface IntegralService {

    public Map<String, String> queryIntegralByUserId(String userId);

    public List<IntegralRule> getIntegralRuleByPage() throws Exception;

    public Map<String, String> updateIntegralRule(Map<String, String> paramMap) throws Exception;

    public List<Map<String, Object>> queryIntegralHistory(Map<String, String> paramMap);

    public Map<String, String> addIntegralHistory(Map<String, String> paramMap) throws Exception;



}
