package com.bonc.medicine.mapper.mall;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface HomePageMapper {

	public List<Map<String, Object>> notice(Map map);
	
	public List<Map<String, Object>> today(Map map);

}
