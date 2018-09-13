package com.bonc.medicine.mapper.thumb;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: ${description}
 * @author: hejiajun
 * @create: 2018-09-05 19:05
 **/
@Component
public interface ThumbMapper {

    public int giveThumb(Map<String, String> paramMap);

    public int removeThumb(Map<String, String> paramMap);

    public List<Map<String, Object>> thumbNumber(Map<String, String> paramMap);
}
