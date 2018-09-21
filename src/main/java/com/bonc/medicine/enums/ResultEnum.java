package com.bonc.medicine.enums;

/** 
 * @ClassName: ResultEnum 
 * @Description: 统一异常
 * @author: hejiajun
 * @date: 2018年8月20日 上午11:56:59  
 */
public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    ERROE(500, "服务器错误"),
    PERMISSION(300, "you have no permission"),
    NO_CONTENT(204, "响应成功，但资源不存在"),
    ERROR_LOGIN(400, "用户名或密码错误"),
    MISSING_PARA(201, "参数不全"),
    ERROR_VERIFI(202, "验证码错误"),
    ERROR_PHONE(203, "无此手机号"),
    UN_USE_PHONE(206,"账户无效，或者已经被注册"),
    NET_ERROR(405,"网络错误，请稍后再试"),
    OUT_OF_TIME(401,"登陆超时"),
    ERROR_CODE(205,"验证码错误"),
    CODE_OUT_TIME(209,"验证码超时啦")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
