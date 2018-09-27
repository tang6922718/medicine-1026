package com.bonc.medicine.utils;

/**
 * @Description:浏览数，redis，key的工具类
 * @Author: hejiajun
 * @Date: 2018/9/6
 */
public class ViewNumberKeyUtil {

    private static String SPLIT = ":";

    private static String PRE = "VIEW";

    private static String SUB = "NUMBER";

    public static String getViewNumberKey(String objectId, String ObjectType) {

        return PRE + SPLIT + objectId + SPLIT + ObjectType + SPLIT + SUB;
    }


}
