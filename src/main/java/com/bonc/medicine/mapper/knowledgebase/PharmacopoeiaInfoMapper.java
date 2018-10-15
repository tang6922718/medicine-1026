
package com.bonc.medicine.mapper.knowledgebase;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface PharmacopoeiaInfoMapper {
	public int insertPharmacopoeiaInfo();

	public int addPharma(Map map);

	public int updatePhara(Map map);

	public Map<String,Object> pharaDetail(String id);
	
	public Map<String,Object> pharaDetailBack(String id);

	public int updatePharaDetail(Map map);
}
