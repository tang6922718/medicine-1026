package com.bonc.medicine.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: medicine
 * @description:时间转换的工具类
 * @author: hejiajun
 * @create: 2018-09-17 10:32
 **/
public class TimeFormatUtils {


    /**
    * @Description:（秒）时间戳转换成yyyy-MM-dd格式的时间
    * @Param: [imestamp] like : 1537151321
    * @return: java.lang.String ： 2018-09-17
    * @Author: hejiajun
    * @Date: 2018/9/17
    */
    public static String secendsToDate(String imestamp){

        Long millTime = Long.parseLong(imestamp) * 1000; //把秒转换成毫秒

        return millisecondToDate(millTime.toString());
    }

    /**
     * @Description:（秒）时间戳转换成yyyy-MM-dd格式的时间
     * @Param: [imestamp] like : 1537151321
     * @return: java.lang.String ： 2018-09-17 10:28:41
     * @Author: hejiajun
     * @Date: 2018/9/17
     */
    public static String secendsToDateHours(String imestamp){

        Long millTime = Long.parseLong(imestamp) * 1000; //把秒转换成毫秒

        return millisecondToDateHours(millTime.toString());
    }

    /**
     * @Description:（毫秒）时间戳转换成yyyy-MM-dd格式的时间
     * @Param: [imestamp] like : 1537151321000
     * @return: java.lang.String ： 2018-09-17
     * @Author: hejiajun
     * @Date: 2018/9/17
     */
    @SuppressWarnings("Duplicates")
    public static String millisecondToDate(String imestamp){

        Long millTime = Long.parseLong(imestamp); //把秒转换成毫秒

        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millTime);

        String formatedDate = adf.format(date);

        return formatedDate;
    }

    /**
     * @Description:（毫秒）时间戳转换成yyyy-MM-dd格式的时间
     * @Param: [imestamp] like : 1537151321000
     * @return: java.lang.String ： 2018-09-17 10:28:41
     * @Author: hejiajun
     * @Date: 2018/9/17
     */
    public static String millisecondToDateHours(String imestamp){

        Long millTime = Long.parseLong(imestamp); //把秒转换成毫秒

        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(millTime);

        String formatedDate = adf.format(date);

        return formatedDate;
    }


    public static void main(String[] args) {
        System.out.println(secendsToDateHours("1537151321"));
    }
}
