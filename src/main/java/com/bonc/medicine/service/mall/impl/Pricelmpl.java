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
	public List<Map<String, Object>> hotword() {

		Map map = new HashMap<>();
		return priceMapper.hotword(map);
	}

	@Override
	public List<Map<String, Object>> queryHotwordByName(String name) {
		Map map = new HashMap<>();
		map.put("hotword", name);
		return priceMapper.queryHotwordByName(map);
	}

	@Override
	public void addHotword(String name) {
		Map map = new HashMap<>();
		map.put("hotword", name);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = priceMapper.queryHotwordByName(map);
		if (list != null && list.size() != 0) {
			priceMapper.increase(map);
		} else {
			priceMapper.addHotword(map);
		}
	}

	@Override
	public List<Map<String, Object>> market(String hotword, String market) {
		Map map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		map.put("hotword", hotword);
		map.put("market", market);
		map.put("time", time);
		return priceMapper.market(map);
	}

	@Override
	public List<Map<String, Object>> product(String hotword, String product) {
		Map map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		map.put("hotword", hotword);
		map.put("product", product);
		map.put("time", time);
		return priceMapper.product(map);
	}

	@Override
	public List<Map<String, Object>> trend(String hotword, String start_time, String end_time) {
		Map map = new HashMap<>();
		map.put("hotword", hotword);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		return priceMapper.trend(map);
	}

	@Override
	public List<Map<String, Object>> detail(String hotword) {
		Map map = new HashMap<>();
		map.put("hotword", hotword);
		return priceMapper.detail(map);
	}

}
