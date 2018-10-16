package com.bonc.medicine.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: MethodLog 
 * @Description: 通过这个注解实现操作日志写入 ("新增,新增培训视频,培训")像这种款式
 * @author: hejiajun
 * @date: 2018年10月16日 下午4:31:18
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
	
	String remark() default ",,";
	
}
