package com.bonc.medicine.service.management;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
public interface MallRecommendService {
    public List<Map<String,Object>> searchMallRecommend(Map map);

    public int enableMallRecommend(String id);

    public int stopMallRecommend(String id);

    public int deleteMallRecommend(String id);

    public Map<String,Object> showGoodsById(String id);

    public int mallRecommend(Map map);

    public int editMallRecommend(Map map);

    public Map<String,Object> showMallRecommend(String id);

    public Map<String,Object>  MallRecommendDetail(String id);

}
