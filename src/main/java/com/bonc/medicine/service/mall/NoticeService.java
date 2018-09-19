package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;


public interface NoticeService {

	public List<Map<String, Object>> systemInfo(Integer user_id, String [] notice_role_type_array,String is_coop);

	public List<Map<String, Object>> findRole(Integer user_id);

	public List<Map<String, Object>> meetProfessor(Integer user_id);

	public List<Map<String, Object>> dyanimic(Integer user_id);

	public List<Map<String, Object>> message(Integer user_id);

}
