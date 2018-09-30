package com.bonc.medicine.service.user;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserManagerService {

	public void addBasic(Basicinfo basicinfo);
	
	public void addUser(JSONObject json);

	public int updateBasic(Integer id, String name, String sex, Integer age, String address, String img_url);

	public void addExpert(Expert expert);

	public void addCooperative(Cooperative cooperative);

	public Result<Object> basicInfo(Integer id);

	public Result<Object> userStatus(Integer id, String status);

	public Result<Object> fans(Integer id);

	public Result<Object> care(Integer id);

	public Result<Object> integral(Integer id);

	public Result<Object> issue(Integer id);

	public Result<Object> supply(Integer id);

	public Result<Object> purchase(Integer id);

	public Result<Object> field(Integer id);

	public List<Map<String, Object>> userlist(String tel, String role, String startTime, String endTime);

	public List<Map<String, Object>> coop_hrlp_list();

	public List<Map<String, Object>> integrallist();

	public List<Map<String, Object>> supplylist();

	public List<Map<String, Object>> purchaselist();

	public Result<Object> queryUserInfo(int userID);

	public Result<Object> updateUserPlantRole(Map<String,String> params);

	public Result<Object> updateUserCareVariety(Map<String,Object> params);

	public Map<String, String> activeDays(String userId);

	/**
	* @Description: userId要查询的用户的id  ‘1,2,3,4’ 多个这样用都逗号隔开，必须参数 如果没有就返回null
	 * queryType:目前已知  1：直播 2：专家
	* @Param: [userId, queryType]
	* @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
	* @Author: hejiajun
	* @Date: 2018/9/30 
	*/ 
	public List<Map<String, String>> queryInteractTimes( String userId);
}
