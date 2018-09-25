package com.bonc.medicine.mapper.user;

import com.bonc.medicine.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper {

	public List<Map> getAllUser();
	
	public int completeUserInfo(User user);

	public int signUpUserRoleRelation();

	public int signUpCareVarieties();

	public List<Map<String, Object>> login(Map<String, String> paramMap);

	public List<Map<String, Object>> loginSecond(Map<String, String> paramMap);

	public List<Map<String, Object>> getTableByPhone(String phone);

    public int updatePassword(Map<String, String> paramMap);

	public int signUp(Map<String, String> paramMap);

	public  List<Map<String, Object>> getIdByPhone(String phone);

}
