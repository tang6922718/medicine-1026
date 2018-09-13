package com.bonc.medicine.service.thumb;

import java.util.Map;

/**
 * @program: medicine-hn
 *
 * @description: 用户点赞接口
 *
 * @author: hejiajun
 *
 * @create: 2018-09-04 16:10
 **/
public interface ThumbService {

    public Map<String, Object> giveThumb(Map<String, String> paramMap);

    public Map<String, Object> removeThumb(Map<String, String> paramMap);

    public Map<String, Object> thumbNumber(Map<String, String> paramMap);

}
