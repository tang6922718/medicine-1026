package com.bonc.medicine.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WeatherUtil
 * @Description 天气查询相关
 * @Author YQ
 * @Date 2018/10/7 10:56
 * @Version 1.0
 */
public class WeatherUtil {

    private static final ConcurrentHashMap<String, Map> weatherInfoCacheMap = new ConcurrentHashMap<>();


    /* *
     * @Description 从weatherInfoCacheMap中获取天气信息
     * @Date 15:30 2018/10/15
     * @Param [city_code]
     * @return java.lang.Object
     */
    public static Object getCache(String city_code) {

        // 如果缓冲中有该账号，则返回value
        if (weatherInfoCacheMap.containsKey(city_code)) {

            Map tempMap = weatherInfoCacheMap.get(city_code);

            if ((long) tempMap.get("currentTime") <= (long) tempMap.get("deadTime") ){ // 天气信息未过期

                return tempMap.get("weatherInfo");

            }else { // 天气信息过期   删除该条记录
                removeCache(city_code);
            }

        }

        // 如果缓存中没有该城市的天气信息，把把该城市天气信息缓存到concurrentHashMap中
        initCache(city_code);
        return weatherInfoCacheMap.get(city_code).get("weatherInfo");
    }


    /* *
     * @Description 查询天气信息 并把结果放在weatherInfoCacheMap中
     * @Date 15:31 2018/10/15
     * @Param [city_code]
     * @return void
     */
    private static void initCache(String city_code) {
        // 查询城市的天气信息

        JSONObject weatherInfo =null;
        try{
            URL url = new URL("http://www.weather.com.cn/data/sk/"+ city_code + ".html");
            HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
            ucon.connect();

            InputStream in = ucon.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String str = reader.readLine();
            System.out.println(str);

            weatherInfo = JSONObject.parseObject(str).getJSONObject("weatherinfo");
            System.out.println(weatherInfo);
        }catch(Exception e){
            e.printStackTrace();
        }


        JSONObject weatherInfo2 =null;
        try{
            URL url2 = new URL("http://www.weather.com.cn/data/cityinfo/"+ city_code + ".html");
            HttpURLConnection ucon2 = (HttpURLConnection) url2.openConnection();
            ucon2.connect();

            InputStream in2 = ucon2.getInputStream();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2,"UTF-8"));
            String str2 = reader2.readLine();
            System.out.println(str2);

            weatherInfo2 = JSONObject.parseObject(str2).getJSONObject("weatherinfo");
            System.out.println(weatherInfo2);
        }catch(Exception e){
            e.printStackTrace();
        }

        weatherInfo.putAll(weatherInfo2);

        // 加上时间  和 信息过期时间
        Map<String,Object> temp=new HashMap<>();
        long currentTime=System.currentTimeMillis();
        long deadTime=currentTime+2*60*60*1000;
        temp.put("currentTime",currentTime);
        temp.put("deadTime",deadTime);
        temp.put("weatherInfo",weatherInfo);

        weatherInfoCacheMap.put(city_code, temp);
    }


    /* *
     * @Description 删除weatherInfoCacheMap中的某条数据
     * @Date 15:32 2018/10/15
     * @Param [city_code]
     * @return void
     */
    public static void removeCache(String city_code) {

        weatherInfoCacheMap.remove(city_code);
    }

}

