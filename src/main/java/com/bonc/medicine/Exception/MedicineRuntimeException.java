package com.bonc.medicine.Exception;


import com.bonc.medicine.enums.ResultEnum;

/**
 * Created by jjjj on 2018/8/21.
 */
public class MedicineRuntimeException extends RuntimeException{

    private Integer code;

    public MedicineRuntimeException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public MedicineRuntimeException(int code , String msg) {
        super(msg);
        this.code=code;
        //this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
