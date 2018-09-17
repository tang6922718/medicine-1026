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
	public List<Map> selectAllDyanimic(int dyn_cat_id, String publish_time) {
		return dyanimicMapper.selectAllDyanimic( dyn_cat_id,  publish_time);
	}

	@Override
	public List<Map> selectUserDyanimic(int publish_user_id,int dyn_cat_id) {
		return dyanimicMapper.selectUserDyanimic(publish_user_id,dyn_cat_id);
	}

	@Override
	public List<Map> selectDetailOneDyanimic(int id) {
		return dyanimicMapper.selectDetailOneDyanimic(id);
	}

	@Override
	public int delOneDyanimic(int id) {
		return dyanimicMapper.delOneDyanimic(id);
	}

	@Override
	public List<Map> selectDyanimicCategory() {
		return dyanimicMapper.selectDyanimicCategory();
	}

	@Override
	public List<Map> selectUncheckDyanimic() {
		return dyanimicMapper.selectUncheckDyanimic();
	}

	@Override
	public int updateOneDyanimic(char effect_flag, String fail_opinion,int id) {
		return dyanimicMapper.updateOneDyanimic(effect_flag, fail_opinion,id);
	}

	@Override
	public List<Map> selectJoinDyanimic(int user_id) {
		return dyanimicMapper.selectJoinDyanimic(user_id);
	}

}
