
package com.bonc.medicine.service.knowledgebase;

import java.util.Map;

public interface PharmacopoeiaInfoService {
	public int insertPharmacopoeiaInfo();

	public int addPharma(Map map);

	public int updatePhara(Map map);

	public Map<String,Object> pharaDetail(String id);

	public int updatePharaDetail(Map map);
}
