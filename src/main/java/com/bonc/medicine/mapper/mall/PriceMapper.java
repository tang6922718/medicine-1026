package com.bonc.medicine.mapper.mall;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PriceMapper {

	public List<Map<String, Object>> hotword(Map map);

	public List<Map<String, Object>> queryHotwordByName(Map map);

	public void addHotword(Map map);
	
	public void increase(Map map);

	public List<Map<String, Object>> market(Map map);

	public List<Map<String, Object>> product(Map map);

	public List<Map<String, Object>> trend(Map map);

	public List<Map<String, Object>> detail(Map map);

}
