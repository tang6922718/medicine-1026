package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.mapper.mall.HomePageMapper;
import com.bonc.medicine.service.mall.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class HomePagelmpl implements HomePageService {

	@Autowired
	private HomePageMapper homePageMapper;

	@Override
	public List<Map<String, Object>> notice() {

		Map map = new HashMap<>();
		return homePageMapper.notice(map);
	}

	@Override
	public List<Map<String, Object>> today() {

		Map map = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = format.format(date);
		String min_time = format.format(date) + " 00:00:00";
		String max_time = format.format(date) + " 23:59:59";
		map.put("time", time);
		map.put("min_time", min_time);
		map.put("max_time", max_time);
		return homePageMapper.today(map);
	}

}
