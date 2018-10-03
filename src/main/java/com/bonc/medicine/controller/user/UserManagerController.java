package com.bonc.medicine.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import com.bonc.medicine.service.user.UserManagerService;
import com.bonc.medicine.utils.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserManagerController {

	@Autowired
	private UserManagerService userManagerService;

	/*
	 * 添加基础信息
	 */
	@PostMapping("/userManager/addBasic")
	public Result<Object> addUser(@RequestBody JSONObject json) {
		userManagerService.addUser(json);
		return ResultUtil.success("成功");
	}

	/*
	 * 查询数据手机号是否存在，有返回0以外的数字
	 */
	@GetMapping("/userManager/get/tel")
	public Result<Object> getTel(String tel) {
		return ResultUtil.success(userManagerService.getTel(tel));
	}
	
	@GetMapping("/userManager/update/Basic")
	public Result<Object> updateBasic(Integer id, String name, String sex, Integer age, String address,
			String img_url) {
		return ResultUtil.success(userManagerService.updateBasic(id, name, sex, age, address, img_url));
	}

	/*
	 * 用户详情-基本信息
	 */
	@GetMapping("/userManager/get/basicInfo")
	public Result<Object> basicInfo(Integer id) {
		return userManagerService.basicInfo(id);
	}

	/*
	 * 账号解冻和激活 1：有效。0：冻结
	 */
	@PutMapping("/userManager/userStatus")
	public Result<Object> userStatus(@RequestBody Map param) {
		return userManagerService.userStatus(Integer.parseInt(param.get("id") + ""), param.get("status") + "");
	}

	/*
	 * 粉丝数
	 */
	@GetMapping("/userManager/get/fans")
	public Result<Object> fans(Integer id) {
		return userManagerService.fans(id);
	}

	/*
	 * 关注数
	 */
	@GetMapping("/userManager/get/care")
	public Result<Object> care(Integer id) {
		return userManagerService.care(id);
	}

	/*
	 * 积分
	 */
	@GetMapping("/userManager/get/integral")
	public Result<Object> integral(Integer id) {
		return userManagerService.integral(id);
	}

	/*
	 * 专家提问
	 */
	@GetMapping("/userManager/get/issue")
	public Result<Object> issue(Integer id) {
		return userManagerService.issue(id);
	}

	/*
	 * 供应信息
	 */
	@GetMapping("/userManager/get/supply")
	public Result<Object> supply(Integer id) {
		return userManagerService.supply(id);
	}

	/*
	 * 求购信息
	 */
	@GetMapping("/userManager/get/purchase")
	public Result<Object> purchase(Integer id) {
		return userManagerService.purchase(id);
	}

	/*
	 * 田间管理
	 */
	@GetMapping("/userManager/get/field")
	public Result<Object> field(Integer id) {
		return userManagerService.field(id);
	}

	/*
	 * 用户管理列表
	 */
	@GetMapping("/userManager/get/userlist")
	public Result<Object> userlist(String tel, String role, String startTime, String endTime) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 保存1，2，3，4，10列数据
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 保存5,6列数据
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();// 保存7列数据
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();// 保存8列数据
		List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();// 保存9列数据
		list = userManagerService.userlist(tel, role, startTime, endTime);
		list1 = userManagerService.coop_hrlp_list();
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).get("user_id").toString().equals(list1.get(i).get("user_id").toString())) {
					list.get(j).put("coop", list1.get(i).get("name"));
					list.get(j).put("HELP", list1.get(i).get("assistant"));
				}
			}
		}
		list2 = userManagerService.integrallist();
		for (int i = 0; i < list2.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).get("user_id").toString().equals(list2.get(i).get("user_id").toString())) {
					list.get(j).put("integral", list2.get(i).get("current_integral"));
				}
			}
		}
		list3 = userManagerService.supplylist();
		for (int i = 0; i < list3.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).get("user_id").toString().equals(list3.get(i).get("user_id").toString())) {
					list.get(j).put("supply", list3.get(i).get("supply"));
				}
			}
		}
		list4 = userManagerService.purchaselist();
		for (int i = 0; i < list4.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).get("user_id").toString().equals(list4.get(i).get("user_id").toString())) {
					list.get(j).put("purchase", list4.get(i).get("purchase"));
				}
			}
		}
		return ResultUtil.success(list);
	}

	/*
	 * *
	 * 
	 * @Description 查询用户的基本信息
	 * 
	 * @Date 17:51 2018/9/18
	 * 
	 * @Param [userID]
	 * 
	 * @return com.bonc.medicine.entity.Result<java.lang.Object>
	 */
	@GetMapping("/userManager/userbaseinfo")
	public Result<Object> queryUserInfo(@RequestParam int userID) {
		return userManagerService.queryUserInfo(userID);
	}

	/*
	 * *
	 * 
	 * @Description 修改用户的种植户角色 种植户角色不需要审核
	 * 
	 * @Date 10:53 2018/9/26
	 * 
	 * @Param [params]
	 * 
	 * @return com.bonc.medicine.entity.Result<java.lang.Object>
	 */
	@PutMapping("/userManager/updatePlantRole")
	public Result<Object> updateUserPlantRole(@RequestBody Map<String, String> params) {
		return userManagerService.updateUserPlantRole(params);
	}

	/*
	 * *
	 * 
	 * @Description 修改用户关心品种
	 * 
	 * @Date 17:17 2018/9/26
	 * 
	 * @Param [parsms]
	 * 
	 * @return com.bonc.medicine.entity.Result<java.lang.Object>
	 */
	@PutMapping("/userManager/updateCareVariety")
	public Result<Object> updateUserCareVariety(@RequestBody Map<String, Object> parsms) {
		return userManagerService.updateUserCareVariety(parsms);
	}


	/**
	* @Description: 根据用户的id获取当前用户的活跃天数；不是从之前的统计的表去查询。而是去登陆日志表中去统计
	* @Param: []
	* @return: com.bonc.medicine.entity.Result
	 *     reMap.put("acDays", "0");
	* @Author: hejiajun
	* @Date: 2018/9/29 
	*/
	@Authorization
	@GetMapping("/user/active/day/v1.0")
	public Result activeDays (@CurrentUser String userId){

		//System.out.println(userId);
		return ResultUtil.success(userManagerService.activeDays(userId));
	}


}
