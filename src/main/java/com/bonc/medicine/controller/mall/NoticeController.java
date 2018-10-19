package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.NoticeService;
import com.bonc.medicine.utils.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	/*
	 * 系统通知
	 */
	@GetMapping("/notice/systemInfo/{user_id}")
	public Result<Object> systemInfo(@PathVariable Integer user_id) {
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list1 = noticeService.findRole(user_id);
		String str_role_id = (list1==null || list1.size()==0 || list1.get(0) == null) ?"":list1.get(0).get("role_id").toString();// 返回拥有角色的编号
		String notice_role_type = "1,";// 转换一下，和common_notice的角色字段对应,1表示是全体
		if (str_role_id.contains("1")) {
			notice_role_type += "2,";
		}
		if (str_role_id.contains("3")) {
			notice_role_type += "4,";
		}
		if (str_role_id.contains("5")) {
			notice_role_type += "3,";
		}
		if(notice_role_type != ""){
			notice_role_type.substring(0, notice_role_type.length() - 1);
		}
		String []  notice_role_type_array =  notice_role_type.split(",");
		list = noticeService.systemInfo(user_id,notice_role_type_array);
		return ResultUtil.success(list);
	}

	/*
	 * 专家答疑
	 */
	@GetMapping("/notice/meetProfessor/{user_id}")
	public Result<Object> meetProfessor(@PathVariable Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = noticeService.meetProfessor(user_id);
		return ResultUtil.success(list);
	}

	/*
	 * 动态评论
	 */
	@GetMapping("/notice/dyanimic/{user_id}")
	public Result<Object> dyanimic(@PathVariable Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = noticeService.dyanimic(user_id);
		return ResultUtil.success(list);
	}

	/*
	 * 商品留言
	 */
	@GetMapping("/notice/message/{user_id}")
	public Result<Object> message(@PathVariable Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = noticeService.message(user_id);
		return ResultUtil.success(list);
	}

}
