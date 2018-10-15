package com.bonc.medicine.utils;

/**
 * @Description:用户的验证码的key的工具类
 * @Author: hejiajun
 * @Date: 2018/9/6
 */
public class CodeKeyUtil {

    private static String SPLIT = ":";

    private static String CODE_PRE = "VCODE";

    private static String CODE_SUB = "CASUAL";

    /**
     * @Description: 验证码的KEY
     * @Param: [userId]
     * @return: java.lang.String
     * @Author: hejiajun
     * @Date: 2018/9/6
     */
    public static String getCodeKey(String phone) {

        return CODE_PRE + SPLIT + phone  + SPLIT + CODE_SUB;
    }

}
