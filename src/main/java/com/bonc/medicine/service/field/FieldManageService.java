package com.bonc.medicine.service.field;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Field;
import com.bonc.medicine.entity.field.FieldRecord;

import java.util.Map;

public interface FieldManageService {

	public Result<Object> addField(Field tempData);

	public Result<Object> getfield(Map param);

	public Result<Object> addOperation(FieldRecord tempData);

	public Result<Object> getOperation(Map param);	
	
	public Result<Object> guideRecord(Integer user_id);	
	
	public Result<Object> guideRecordNum(Integer user_id);	
	
}
