package com.bonc.medicine.mapper.thumb;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description:
 * @author: hejiajun
 * @create: 2018-09-09 19:24
 **/
@Component
public interface ViewNumberMapper {

    public List<Map<String, Object>> queryViewNumber(Map<String, String> map);

    public List<Map<String, Object>> queryExistsObject(Map<String, String> map);

    public int addViewNumberCord(Map<String, String> map);

    public int updateViewNumber(Map<String, String> map);

}
