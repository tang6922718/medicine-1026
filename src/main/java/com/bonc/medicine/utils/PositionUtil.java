package com.bonc.medicine.utils;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName PositionUtil
 * @Description
 * @Author YQ
 * @Date 2018/9/28 11:12
 * @Version 1.0
 */
@RestController
public class PositionUtil {

    @GetMapping("/positon/{lng}/{lat}")
    public Result<Object> getAddress(@PathVariable String lng, @PathVariable String lat){
        String address = "";
        try{

            URL url = new URL("http://api.map.baidu.com/geocoder/v2/?ak=wPPxsfCtNFm4PqEV68jbDdjP7U4twstj&callback=renderReverse&location="+ lat + "," + lng + "&output=json&pois=1");
            HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
            ucon.connect();

            InputStream in = ucon.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String str = reader.readLine();
            //outprint.print(str);

            str = str.substring(str.indexOf("(") + 1, str.length()-1);
//            System.out.println(str);

            JSONObject jsonObject = JSONObject.parseObject(str);
            address = jsonObject.getJSONObject("result").getString("formatted_address");
            System.out.println(address);


        }catch(Exception e){
            e.printStackTrace();
        }

        return ResultUtil.success(address);
    }
}
