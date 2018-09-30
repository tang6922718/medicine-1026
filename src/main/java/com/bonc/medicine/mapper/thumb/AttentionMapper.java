
package com.bonc.medicine.mapper.thumb;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface AttentionMapper {

	public List<Map<String, Object>> attentionRelation(Map<String, String> param);

	public List<Map<String, Object>> attentionList(Map<String, String> param);

	public int giveAttention(Map<String, String> param);

	public int removeAttention(Map<String, String> param);

	public List<Map<String, Object>> fansNum(String param);

	public List<Map<String, Object>> fansList(String userId);

	public Map<String, Object> myAttentionNumberUser(String userId);

	public Map<String, Object> myAttentionNumberPro(String userId);

	


}

