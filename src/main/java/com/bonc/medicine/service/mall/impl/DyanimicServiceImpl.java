package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.entity.mall.Dyanimic;
import com.bonc.medicine.mapper.mall.DyanimicMapper;
import com.bonc.medicine.service.mall.DyanimicService;
import com.bonc.medicine.service.thumb.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DyanimicServiceImpl implements DyanimicService {
	
	@Autowired
	DyanimicMapper dyanimicMapper;

	@Autowired
	private IntegralService integralService;

	@Override
	public int insertDyanimic(Dyanimic dyanimic) {

		//积分代码
		Map<String, String> ppparamMap = new HashMap<>();
		//userId;actionCode
		ppparamMap.put("userId", dyanimic.getPublish_user_id() + "");
		ppparamMap.put("actionCode", "RELEASE_DYNAMIC");
		try{

			integralService.addIntegralHistory(ppparamMap);
		}catch (Exception e){
			System.out.println("ERROR ：新建田间操作中---增加积分异常");
		}
		return dyanimicMapper.insertDyanimic(dyanimic);
	}

	@Override
	public List<Map> selectAllDyanimic(int dyn_cat_id, String publish_time) {
		return dyanimicMapper.selectAllDyanimic( dyn_cat_id,  publish_time);
	}
	@Override
	public List<Map> selectAllDyanimic(String dyn_cat_id, String publish_time, String words) {
		return dyanimicMapper.selectAllDyanimicTwo( dyn_cat_id,  publish_time, words);
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
	public List<Map> selectUncheckDyanimic(int dyn_cat_id,String publish_time) {
		return dyanimicMapper.selectUncheckDyanimic( dyn_cat_id, publish_time);
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
