package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.mapper.mall.PriceMapper;
import com.bonc.medicine.service.mall.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class Pricelmpl implements PriceService {

	@Autowired
	private PriceMapper priceMapper;

	@Override
	public List<Map<String, Object>> getArieties() {

		Map map = new HashMap<>();
		return priceMapper.getArieties(map);
	}

	@Override
	public List<Map<String, Object>> getMarket() {

		Map map = new HashMap<>();
		return priceMapper.getMarket(map);
	}

	@Override
	public List<Map<String, Object>> getSpecifaction() {

		Map map = new HashMap<>();
		return priceMapper.getSpecifaction(map);
	}

	@Override
	public List<Map<String, Object>> getProduct() {

		Map map = new HashMap<>();
		return priceMapper.getProduct(map);
	}

	@Override
	public List<Map<String, Object>> market(String hotword, String market,Integer limit) {
		Map map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		map.put("hotword", hotword);
		map.put("market", market);
		map.put("limit", limit);
		map.put("time", time);
		return priceMapper.market(map);
	}

	@Override
	public List<Map<String, Object>> trend(String hotword, String market, String product, String specifaction,
			String start_time, String end_time) {
		Map map = new HashMap<>();
		map.put("hotword", hotword);
		map.put("market", market);
		map.put("product", product);
		map.put("specifaction", specifaction);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		return priceMapper.trend(map);
	}

	@Override
	public List<Map<String, Object>> detail(String hotword, String market, String product, String specifaction,
			String start_time, String end_time) {
		Map map = new HashMap<>();
		map.put("hotword", hotword);
		map.put("market", market);
		map.put("product", product);
		map.put("specifaction", specifaction);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		return priceMapper.detail(map);
	}

	@Override
	public List<Map<String, Object>> todayPrice(String hotword, String market, String product, String specifaction) {
		Map map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		map.put("hotword", hotword);
		map.put("market", market);
		map.put("product", product);
		map.put("specifaction", specifaction);
		map.put("time", time);
		return priceMapper.todayPrice(map);
	}

	@Override
	public List<Map<String, Object>> pricelist(String hotword, String priceType, String priceState, String start_time,
			String end_time) {
		Map map = new HashMap<>();
		map.put("hotword", hotword);
		map.put("priceType", priceType);
		map.put("priceState", priceState);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		return priceMapper.pricelist(map);
	}

	@Override
	public int priceState(Integer id, String state) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("state", state);
		return priceMapper.priceState(map);
	}

}
