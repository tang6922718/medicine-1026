package com.bonc.medicine.utils;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;

/**
 * @program: medicine-hn
 *
 * @description: 验证码获取的工具类
 *
 * @author: hejiajun
 *
 * @create: 2018-09-01 15:41
 **/
public class VerificationUtils {

    /**
    * @Description: 通过手机号码获取验证码
    * @Param: [phone] 手机号码
    * @return: java.lang.String
    * @Author: hejiajun
    * @Date: 2018/9/3
    */
    public static String getVerifByPhone(String phone){

        // TODO 可能这个正则需要改动
        String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[9]))\\d{8}$";

        if (StringUtils.isEmpty(phone) || !phone.matches(pattern)){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }
        int code = 0;
        try{
            code  = methodOfGeneratingGourDigits();

            String realCode = HttpTool.sendPost(phone, code);

            JSONObject json = JSON.parseObject(realCode);
            String  status = json.getString("respstatus");
            if (!StringUtils.equals("0", status)){
                throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
            }

        }catch (Exception e){

            throw new MedicineRuntimeException(ResultEnum.NET_ERROR);
        }

        // TODO zheli 需要进一步实现获取验证码，如果是用后端接口

        return code + "";
    }

    /**
    * @Description: 验证验证码是否正确，
    * @Param: [verification：验证码, phone：手机号码]
    * @return: boolean
    * @Author: hejiajun
    * @Date: 2018/9/3
    */
 /*   public static boolean validateVerification(String verification, String phone) {

        if (StringUtils.isEmpty(verification)
                || !StringUtils.equals(verification.trim(), getVerifByPhone(phone))) {
            return false;
        }

        return true;
    }*/

    /**
    * @Description: 生成一个四位数的验证码
    * @Param: []
    * @return: int
    * @Author: hejiajun
    * @Date: 2018/9/20 
    */ 
    private static int methodOfGeneratingGourDigits(){
        double codeDemo = Math.random();
        String code = String.valueOf(codeDemo).substring(2, 6);
        return Integer.parseInt(code);
    }

 /*  public static void main(String[] args) {
       for (int i = 0; i < 10000000; i++){
           double codeDemo = Math.random();
           String code = String.valueOf(codeDemo).substring(2, 6);
           if (code.length() != 4){
               System.out.println(code);
           }

       }
    }*/

}
