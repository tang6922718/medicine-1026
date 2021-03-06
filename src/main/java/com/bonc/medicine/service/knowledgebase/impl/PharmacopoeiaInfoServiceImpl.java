package com.bonc.medicine.service.knowledgebase.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.knowledgebase.PharmacopoeiaInfoMapper;
import com.bonc.medicine.service.knowledgebase.PharmacopoeiaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@Service
public class PharmacopoeiaInfoServiceImpl implements PharmacopoeiaInfoService {

    @Autowired
    private PharmacopoeiaInfoMapper pharmacopoeiaInfoMapper;

    @Override
    public int insertPharmacopoeiaInfo() {
        return pharmacopoeiaInfoMapper.insertPharmacopoeiaInfo();
    }

    @Override
    public int addPharma(Map map) {
        return pharmacopoeiaInfoMapper.addPharma(map);
    }

    @Override
    public int updatePhara(Map map) {
        return pharmacopoeiaInfoMapper.updatePhara(map);
    }

    @Override
    public Map<String, Object> pharaDetail(String id) {
        Map<String, Object> reMap = pharmacopoeiaInfoMapper.pharaDetail(id);
        if (reMap == null || reMap.size() == 0 || reMap.isEmpty()){
            throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
        }
        return reMap;
    }
    
    @Override
    public Map<String, Object> pharaDetailBack(String id) {
        Map<String, Object> reMap = pharmacopoeiaInfoMapper.pharaDetailBack(id);
        if (reMap == null || reMap.size() == 0 || reMap.isEmpty()){
            throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
        }
        return reMap;
    }

    @Override
    public int updatePharaDetail(Map map) {
        return pharmacopoeiaInfoMapper.updatePharaDetail(map);
    }


}
