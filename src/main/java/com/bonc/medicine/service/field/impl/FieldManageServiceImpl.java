package com.bonc.medicine.service.field.impl;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Field;
import com.bonc.medicine.entity.field.FieldRecord;
import com.bonc.medicine.mapper.field.FieldManageMapper;
import com.bonc.medicine.service.field.FieldManageService;
import com.bonc.medicine.service.thumb.IntegralService;
import com.bonc.medicine.utils.ExchangeCategroyNameID;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.WeatherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service("fieldManageService")
public class FieldManageServiceImpl implements FieldManageService {

	@Autowired
	FieldManageMapper fieldManageMapper;

	@Autowired
	private IntegralService integralService;

	@Override
	@Transactional
	public Result<Object> addField(Field tempData) {
		// // 所有品种信息
		// List<Map> allCategroyInfo=fieldManageMapper.queryAllCategroy();

		// 根据userID 和 creatorID 是否一样来判断是否是技术指导 (避免技术员给自己新建种植结果也为技术指导)
		if (tempData.getUser_id()==tempData.getCreator_id()){
			tempData.setGuide_flag("1");   // 0 是   1  不是
		}

		Map map = new HashMap();
		map = fieldManageMapper.queryUserInfo(tempData.getUser_id());
		if (map != null) {
			tempData.setUser_name((String) map.get("name"));
			tempData.setUser_tel((String) map.get("telephone"));
		}


		// 查询所属合作社
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

		//积分代码
		Map<String, String> ppparamMap = new HashMap<>();
		//userId;actionCode
		ppparamMap.put("userId", tempData.getUser_id() + "");
		ppparamMap.put("actionCode", "REGISTER_PLANTIN");
		try{

			integralService.addIntegralHistory(ppparamMap);
		}catch (Exception e){
			System.out.println("ERROR ：新建田间操作中---增加积分异常");
		}


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
		//根据记录人ID 和 地块所有者的ID 是否一样判断是否技术指导  (避免技术员给自己的地块进行农事操作结果为技术指导)
		Map map = new HashMap();
		map.put("field_id", tempData.getField_id());
		List<Map> list1 = new ArrayList<Map>();
		list1 = fieldManageMapper.getfield(map);
		if (Integer.parseInt(list1.get(0).get("user_id").toString())==tempData.getRecord_user_id()){
			tempData.setGuide_flag("1");  // 0 是   1  不是
		}



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
		map.put("variety_id", categroyID);
		map.put("id", stepID);
		return ResultUtil.success(fieldManageMapper.querySOP(map));
	}

	@Override
	public Result<Object> queryAllCategroy() {
		return ResultUtil.success(fieldManageMapper.queryAllCategroy());
	}

	@Override
	public Result<Object> queryWeatherInfo(String city_name) {
		List<Map> cityInfoList=fieldManageMapper.queryCityNameAndCityCode();
		String city_code=ExchangeCategroyNameID.CityNameToCode(city_name,cityInfoList);

		if ("".equals(city_code)){
			return ResultUtil.error(500,"地级市编码获取错误");
		}

		return ResultUtil.success(WeatherUtil.getCache(city_code));
	}

}
