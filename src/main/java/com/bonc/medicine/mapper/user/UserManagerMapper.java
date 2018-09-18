package com.bonc.medicine.mapper.user;

import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserManagerMapper {

	public void addBasic(Basicinfo basicinfo);

	public void addUserRoleRel(Map map);

	public void addCatRel(Map map);

	public void addSubject_rel(Map map);

	public void addExpert(Expert expert);

	public void addCooperative(Cooperative cooperative);

	public List<Map> basicInfo(Map map);

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

	public Map queryUserInfo(int userID);

}
