package com.bonc.medicine.mapper.mall;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface NoticeMapper {

	public List<Map<String, Object>> systemInfo(Map map);
	
	public List<Map<String, Object>> findRole(Map map);
	
	public List<Map<String, Object>> meetProfessor(Map map);
	
	public List<Map<String, Object>> dyanimic(Map map);
	
	public List<Map<String, Object>> message(Map map);
	
	public void scanNotice();
	
}
