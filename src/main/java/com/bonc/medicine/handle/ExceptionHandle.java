package com.bonc.medicine.handle;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jjjj on 2018/8/21.
 * 统一处理RuntimeException，
 */
@ControllerAdvice
public class ExceptionHandle {


    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof MedicineRuntimeException) {
            MedicineRuntimeException girlException = (MedicineRuntimeException) e;
            return ResultUtil.error(girlException.getCode(), girlException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return ResultUtil.error(ResultEnum.UNKONW_ERROR);
        }
    }
}

