package com.bonc.medicine.utils;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import org.apache.commons.lang.StringUtils;

public class RedisKeyUtil {
	
	private static String SPLIT = ":";

    private static String BIZ_LIKE = "LIKE";

    private static String BE_THUMB = "THUMB";

    private static String BIZ_DISLIKE = "DISLIKE";

    private static String USER_TYPE = "USER";

    private static String PRO_TYPE = "PRO";

    private static String PRO_MARK = "1";

    private static String USER_MARK = "0";

    private static String ATTENTION_PREFIX = "ATT";

    private static String FANS_PREFIX = "FANS";

    private static String USER_PRE = "USERINFO";



    /**
    * @Description: 获取关注的普通用户的key
    * @Param: [userid, entityId]
    * @return: java.lang.String
    * @Author: hejiajun
    * @Date: 2018/9/3 
    */ 
    public static String  getUserAttentionKey(String userid){
        return ATTENTION_PREFIX + SPLIT + userid + SPLIT + USER_TYPE;
    }

    /**
     * @Description: 获取关注的专家用户的key
     * @Param: [userid, entityId]
     * @return: java.lang.String
     * @Author: hejiajun
     * @Date: 2018/9/3
     */
    public static String  getProAttentionKey(String userid){
        return ATTENTION_PREFIX + SPLIT + userid + SPLIT + PRO_TYPE ;
    }

    public static String  getAttentionKey(String userid, String type){
        if (null != type && StringUtils.equals(type, PRO_MARK)){
            return getProAttentionKey(userid);
        }else if (null != type && StringUtils.equals(type, USER_MARK)){
            return getUserAttentionKey(userid);
        }else{
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }
    }

    public static String  getFansKey(String userid){
        return FANS_PREFIX + SPLIT + userid  + USER_TYPE;
    }




    /**
    * @Description: 获取点赞的key 被点赞人为KEY  点赞人为SET集合
    * @Param: [thumbedId, type]
    * @return: java.lang.String
    * @Author: hejiajun
    * @Date: 2018/9/5 
    */ 
    public static String getThumbKey(String acceptThumbId, String type){
        return BE_THUMB + SPLIT + acceptThumbId + SPLIT + type;
    }




    public static String getDisLikeKey(int entityId, String type){
        return BIZ_DISLIKE + SPLIT + type + SPLIT + String.valueOf(entityId);
    }

    public static String getLikeKey(int entityId, String type){
        return BIZ_LIKE + SPLIT + type + SPLIT + String.valueOf(entityId);
    }

    public static String getUserInfoKey(String token) {
        return USER_PRE + SPLIT + token;
    }

}
