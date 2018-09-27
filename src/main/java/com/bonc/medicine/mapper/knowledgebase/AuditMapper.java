
package com.bonc.medicine.mapper.knowledgebase;

import org.springframework.stereotype.Component;
import sun.misc.Contended;

import java.util.Map;

@Component
public interface AuditMapper {
	public int insertAudit();

	public int addAudit(Map map);

	public int czAudit(Map map);
}
