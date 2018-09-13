package com.bonc.medicine.service.field.impl;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Field;
import com.bonc.medicine.entity.field.FieldRecord;
import com.bonc.medicine.mapper.field.FieldManageMapper;
import com.bonc.medicine.service.field.FieldManageService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("fieldManageService")
public class FieldManageServiceImpl implements FieldManageService {

	@Autowired
	FieldManageMapper fieldManageMapper;
	
	@Override
	public Result<Object> addField(Field tempData) {
		Map map =new HashMap();
		map=fieldManageMapper.queryUserInfo(tempData.getUser_id());
		map.putAll(fieldManageMapper.queryCoopName(tempData.getUser_id()));
		if (map !=null){
			tempData.setUser_name((String) map.get("name"));
			tempData.setUser_tel((String)map.get("telephone"));
			tempData.setCoop_name((String)map.get("coop_name"));
		}
		tempData.setRegistation_time(new Date());
		return ResultUtil.success(fieldManageMapper.insertField(tempData));
	}

	@Override
	public Result<Object> getfield(Map param) {
		return ResultUtil.success(fieldManageMapper.getfield(param));
	}

	/* *
	 * @Description 新增农事操作时  需要更新相关田块的最新修改时间
	 * @Date 15:11 2018/9/1
	 * @Param [param]
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@Override
	public Result<Object> addOperation(FieldRecord tempData) {
		//更新相关田块的最新修改时间
		int temp=fieldManageMapper.updateFieldInfo(tempData.getField_id(),new Date());
		if (temp>0){
			tempData.setRecord_time(new Date());
			return ResultUtil.success(fieldManageMapper.addOperation(tempData));
		}else {
			return ResultUtil.error(500,"田块信息未更新");
		}

	}

	@Override
	public Result<Object> getOperation(Map param) {
		Map map=new HashMap();
		map.put("field_id",param.get("field_id"));

		List<Map> list1=new ArrayList<Map>();
		List<Map> list2=new ArrayList<Map>();
		List<Map> list3=new ArrayList<Map>();

		list1=fieldManageMapper.getfield(map);
		list2=fieldManageMapper.getOperation(param);
		list3.addAll(list1);
		list3.addAll(list2);
		return ResultUtil.success(list3);
//		return ResultUtil.success(fieldManageMapper.getOperation(param));
	}
	
	@Override
	public Result<Object> guideRecord(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return ResultUtil.success(fieldManageMapper.guideRecord(map));
	}
	
	@Override
	public Result<Object> guideRecordNum(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return ResultUtil.success(fieldManageMapper.guideRecordNum(map));
	}
	
}
