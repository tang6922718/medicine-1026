package com.bonc.medicine.service.thumb;

import java.util.Map;

/**
 * @program: medicine-hn
 * @description:
 * @author: hejiajun
 * @create: 2018-09-09 19:23
 **/
public interface ViewNumberService {

    public Map<String, Object> queryViewNumber(Map<String, String> map);


    public Map<String, Object> addOrUpdateViewNumberCord(Map<String, String> map);


}
