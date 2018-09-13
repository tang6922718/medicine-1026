package com.bonc.medicine.utils;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import org.apache.commons.lang.StringUtils;

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

        String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[9]))\\d{8}$";

        if (StringUtils.isEmpty(phone) || !phone.matches(pattern)){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }

        // TODO zheli 需要进一步实现获取验证码，如果是用后端接口

        // 模拟一个假的验证码
        return "9527";
    }

    /**
    * @Description: 验证验证码是否正确，
    * @Param: [verification：验证码, phone：手机号码]
    * @return: boolean
    * @Author: hejiajun
    * @Date: 2018/9/3
    */
    public static boolean validateVerification(String verification, String phone) {

        if (StringUtils.isEmpty(verification)
                || !StringUtils.equals(verification.trim(), getVerifByPhone(phone))) {
            return false;
        }

        return true;
    }

}
