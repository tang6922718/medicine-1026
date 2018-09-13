package com.bonc.medicine.entity.mall;


import com.bonc.medicine.hbase.HbaseColumn;
import com.bonc.medicine.hbase.HbaseTable;

/**
* @desc: HbaseExampleEntity  
* @package com.bonc.parent.knowledgebase.entity
* @Description 与Hbase中表的rowkey、列簇、列名以及列对应
* @author: zhengzhoujin  
* @createTime: 2018年6月21日 下午2:14:13   
* @version: v1.0    
*/  
@HbaseTable(tableName = "test_hbase_z")
public class HbaseExampleEntity {

	// 标示数据中rowkey，用在新增时候填充
	@HbaseColumn(family = "rowkey", qualifier = "rowkey001")
	private String id;

	// 列簇、列映射的实体名称，用于查询的数据返回
	@HbaseColumn(family = "f001", qualifier = "col001")
	private String c1;

	/** 
	 * @return id 
	 */
	public String getId() {
		return id;
	}

	/** 
	 * @param id 要设置的 id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/** 
	 * @return c1 
	 */
	public String getC1() {
		return c1;
	}

	/** 
	 * @param c1 要设置的 c1 
	 */
	public void setC1(String c1) {
		this.c1 = c1;
	}

}
