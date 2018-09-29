package com.bonc.medicine.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.user.UserManagerMapper;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.service.user.UserManagerService;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserManagerServiceImpl implements UserManagerService {

	@Autowired
	private UserManagerMapper userManagerMapper;

	@Autowired
	private AttentionService attentionService;

	@Override
	public void addBasic(Basicinfo basicinfo) {
		userManagerMapper.addBasic(basicinfo);
	}

	@Override
	@Transactional
	public int updateBasic(Integer id, String name ,String sex, Integer age, String address,String img_url) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("name", name);
		map.put("sex", sex);
		map.put("age", age);
		map.put("address", address);
		map.put("img_url", img_url);
		Map map1 = new HashMap<>();
		map1.put("id", id);
		map1.put("name", name);
		map1.put("sex", "男".equals(sex) ? "0" : "1");
		map1.put("age", age);
		map1.put("address", address);
		map1.put("img_url", img_url);
		userManagerMapper.updateField_coop(map);
		userManagerMapper.updateField_coop_member(map1);
		return userManagerMapper.updateBasic(map);
	}

	@Override
	public void addExpert(Expert expert) {
		userManagerMapper.addExpert(expert);
	}

	@Override
	public void addCooperative(Cooperative cooperative) {
		userManagerMapper.addCooperative(cooperative);
	}

	@Override
	public Result<Object> basicInfo(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.basicInfo(map));
	}

	@Override
	public Result<Object> userStatus(Integer id, String status) {

		Map map = new HashMap<>();
		map.put("id", id);
		map.put("status", status);
		return ResultUtil.success(userManagerMapper.userStatus(map));
	}

	@Override
	public Result<Object> fans(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.fans(map));
	}

	@Override
	public Result<Object> care(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.care(map));
	}

	@Override
	public Result<Object> integral(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.integral(map));
	}

	@Override
	public Result<Object> issue(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.issue(map));
	}

	@Override
	public Result<Object> supply(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.supply(map));
	}

	@Override
	public Result<Object> purchase(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.purchase(map));
	}

	@Override
	public Result<Object> field(Integer id) {

		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(userManagerMapper.field(map));
	}

	@Override
	public List<Map<String, Object>> userlist(String tel, String role, String startTime, String endTime) {

		Map map = new HashMap<>();
		map.put("tel", tel);
		map.put("role", role);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return userManagerMapper.userlist(map);
	}

	@Override
	public List<Map<String, Object>> coop_hrlp_list() {

		return userManagerMapper.coop_hrlp_list();
	}

	@Override
	public List<Map<String, Object>> integrallist() {

		return userManagerMapper.integrallist();
	}

	@Override
	public List<Map<String, Object>> supplylist() {

		return userManagerMapper.supplylist();
	}

	@Override
	public List<Map<String, Object>> purchaselist() {

		return userManagerMapper.purchaselist();
	}

	@Override
	public Result<Object> queryUserInfo(int userID) {

		// return ResultUtil.success(userManagerMapper.queryUserInfo(userID));
		Map queryMap = userManagerMapper.queryUserInfo(userID);

		String userId = queryMap.get("id") + "";
		Map<String, Object> fansMap = attentionService.fansNum(userId);
		queryMap.put("fansNum", fansMap.get("fansNum"));
		Map<String, Object> hudongMap = userManagerMapper.getActiveAndhudong(Integer.parseInt(userId));
		queryMap.put("interact_count", hudongMap == null ? "0" : hudongMap.get("interact_count"));
		queryMap.put("active_count", hudongMap == null ? "0" : hudongMap.get("active_count"));
		Map<String, Object> pinZhongMap = userManagerMapper.getUserCarePinZhong(Integer.parseInt(userId));

		queryMap.put("loveMedicineName", pinZhongMap == null ? "" : pinZhongMap.get("loveMedicineName"));
		queryMap.put("loveMedicineID", pinZhongMap == null ? "" : pinZhongMap.get("loveMedicineID"));

		return ResultUtil.success(queryMap);
	}

	@Override
	public Result<Object> updateUserPlantRole(Map<String, String> params) {
		if (StringUtils.isEmpty(params.get("userID")) || StringUtils.isEmpty(params.get("isPlant"))){
			return ResultUtil.error(ResultEnum.MISSING_PARA);
		}

		if (Integer.parseInt(params.get("isPlant"))==0){ // 是否种植户   0 是    1 不是
			// 先删除 再插入
			int i=userManagerMapper.deleteUserPlantRole(Integer.parseInt(params.get("userID")));
			i=userManagerMapper.insertUserPlantRole(Integer.parseInt(params.get("userID")));
			if (i<=0){
				return ResultUtil.error(ResultEnum.UNKONW_ERROR);
			}
			return ResultUtil.success(ResultEnum.SUCCESS);

		}else {

			return ResultUtil.success(userManagerMapper.deleteUserPlantRole(Integer.parseInt(params.get("userID"))));
		}

	}

	@Override
	public Result<Object> updateUserCareVariety(Map<String, Object> params) {

		if (params.get("list")==null || params.get("userID")==null){
			return ResultUtil.error(ResultEnum.MISSING_PARA);
		}

		int userID= Integer.parseInt(params.get("userID").toString()) ;
		List careVariety= (List) params.get("list");

		int i=userManagerMapper.deleteUserCareVariety(userID);
		i=userManagerMapper.insertUserCareVariety(careVariety);

		return ResultUtil.success(i);
	}

	@Override
	@Transactional
	public void addUser(JSONObject json) {
		Map<String, Object> tempData = new HashMap<String, Object>();
		tempData.putAll(json);
		Basicinfo basicinfo = new Basicinfo();
		basicinfo.setAddress(tempData.get("address").toString());
		basicinfo.setAge(Integer.parseInt(tempData.get("age").toString()));
		basicinfo.setName(tempData.get("name").toString());
		basicinfo.setPassword(tempData.get("password").toString());
		basicinfo.setSex(tempData.get("sex").toString());
		basicinfo.setTelephone(tempData.get("telephone").toString());
		basicinfo.setRole(tempData.get("role").toString());
		userManagerMapper.addBasic(basicinfo);
		int id = basicinfo.getId();// user_id
		String[] role = basicinfo.getRole().split(",");
		for (int i = 0; i < role.length; i++) {
			userManagerMapper.addUserRoleRel(id, Integer.parseInt(role[i]));
		}
		if (basicinfo.getRole().toString().contains("5")) {
			Expert expert = new Expert();
			expert.setSpec_id(id);
			expert.setName(tempData.get("spec_name").toString());
			expert.setEmployment_age(Integer.parseInt(tempData.get("employment_age").toString()));
			expert.setEducation(tempData.get("education").toString());
			expert.setProfessional_direction(tempData.get("professional_direction").toString());
			expert.setTitle(tempData.get("title").toString());
			expert.setCompany(tempData.get("company").toString());
			expert.setExpertise_field(tempData.get("expertise_field").toString());
			expert.setCat_rel(tempData.get("cat_rel").toString());
			expert.setSubject_rel(tempData.get("subject_rel").toString());
			expert.setDetail(tempData.get("detail").toString());
			userManagerMapper.addExpert(expert);
			String[] cat_rel = expert.getCat_rel().split(",");
			for (int i = 0; i < cat_rel.length; i++) {
				userManagerMapper.addCatRel(id, Integer.parseInt(cat_rel[i]));
			}
			String[] subject_rel = expert.getSubject_rel().split(",");
			for (int i = 0; i < subject_rel.length; i++) {
				userManagerMapper.addSubject_rel(id, Integer.parseInt(subject_rel[i]));
			}
		}
		if (basicinfo.getRole().toString().contains("3")) {
			Cooperative cooperative = new Cooperative();
			cooperative.setName(tempData.get("coo_name").toString());
			cooperative.setOfficial_user_id(id);
			cooperative.setOfficial_user_name(tempData.get("official_user_name").toString());
			cooperative.setAddress(tempData.get("coo_address").toString());
			cooperative.setImg_url(tempData.get("img_url").toString());
			cooperative.setCultivar(tempData.get("cultivar").toString());
			cooperative.setIntroduce(tempData.get("introduce").toString());
			userManagerMapper.addCooperative(cooperative);
		}
	}

}
