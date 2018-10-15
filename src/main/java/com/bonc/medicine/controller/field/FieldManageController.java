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
	@GetMapping("/guideRecord")
	public Result<Object> guideRecord( Integer user_id) {
		return fieldManageService.guideRecord(user_id);
	}
	
	/*
	 * 获取指导记录总数
	 */
	@GetMapping("/guideRecordNum")
	public Result<Object> guideRecordNum( Integer user_id){		
		return fieldManageService.guideRecordNum(user_id);
	}


	/* *
* @Description 获取该品种的所有农事操作步骤
* @Date 14:43 2018/9/16
* @Param [categroyID]
* @return com.bonc.medicine.entity.Result<java.lang.Object>
*/
	@GetMapping("/farm/opreation/{categroyID}")
	public Result<Object> getFarmOpreationByCategroy(@PathVariable int categroyID){
		return fieldManageService.getFarmOpreationByCategroy(categroyID);
	}


	/* *
	 * @Description 获取该品种的某步骤的SOP信息
	 * @Date 15:43 2018/9/16
	 * @Param [categroyID, stepID]
	 * @return com.bonc.medicine.entity.Result<java.lang.Object>
	 */
	@GetMapping("/farm/SOPinfo/{categroyID}/{stepID}")
	public Result<Object> getCategroySOPInfo(@PathVariable int categroyID,
											 @PathVariable int stepID){
		return fieldManageService.getCategroySOPInfo(categroyID,stepID);
	}



	/* *
	 * @Description 所有品种查询
	 * @Date 17:40 2018/9/19
	 * @Param []
	 * @return com.bonc.medicine.entity.Result<java.lang.Object>
	 */
	@GetMapping("/allcategroy")
	public Result<Object> queryAllCategroy(){
		return fieldManageService.queryAllCategroy();
	}


	@GetMapping("/weather/{city_name}")
	public Result<Object> queryWeatherInfo(@PathVariable String city_name){
		return fieldManageService.queryWeatherInfo(city_name);
	}
	
}
