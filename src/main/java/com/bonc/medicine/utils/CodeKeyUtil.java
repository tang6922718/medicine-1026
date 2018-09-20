package com.bonc.medicine.utils;

/**
 * @Description:用户的验证码的key的工具类
 * @Author: hejiajun
 * @Date: 2018/9/6
 */
public class CodeKeyUtil {

    private static String SPLIT = ":";

    private static String INTEGRAL_PRE = "INTEGRAL";

    private static String INTEGRAL_RULE_SUB = "RULES";

    /**
     * @Description: 验证码的KEY
     * @Param: [userId]
     * @return: java.lang.String
     * @Author: hejiajun
     * @Date: 2018/9/6
     */
    public static String getIntegralKey(String userId) {

        return INTEGRAL_PRE + SPLIT + userId;
    }

}
