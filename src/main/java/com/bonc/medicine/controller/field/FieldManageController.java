package com.bonc.medicine.controller.field;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Field;
import com.bonc.medicine.entity.field.FieldRecord;
import com.bonc.medicine.service.field.FieldManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/field_manage")
public class FieldManageController {

	@Autowired
	FieldManageService fieldManageService;
		
	@PostMapping("/info")
    public Result<Object> addFieldInfo(@RequestBody Field tempData){
		 return fieldManageService.addField(tempData);
    }


    /* *
     * @Description  1 查田间所有记录时，不传参数   2 查个人登记种植记录时  需要传递相应的参数  个人登记种植总数
     *                 即为返回list的长度
     * @Date 14:17 2018/9/1
     * @Param [param]
     * @return com.bonc.field.entity.Result<java.lang.Object>
     */
	@GetMapping("/info")
	public Result<Object> getInfo(@RequestParam Map<String,String> param){		
		return fieldManageService.getfield(param);
	}


	@PostMapping("/operation")
    public Result<Object> addOperation(@RequestBody FieldRecord tempData){
		 return fieldManageService.addOperation(tempData);
    }
	@GetMapping("/operation")
	public Result<Object> getOperation(@RequestParam Map<String,String> param){		
		return fieldManageService.getOperation(param);
	}
	/*
	 * 获取指导记录详情
	 */
	@GetMapping("/guideRecord/{user_id}")
	public Result<Object> guideRecord(@PathVariable Integer user_id) {
		return fieldManageService.guideRecord(user_id);
	}
	
	/*
	 * 获取指导记录总数
	 */
	@GetMapping("/guideRecordNum/{user_id}")
	public Result<Object> guideRecordNum(@PathVariable Integer user_id){		
		return fieldManageService.guideRecordNum(user_id);
	}
	
}
