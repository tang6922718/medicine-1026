package com.bonc.medicine.service.information.impl;

import com.bonc.medicine.mapper.information.InfoMapper;
import com.bonc.medicine.mapper.knowledgebase.AuditMapper;
import com.bonc.medicine.service.information.InfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class InfoServiceImpl implements InfoService {

    @Autowired(required = false)
    private InfoMapper infoMapper;


    @Override
    public List<Map> getAllInfo(String catCode,  String pageNum,String pageSize,String title,String status,String source_code,String operationClass) {
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return infoMapper.getAllInfo(catCode,title,status,source_code,operationClass);
    }

    @Override
    public int addInfo(@RequestBody Map<String, Object> map) {
        return infoMapper.addInfo(map);
    }

    @Override
    public Map infoDetailById(String id) {
        return infoMapper.infoDetailById(id);
    }

    @Override
    public int infoEditById(Map<String, Object> map) {
//        auditMapper.czAudit(map);
//        auditMapper.addAudit(map);
        return infoMapper.infoEditById(map);
    }


    /**
     * 如果存在更新操作，则更新info_basic表，然后对km_audit进行插入操作
     * @param map
     * @return
     */
    @Override
    public int infoAudit(Map<String, Object> map) {
        infoMapper.infoEditById(map);
        return  infoMapper.infoAuditInsert(map);

    }

    @Override
    public List<Map> infoClass(Map<String, Object> map) {
        return infoMapper.infoClass(map);
    }

    @Override
    public Map infoReadCount(Map map) {
        infoMapper.updateReadCount(map);
        return infoMapper.infoReadCount(map);
    }

    @Override
    public int delInfo(Map<String, Object> map) {
        return infoMapper.delInfo(map);
    }

    @Override
    public int infoRepeal(String id) {
        return infoMapper.infoRepeal(id);
    }
}
