package com.bonc.medicine.mapper.user;

import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.CoopMember;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserManagerMapper {

	public void addBasic(Basicinfo basicinfo);

	public int getTel(Map map);
	
	public int updateBasic(Map map);

	public int updateField_coop(Map map);

	public int updateField_coop_member(Map map);

	public void addUserRoleRel(@Param("user_id") int id, @Param("role_id") int role);

	public void addCatRel(@Param("id") int user_id, @Param("cat_rel_id") int id);

	public void addSubject_rel(@Param("id") int user_id,@Param("subject_rel_id") int id);

	public void addExpert(Expert expert);

	public void addCooperative(Cooperative cooperative);
	
	public void addCoopMember(CoopMember coopMember);

	public List<Map> basicInfo(Map map);
	
	public List<Map<String, Object>> basicInfo2(Map map);

	public int userStatus(Map map);

	public List<Map> fans(Map map);

	public List<Map> care(Map map);

	public List<Map> integral(Map map);

	public List<Map> issue(Map map);

	public List<Map> supply(Map map);

	public List<Map> purchase(Map map);

	public List<Map> field(Map map);

	public List<Map<String, Object>> userlist(Map map);

	public List<Map<String, Object>> coop_hrlp_list();

	public List<Map<String, Object>> integrallist();

	public List<Map<String, Object>> supplylist();

	public List<Map<String, Object>> purchaselist();

	public Map<String, Object> queryUserInfo(String userID);

	public Map getActiveAndhudong(int userID);

	public Map getUserCarePinZhong(int userID);

	public int deleteUserPlantRole(int userID);

	public int insertUserPlantRole(int userID);

	public int deleteUserCareVariety(int userID);

	public int insertUserCareVariety(List userCareVariety);

	public Map activeDays(String userId);
	public List<Map<String, Object>> activeDaysForBack(@Param("userId")String userId);

	public List<Map<String, Object>> queryInteractTimes(@Param("proId")String proId);

}
