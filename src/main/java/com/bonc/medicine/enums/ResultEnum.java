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
    ERROR_LOGIN(400, "用户名或密码错误哦！！！亲"),
    MISSING_PARA(201, "参数不全,要不就是参数是错误的哦"),
    ERROR_VERIFI(202, "验证码错误"),
    ERROR_PHONE(203, "亲！先注册才能进行这个操作哦"),
    UN_USE_PHONE(206,"账户无效，或者已经被注册"),
    NET_ERROR(405,"网络错误，请稍后再试，或者您正在操作错误资源"),
    OUT_OF_TIME(401,"登陆超时"),
    ERROR_CODE(205,"验证码错误"),
    CODE_OUT_TIME(209,"验证码超时啦"),
    ERROR_PARAM(208,"两次输入的密码不一致")
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
