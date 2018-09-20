package com.bonc.medicine.service.user;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;

import java.util.List;
import java.util.Map;

public interface UserManagerService {

	public void addBasic(Basicinfo basicinfo);

	public void addUserRoleRel(Integer id, Integer user_role_id);
	
	public int updateBasic(Integer id,String sex,Integer age,String address);

	public void addCatRel(Integer id, Integer cat_rel_id);

	public void addSubject_rel(Integer id, Integer subject_rel_id);

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
}
