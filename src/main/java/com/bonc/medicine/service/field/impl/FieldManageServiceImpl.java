package com.bonc.medicine.service.field.impl;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Field;
import com.bonc.medicine.entity.field.FieldRecord;
import com.bonc.medicine.mapper.field.FieldManageMapper;
import com.bonc.medicine.service.field.FieldManageService;
import com.bonc.medicine.utils.ExchangeCategroyNameID;
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
		// // 所有品种信息
		// List<Map> allCategroyInfo=fieldManageMapper.queryAllCategroy();

		Map map = new HashMap();
		map = fieldManageMapper.queryUserInfo(tempData.getUser_id());
		if (map != null) {
			tempData.setUser_name((String) map.get("name"));
			tempData.setUser_tel((String) map.get("telephone"));
		}

		Map map2 = new HashMap();
		map2=fieldManageMapper.queryCoopName(tempData.getUser_id());  // map2 有可能为空
		if (map2==null){
			tempData.setCoop_name("");
		}else {
			tempData.setCoop_name( map2.get("coop_name")==null?"":(String) map2.get("coop_name"));
		}

		tempData.setRegistation_time(new Date());
		tempData.setState("0");
		// tempData.setPlant_type(ExchangeCategroyNameID.NameToId(tempData.getPlant_type(),allCategroyInfo));

		// 新建地块是否成功 成功后才往品种信息表里插入地块品种信息
		int i = fieldManageMapper.insertField(tempData);
		if (i > 0) {
			int insertNum = 0;
			int fieldID = tempData.getId();
			String categroyString = tempData.getCategroysList();

			if (categroyString.contains(";")) { // 多品种

				String[] categroyList = categroyString.split(";");
				for (int j = 0; j < categroyList.length; j++) {
					String[] token = categroyList[j].split(",");
					Map param = new HashMap();
					param.put("fieldID", fieldID);
					param.put("categroyID", token[0]);
					param.put("plantNum", token[1]);
					param.put("seedAge", token[2]);
					fieldManageMapper.insertFieldPlantCategroysInfo(param);
					insertNum++;
				}

				return ResultUtil.success(insertNum);

			} else { // 单品种
				String[] token = categroyString.split(",");
				Map param = new HashMap();
				param.put("fieldID", fieldID);
				param.put("categroyID", token[0]);
				param.put("plantNum", token[1]);
				param.put("seedAge", token[2]);
				insertNum = fieldManageMapper.insertFieldPlantCategroysInfo(param);

				return ResultUtil.success(insertNum);
			}
		} else {
			return ResultUtil.error(500, "新建种植失败!!!");
		}

	}

	@Override
	public Result<Object> getfield(Map param) {
		List<Map> categroy = fieldManageMapper.queryAllCategroy();

		List<Map> temp = fieldManageMapper.getfield(param);
		for (Map obj : temp) {
			obj.put("plant_typeID", obj.get("plant_type"));
			obj.put("plant_type", ExchangeCategroyNameID.IDToName(obj.get("plant_type").toString(), categroy));
			obj.put("fieldCategroyInfo", fieldManageMapper.queryFieldCategroyInfo((int) obj.get("id")));
		}

		return ResultUtil.success(temp);
	}

	/*
	 * *
	 * 
	 * @Description 新增农事操作时 需要更新相关田块的最新修改时间
	 * 
	 * @Date 15:11 2018/9/1
	 * 
	 * @Param [param]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@Override
	public Result<Object> addOperation(FieldRecord tempData) {
		// 更新相关田块的最新修改时间
		int temp = fieldManageMapper.updateFieldInfo(tempData.getField_id(), new Date());
		if (temp > 0) {
			tempData.setRecord_time(new Date());
			return ResultUtil.success(fieldManageMapper.addOperation(tempData));
		} else {
			return ResultUtil.error(500, "田块信息未更新");
		}

	}

	@Override
	public Result<Object> getOperation(Map param) {
		List<Map> categroy = fieldManageMapper.queryAllCategroy();

		Map map = new HashMap();
		map.put("field_id", param.get("field_id"));

		List<Map> list1 = new ArrayList<Map>();
		List<Map> list2 = new ArrayList<Map>();
		List<Map> list3 = new ArrayList<Map>();

		list1 = fieldManageMapper.getfield(map);

		for (Map obj : list1) {
			obj.put("plant_typeID", obj.get("plant_type"));
			obj.put("plant_type", ExchangeCategroyNameID.IDToName(obj.get("plant_type").toString(), categroy));
			obj.put("fieldCategroyInfo", fieldManageMapper.queryFieldCategroyInfo((int) obj.get("id")));
		}

		list2 = fieldManageMapper.getOperation(param);
		list3.addAll(list1);
		list3.addAll(list2);
		return ResultUtil.success(list3);
		// return ResultUtil.success(fieldManageMapper.getOperation(param));
	}

	@Override
	public Result<Object> guideRecord(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = fieldManageMapper.guideRecord(map);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				if ("新建种植".equals(list.get(i).get("record").toString())) {
					list.get(i).put("plant_msg",
							fieldManageMapper.getPlantMsg(Integer.parseInt(list.get(i).get("id").toString())));
				}
			}
		}
		return ResultUtil.success(list);
	}

	@Override
	public Result<Object> guideRecordNum(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return ResultUtil.success(fieldManageMapper.guideRecordNum(map));
	}

	@Override
	public Result<Object> getFarmOpreationByCategroy(int categroyID) {
		return ResultUtil.success(fieldManageMapper.queryFarmOpreationByCategroy(categroyID));
	}

	@Override
	public Result<Object> getCategroySOPInfo(int categroyID, int stepID) {
		Map map = new HashMap();
		map.put("sop_id", categroyID);
		map.put("id", stepID);
		return ResultUtil.success(fieldManageMapper.querySOP(map));
	}

	@Override
	public Result<Object> queryAllCategroy() {
		return ResultUtil.success(fieldManageMapper.queryAllCategroy());
	}

}
