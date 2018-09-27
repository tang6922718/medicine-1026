package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;

public interface PriceService {

	public List<Map<String, Object>> getArieties();

	public List<Map<String, Object>> getMarket(String hotword);

	public List<Map<String, Object>> getSpecifaction(String hotword);

	public List<Map<String, Object>> getProduct(String hotword);

	public List<Map<String, Object>> market(String hotword, String market);
	
	public List<Map<String, Object>> homeMarket();

	public List<Map<String, Object>> trend(String hotword, String market, String product, String specifaction,
			String start_time, String end_time);

	public List<Map<String, Object>> detail(String hotword, String market, String product, String specifaction,
			String start_time, String end_time);

	public List<Map<String, Object>> todayPrice(String hotword, String market, String product, String specifaction);

	public List<Map<String, Object>> pricelist(String hotword, String priceType, String priceState, String start_time,
			String end_time);

	public int priceState(Integer id, String state);

}
