package com.bonc.medicine.mapper.management;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@Component
public interface MallRecommendMapper {
    public List<Map<String,Object>> searchMallRecommend(Map map);

    public int enableMallRecommend(String id);

    public int stopMallRecommend(String id);

    public int deleteMallRecommend(String id);

}
