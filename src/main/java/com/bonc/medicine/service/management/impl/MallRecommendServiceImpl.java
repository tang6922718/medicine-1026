package com.bonc.medicine.service.management.impl;

import com.bonc.medicine.mapper.management.MallRecommendMapper;
import com.bonc.medicine.service.management.MallRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/5.
 */
@Repository
public class MallRecommendServiceImpl implements MallRecommendService {

    @Autowired
    private MallRecommendMapper mallRecommendMapper;

    @Override
    public List<Map<String, Object>> searchMallRecommend(Map map) {
        return mallRecommendMapper.searchMallRecommend(map);
    }

    @Override
    public int enableMallRecommend(String id) {
        return mallRecommendMapper.enableMallRecommend(id);
    }

    @Override
    public int stopMallRecommend(String id) {
        return mallRecommendMapper.stopMallRecommend(id);
    }

    @Override
    public int deleteMallRecommend(String id) {
        return mallRecommendMapper.deleteMallRecommend(id);
    }

    @Override
    public Map<String, Object> showGoodsById(String id,String site) {
        Map map = new HashMap();
        map.put("id",id);
        map.put("site",site);
        return mallRecommendMapper.showGoodsById(map);
    }

    @Override
    public int mallRecommend(Map map){
        return mallRecommendMapper.mallRecommend(map);
    }

    @Override
    public int editMallRecommend(Map map) {
        return mallRecommendMapper.editMallRecommend(map);
    }

    @Override
    public Map<String, Object> showMallRecommend(String id) {
        return mallRecommendMapper.showMallRecommend(id);
    }

    @Override
    public Map<String, Object> MallRecommendDetail(String id) {
        return mallRecommendMapper.MallRecommendDetail(id);
    }

    @Override
    public List qualityRecommend(String... ids) {
        return mallRecommendMapper.qualityRecommend(ids);
    }
}
