package com.bonc.medicine.hbase;

import java.lang.annotation.*;


/**  
* @desc: HbaseTable  
* @package com.bonc.parent.knowledgebase.hbase
* @Description 自定义注解，用于获取table
* @author: zhengzhoujin  
* @createTime: 2018年6月21日 下午2:12:56   
* @version: v1.0    
*/  
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface HbaseTable {

	// 表名
	String tableName() default "";
}
