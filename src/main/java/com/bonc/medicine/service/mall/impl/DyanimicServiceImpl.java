package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.entity.mall.Dyanimic;
import com.bonc.medicine.mapper.mall.DyanimicMapper;
import com.bonc.medicine.service.mall.DyanimicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DyanimicServiceImpl implements DyanimicService {
	
	@Autowired
	DyanimicMapper dyanimicMapper;

	@Override
	public int insertDyanimic(Dyanimic dyanimic) {
		return dyanimicMapper.insertDyanimic(dyanimic);
	}

	@Override
	public List<Map> selectAllDyanimic() {
		return dyanimicMapper.selectAllDyanimic();
	}

	@Override
	public List<Map> selectOneDyanimic(int id) {
		return dyanimicMapper.selectOneDyanimic(id);
	}

	@Override
	public List<Map> selectTwoDyanimic() {
		return dyanimicMapper.selectTwoDyanimic();
	}

	@Override
	public List<Map> selectUserOneDyanimic(int publish_user_id, int id) {
		return dyanimicMapper.selectUserOneDyanimic(publish_user_id,id);
	}

	@Override
	public List<Map> selectUserTwoDyanimic(int publish_user_id) {
		return dyanimicMapper.selectUserTwoDyanimic(publish_user_id);
	}

	@Override
	public List<Map> selectDetailOneDyanimic(int id) {
		return dyanimicMapper.selectDetailOneDyanimic(id);
	}


}
