package com.bonc.medicine.service.knowledgebase;

import java.util.Map;

public interface AuditService {
	public int insertAudit();

	public int addAudit(Map map);

	public int updateAudit(Map map);

	public int czAudit(Map map);
}
