package com.bonc.medicine.controller.user;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.user.Basicinfo;
import com.bonc.medicine.entity.user.Cooperative;
import com.bonc.medicine.entity.user.Expert;
import com.bonc.medicine.service.user.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserManagerController {

	@Autowired
	private UserManagerService userManagerService;

	/*
	 * 添加基础信息，返回账号id,前台必须传2018-01-10格式的字符串时间作为注册时间 角色id用英文逗号隔开1,3,4
	 */
	@PostMapping("/userManager/addBasic")
	public int addBasic(@RequestBody Basicinfo basicinfo) {
		userManagerService.addBasic(basicinfo);
		int id = basicinfo.getId();
		String[] role = basicinfo.getRole().split(",");
		for (int i = 0; i < role.length; i++) {
			userManagerService.addUserRoleRel(id, Integer.parseInt(role[i]));
		}
		return id;
	}

	/*
	 * 添加专家信息
	 */
	@PostMapping("/userManager/addExpert")
	public Result<Object> addExpert(@RequestBody Expert expert) {
		userManagerService.addExpert(expert);
		String[] cat_rel = expert.getCat_rel().split(",");
		int id = expert.getSpec_id();
		for (int i = 0; i < cat_rel.length; i++) {
			userManagerService.addCatRel(id, Integer.parseInt(cat_rel[i]));
		}
		String[] subject_rel = expert.getSubject_rel().split(",");
		for (int i = 0; i < subject_rel.length; i++) {
			userManagerService.addSubject_rel(id, Integer.parseInt(subject_rel[i]));
		}
		Result result = new Result();
		result.setData("成功");
		return result;
	}

	/*
	 * 添加合作社信息
	 */
	@PostMapping("/userManager/addCooperative")
	public Result<Object> addCooperative(@RequestBody Cooperative cooperative) {
		userManagerService.addCooperative(cooperative);
		Result result = new Result();
		result.setData("成功");
		return result;
	}

	/*
	 * 用户详情-基本信息
	 */
	@GetMapping("/userManager/get/basicInfo/{id}")
	public Result<Object> basicInfo(@PathVariable Integer id) {
		return userManagerService.basicInfo(id);
	}

	/*
	 * 账号解冻和激活 1：有效。0：冻结
	 */
	@PutMapping("/userManager/userStatus/{id}/{status}")
	public Result<Object> userStatus(@PathVariable Integer id, @PathVariable String status) {
		return userManagerService.userStatus(id, status);
	}

	/*
	 * 粉丝数
	 */
	@GetMapping("/userManager/get/fans/{id}")
	public Result<Object> fans(@PathVariable Integer id) {
		return userManagerService.fans(id);
	}

	/*
	 * 关注数
	 */
	@GetMapping("/userManager/get/care/{id}")
	public Result<Object> care(@PathVariable Integer id) {
		return userManagerService.care(id);
	}

	/*
	 * 积分
	 */
	@GetMapping("/userManager/get/integral/{id}")
	public Result<Object> integral(@PathVariable Integer id) {
		return userManagerService.integral(id);
	}

	/*
	 * 专家提问
	 */
	@GetMapping("/userManager/get/issue/{id}")
	public Result<Object> issue(@PathVariable Integer id) {
		return userManagerService.issue(id);
	}

	/*
	 * 供应信息
	 */
	@GetMapping("/userManager/get/supply/{id}")
	public Result<Object> supply(@PathVariable Integer id) {
		return userManagerService.supply(id);
	}

	/*
	 * 求购信息
	 */
	@GetMapping("/userManager/get/purchase/{id}")
	public Result<Object> purchase(@PathVariable Integer id) {
		return userManagerService.purchase(id);
	}

	/*
	 * 田间管理
	 */
	@GetMapping("/userManager/get/field/{id}")
	public Result<Object> field(@PathVariable Integer id) {
		return userManagerService.field(id);
	}

	/*
	 * 用户管理列表
	 */
	@GetMapping("/userManager/get/userlist/{tel}/{role}/{startTime}/{endTime}")
	public Result<Object> userlist(@PathVariable String tel, @PathVariable String role, @PathVariable String startTime,
                                   @PathVariable String endTime) {
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
		Result result = new Result();
		result.setData(list);
		return result;
	}
}
