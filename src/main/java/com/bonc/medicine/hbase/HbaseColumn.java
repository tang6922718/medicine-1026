package com.bonc.medicine.hbase;

import java.lang.annotation.*;

/**  
* @desc: HbaseColumn  
* @package com.bonc.parent.knowledgebase.hbase
* @Description 自定义注解，用于描述字段所属的 family与qualifier. 也就是hbase的列与列簇
* @author: zhengzhoujin  
* @createTime: 2018年6月21日 下午2:11:04   
* @version: v1.0    
*/  
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Inherited
public @interface HbaseColumn {

	// 列簇
	String family() default "";

	// 列
	String qualifier() default "";

	// 时间戳
	boolean timestamp() default false;
}
