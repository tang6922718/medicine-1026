package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;


public interface PriceService {

	public List<Map<String, Object>> hotword();

	public List<Map<String, Object>> queryHotwordByName(String name);

	public void addHotword(String name);

	public List<Map<String, Object>> market(String hotword, String market);

	public List<Map<String, Object>> product(String hotword, String product);

	public List<Map<String, Object>> trend(String hotword, String start_time, String end_time);

	public List<Map<String, Object>> detail(String hotword);

}
