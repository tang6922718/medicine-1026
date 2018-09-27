package com.bonc.medicine.mapper.mall;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PriceMapper {

	public List<Map<String, Object>> getArieties(Map map);
	
	public List<Map<String, Object>> getMarket(Map map);
	
	public List<Map<String, Object>> getSpecifaction(Map map);
	
	public List<Map<String, Object>> getProduct(Map map);

	public List<Map<String, Object>> market(Map map);
	
	public List<Map<String, Object>> homeMarket(Map map);

	public List<Map<String, Object>> trend(Map map);

	public List<Map<String, Object>> detail(Map map);
	
	public List<Map<String, Object>> todayPrice(Map map);
	
	public List<Map<String, Object>> pricelist(Map map);
	
	public int priceState(Map map);

}
