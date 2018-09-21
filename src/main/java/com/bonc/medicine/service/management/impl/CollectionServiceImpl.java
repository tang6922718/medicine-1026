package com.bonc.medicine.service.management.impl;

import com.bonc.medicine.mapper.management.CollectionMapper;
import com.bonc.medicine.service.management.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/6.
 */
@Repository
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public int collect(Map map) {
        return collectionMapper.collect(map);
    }

    @Override
    public List<Map<String, Object>> searchInfoByCollect() {
        return collectionMapper.searchInfoByCollect();
    }

    @Override
    public List<Map<String, Object>> searchSupplyByCollect() {
        return collectionMapper.searchSupplyByCollect();
    }

    @Override
    public List<Map<String, Object>> searchVideoByCollect() {
        return collectionMapper.searchVideoByCollect();
    }

    @Override
    public Map<String, Object> infoBasicDetail(String id) {
        return collectionMapper.infoBasicDetail(id);
    }

    @Override
    public Map<String, Object> videoCourseDetail(String id) {
        return collectionMapper.videoCourseDetail(id);
    }

    @Override
    public Map<String, Object> mallSupplyDetail(String id) {
        return collectionMapper.mallSupplyDetail(id);
    }

    @Override
    public Map<String, Object> mallPurchaseDetail(String id) {
        return collectionMapper.mallPurchaseDetail(id);
    }

    @Override
    public Map<String, Object> specCaseDetail(String id) {
        return collectionMapper.specCaseDetail(id);
    }


}
