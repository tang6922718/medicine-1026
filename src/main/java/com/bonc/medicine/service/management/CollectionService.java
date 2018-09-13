package com.bonc.medicine.service.management;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/6.
 */
public interface CollectionService {

    public int collect(Map map);

    public List<Map<String,Object>> searchInfoByCollect();

    public List<Map<String,Object>> searchSupplyByCollect();

    public List<Map<String,Object>> searchVideoByCollect();
}
