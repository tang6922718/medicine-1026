package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.mapper.mall.NoticeMapper;
import com.bonc.medicine.service.mall.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class Noticelmpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public List<Map<String, Object>> systemInfo(Integer user_id, String [] notice_role_type_array) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("notice_role_type_array", notice_role_type_array);
		return noticeMapper.systemInfo(map);
	}

	@Override
	public List<Map<String, Object>> findRole(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return noticeMapper.findRole(map);
	}

	@Override
	public List<Map<String, Object>> meetProfessor(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return noticeMapper.meetProfessor(map);
	}

	@Override
	public List<Map<String, Object>> dyanimic(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return noticeMapper.dyanimic(map);
	}

	@Override
	public List<Map<String, Object>> message(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return noticeMapper.message(map);
	}

}
