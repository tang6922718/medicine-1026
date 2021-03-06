package com.bonc.medicine.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.CoopMember;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.user.UserManagerMapper;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.service.thumb.IntegralService;
import com.bonc.medicine.service.user.UserManagerService;
import com.bonc.medicine.utils.ResultUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	@Autowired
	private IntegralService integralService;

	@Override
	public void addBasic(Basicinfo basicinfo) {
		userManagerMapper.addBasic(basicinfo);
	}

	@Override
	@Transactional
	public int updateBasic(Integer id, String name, String sex, Integer age, String address, String img_url) {
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
		// 积分代码
		Map<String, String> ppparamMap = new HashMap<>();
		// userId;actionCode
		ppparamMap.put("userId", id + "");
		ppparamMap.put("actionCode", "COMPLETE_INFORMA");
		try {

			integralService.addIntegralHistory(ppparamMap);
		} catch (Exception e) {
			System.out.println("ERROR ：修改用户信息---增加积分异常");
		}
		return userManagerMapper.updateBasic(map);
	}

	@Override
	public int getTel(String tel) {
		Map map = new HashMap<>();
		map.put("tel", tel);
		return userManagerMapper.getTel(map);
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
	public List<Map<String, Object>> basicInfo2(String id) {
		
		Map map = new HashMap<>();
		map.put("id", id);
		return userManagerMapper.basicInfo2(map);
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
	public Map<String, Object> queryUserInfo(String userID) {

		// return ResultUtil.success(userManagerMapper.queryUserInfo(userID));
		Map<String, Object> queryMap = userManagerMapper.queryUserInfo(userID);

		String userId = queryMap.get("id") + "";
		Map<String, Object> fansMap = attentionService.fansNum(userId);
		queryMap.put("fansNum", fansMap.get("fansNum"));

		// Map<String, Object> hudongMap =
		// userManagerMapper.getActiveAndhudong(Integer.parseInt(userId))
		List<Map<String, String>> interractMap = queryInteractTimes(userId);// interactNumber
		queryMap.put("interact_count", interractMap.get(0).get("interactNumber"));

		Map activeMap = activeDays(userId);
		queryMap.put("active_count", activeMap == null ? "0" : activeMap.get("acDays"));
		Map<String, Object> pinZhongMap = userManagerMapper.getUserCarePinZhong(Integer.parseInt(userId));

		queryMap.put("loveMedicineName", pinZhongMap == null ? "" : pinZhongMap.get("loveMedicineName"));
		queryMap.put("loveMedicineID", pinZhongMap == null ? "" : pinZhongMap.get("loveMedicineID"));

		return queryMap;
	}

	@Override
	public Result<Object> updateUserPlantRole(Map<String, String> params) {
		if (StringUtils.isEmpty(params.get("userID")) || StringUtils.isEmpty(params.get("isPlant"))) {
			return ResultUtil.error(ResultEnum.MISSING_PARA);
		}

		if (Integer.parseInt(params.get("isPlant")) == 1) { // 是否种植户 0 不是 1 是
			// 先删除 再插入
			int i = userManagerMapper.deleteUserPlantRole(Integer.parseInt(params.get("userID")));
			i = userManagerMapper.insertUserPlantRole(Integer.parseInt(params.get("userID")));
			if (i <= 0) {
				return ResultUtil.error(ResultEnum.UNKONW_ERROR);
			}
			return ResultUtil.success(ResultEnum.SUCCESS);

		} else {

			return ResultUtil.success(userManagerMapper.deleteUserPlantRole(Integer.parseInt(params.get("userID"))));
		}

	}

	@Override
	public Result<Object> updateUserCareVariety(Map<String, Object> params) {

		if (params.get("userID") == null) {
			return ResultUtil.error(ResultEnum.MISSING_PARA);
		}

		int userID = Integer.parseInt(params.get("userID").toString());

		if (params.get("list") == null) { // 关心品种为空时 删除用户关心品种表中的数据

			return ResultUtil.success(userManagerMapper.deleteUserCareVariety(userID));

		} else { // 关心品种不为空时 先删 再插入数据

			List careVariety = (List) params.get("list");
			int i = userManagerMapper.deleteUserCareVariety(userID);
			i = userManagerMapper.insertUserCareVariety(careVariety);
			return ResultUtil.success(i);
		}

	}

	@Override
	@Transactional
	public void addUser(JSONObject json) {
		Map<String, Object> tempData = new HashMap<String, Object>();
		tempData.putAll(json);
		Basicinfo basicinfo = new Basicinfo();
		basicinfo.setAddress(tempData.get("address").toString());
		if (!tempData.get("age").toString().equals("")) {
			basicinfo.setAge(Integer.parseInt(tempData.get("age").toString()));
		}
		basicinfo.setName(tempData.get("name").toString());
		basicinfo.setPassword(DigestUtils.md5Hex(tempData.get("password").toString()));
		basicinfo.setSex(tempData.get("sex").toString());
		basicinfo.setTelephone(tempData.get("telephone").toString());
		basicinfo.setRole(tempData.get("role").toString());
		userManagerMapper.addBasic(basicinfo);
		int id = basicinfo.getId();// user_id
		if (!basicinfo.getRole().equals("")) {
			String[] role = basicinfo.getRole().split(",");
			for (int i = 0; i < role.length; i++) {
				userManagerMapper.addUserRoleRel(id, Integer.parseInt(role[i]));
			}
		}
		if (basicinfo.getRole().toString().contains("5")) {
			Expert expert = new Expert();
			expert.setSpec_id(id);
			expert.setName(tempData.get("name").toString());
			if (!tempData.get("employment_age").toString().equals("")) {
				expert.setEmployment_age(Integer.parseInt(tempData.get("employment_age").toString()));
			}
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
			if (expert.getCat_rel() != null && !expert.getCat_rel().toString().equals("")) {
				for (int i = 0; i < cat_rel.length; i++) {
					userManagerMapper.addCatRel(id, Integer.parseInt(cat_rel[i]));
				}
			}
			if (expert.getSubject_rel() != null && !expert.getSubject_rel().toString().equals("")) {
				String[] subject_rel = expert.getSubject_rel().split(",");
				for (int i = 0; i < subject_rel.length; i++) {
					userManagerMapper.addSubject_rel(id, Integer.parseInt(subject_rel[i]));
				}
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
			cooperative.setTelphone(tempData.get("telephone").toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String time = format.format(date);
			cooperative.setEstablish_date(time);
			userManagerMapper.addCooperative(cooperative);

			CoopMember coopMember = new CoopMember();
			coopMember.setName(tempData.get("name").toString());
			coopMember.setAddress(tempData.get("address").toString());
			if (!tempData.get("age").toString().equals("")) {
				coopMember.setAge(Integer.parseInt(tempData.get("age").toString()));
			}
			coopMember.setSex(tempData.get("sex").toString());
			coopMember.setCoop_id(cooperative.getId());
			coopMember.setTelephone(tempData.get("telephone").toString());
			coopMember.setUser_id(id);
			userManagerMapper.addCoopMember(coopMember);
		}
	}

	public Map<String, Object> activeDays(String userId) {
		Map<String, Object> reMap = userManagerMapper.activeDays(userId);
		if (reMap == null || reMap.isEmpty()) {
			reMap.put("acDays", "0");
		}
		return reMap;
	}

	public List<Map<String, Object>> activeDaysForBack(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
		}

		String[] ids = userId.split(",");
		List<Map<String, Object>> reMap = userManagerMapper.activeDaysForBack(userId);
		List<Map<String, Object>> reListMap = new ArrayList<>();
		if (null == reMap || reMap.size() == 0 || null == reMap.get(0) || reMap.get(0).isEmpty()) {
			for (String id : ids) {
				Map<String, Object> mmap = new HashMap<>();
				mmap.put("user_id", id);
				mmap.put("acDays", "0");
				reListMap.add(mmap);
			}
			return reListMap;
		}
		List<String> idList = new ArrayList<>();

		for (String arrayId : ids) {
			idList.add(arrayId);
		}

		List<String> queryList = new ArrayList<>();
		for (Map<String, Object> map : reMap) {
			String iuse_id = map.get("user_id") + "";
			queryList.add(iuse_id);
		}

		for (String idid : idList) {
			if (!queryList.contains(idid)) {
				Map<String, Object> inaMap = new HashMap<>();
				inaMap.put("user_id", idid);
				inaMap.put("acDays", "0");
				reMap.add(inaMap);
			}
		}

		return reMap;
	}

	public List<Map<String, String>> queryInteractTimes(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		String[] proIds = userId.split(",");
		String paramString = userId.trim();

		List<Map<String, Object>> queryList = userManagerMapper.queryInteractTimes(paramString);

		// 向外返回的list
		List<Map<String, String>> reList = new ArrayList<>();

		// 如果数据库中没有专家的互动信息，每个专家的互动信息 设置为“0”
		if (queryList == null || queryList.isEmpty() || queryList.get(0) == null) {
			for (String inProdId : proIds) {

				Map<String, String> middleMap = new HashMap();
				middleMap.put("proId", inProdId);
				middleMap.put("interactNumber", "0");
				reList.add(middleMap);
			}
		}

		// user_id; interact_number
		for (Map<String, Object> queryMap : queryList) {
			Map<String, String> middleMap = new HashMap();

			middleMap.put("proId", queryMap.get("user_id") + "");
			middleMap.put("interactNumber",
					queryMap.get("interact_number") == null ? "0" : queryMap.get("interact_number") + "");
			reList.add(middleMap);
		}

		return reList;
	}
}
