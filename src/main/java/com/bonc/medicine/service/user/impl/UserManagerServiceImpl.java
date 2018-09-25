package com.bonc.medicine.service.user.impl;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import com.bonc.medicine.mapper.user.UserManagerMapper;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.service.user.UserManagerService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	public int updateBasic(Integer id, String sex, Integer age, String address,String img_url) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("sex", sex);
		map.put("age", age);
		map.put("address", address);
		map.put("img_url", img_url);
		Map map1 = new HashMap<>();
		map1.put("id", id);
		map1.put("sex", "男".equals(sex) ? "0" : "1");
		map1.put("age", age);
		map1.put("address", address);
		map1.put("img_url", img_url);
		userManagerMapper.updateField_coop(map);
		userManagerMapper.updateField_coop_member(map1);
		return userManagerMapper.updateBasic(map);
	}

	@Override
	public void addUserRoleRel(Integer id, Integer user_role_id) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("role_id", user_role_id);
		userManagerMapper.addUserRoleRel(map);
	}

	@Override
	public void addCatRel(Integer id, Integer cat_rel_id) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("cat_rel_id", cat_rel_id);
		userManagerMapper.addCatRel(map);
	}

	@Override
	public void addSubject_rel(Integer id, Integer subject_rel_id) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("subject_rel_id", subject_rel_id);
		userManagerMapper.addSubject_rel(map);
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

}
