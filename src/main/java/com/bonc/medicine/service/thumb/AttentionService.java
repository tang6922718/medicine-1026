package com.bonc.medicine.service.thumb;

import java.util.Map;

/**
 * @program: medicine-hn
 *
 * @description: 用户关注接口
 *
 * @author: hejiajun
 *
 * @create: 2018-09-04 16:10
 **/
public interface AttentionService {

    public Map<String, String> attentionRelation(Map<String, String> param);

    public Map<String, Object> attentionList(Map<String, String> param);

    public long giveAttention(Map<String, String> param);

    public Map<String, Object> fansNum(String userId);

    public long removeAttention(Map<String, String> param);

    public Map<String, Object> fansList(String userId);


    public Map<String, String> myAttentionNumber(String userId);
}
