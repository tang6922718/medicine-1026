package com.bonc.medicine.service.knowledgebase.impl;

import com.bonc.medicine.mapper.knowledgebase.AuditMapper;
import com.bonc.medicine.service.knowledgebase.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditMapper auditMapper;

    @Override
    public int insertAudit() {
        return auditMapper.insertAudit();
    }

    @Override
    public int addAudit(Map map) {
        return auditMapper.addAudit(map);
    }

    @Override
    public int czAudit(Map map){return auditMapper.czAudit(map);};
}
