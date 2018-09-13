package com.bonc.medicine.utils;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;

/**
 * @ClassName: ResultUtil 
 * @Description: 
 * @author: hejiajun
 * @date: 2018年8月20日 上午11:47:31  
 */
public class ResultUtil {

	public static Result success(Object object) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    
    public static Result error(ResultEnum resultEnum) {
    	Result result = new Result();
    	result.setCode(resultEnum.getCode());
    	result.setMsg(resultEnum.getMsg());
    	return result;
    }
}
