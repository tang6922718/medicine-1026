package com.bonc.medicine.controller.field;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Co_op;
import com.bonc.medicine.entity.field.Co_op_Member;
import com.bonc.medicine.entity.field.Notice;
import com.bonc.medicine.service.field.Co_opManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Co_opManageController
 * @Description 合作社管理相关操作
 * @Author YQ
 * @Date 2018/8/27 20:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/co_opmanage")
public class Co_opManageController {

	@Autowired
	Co_opManageService co_opManageService;

	/*
	 * *
	 * 
	 * @Description 新增合作社
	 * 
	 * @Date 11:03 2018/8/28
	 * 
	 * @Param [tempData]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@PostMapping("/add/co_op")
	public Result<Object> addCo_op(@RequestBody Co_op tempData) {
		return co_opManageService.addCo_op(tempData);
	}

	/*
	 * *
	 * 
	 * @Description 合作社删除（逻辑上的删除）
	 * 
	 * @Date 14:42 2018/8/28
	 * 
	 * @Param [ID]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@DeleteMapping("/co_op/{id}")
	public Result<Object> deleteCo_op(@PathVariable int id) {
		return co_opManageService.deleteCo_op(id);
	}

	/*
	 * *
	 * 
	 * @Description 合作社信息查询
	 * 
	 * @Date 14:45 2018/8/28
	 * 
	 * @Param [ID]   这里的ID是指合作社管理员的userID
	 *
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@GetMapping("/co_op/{userID}")
	public Result<Object> getCo_opInfo(@PathVariable int userID) {
		return co_opManageService.getCo_opInfo(userID);
	}

	/*
	 * *
	 * 
	 * @Description 合作社信息修改
	 * 
	 * @Date 15:44 2018/8/28
	 * 
	 * @Param [tempData]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@PutMapping("/co_op")
	public Result<Object> updateCo_op(@RequestBody Co_op tempData) {
		return co_opManageService.updateCo_op(tempData);
	}

	/*
	 * *
	 * 
	 * @Description 新增合作社成员
	 * 
	 * @Date 16:50 2018/8/29
	 * 
	 * @Param [tempData]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@PostMapping("/co_opmember")
	public Result<Object> addCo_opMember(@RequestBody Co_op_Member tempData) {
		return co_opManageService.addCo_opMember(tempData);
	}

	/*
	 * *
	 * 
	 * @Description 合作社社员详情查询
	 * 
	 * @Date 20:05 2018/8/29
	 * 
	 * @Param [ID]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@GetMapping("/co_opmember/{id}")
	public Result<Object> getCo_opMember(@PathVariable int id) {
		return co_opManageService.getCo_opMember(id);
	}

	/*
	 * *
	 * 
	 * @Description 合作社社员删除
	 * 
	 * @Date 14:21 2018/8/30
	 * 
	 * @Param [tempData]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@DeleteMapping("/co_opmember/{id}")
	public Result<Object> deleteCo_opMember(@PathVariable int id) {
		return co_opManageService.deleteCo_opMember(id);
	}

	/*
	 * *
	 * 
	 * @Description 合作社社员信息修改
	 * 
	 * @Date 14:31 2018/8/30
	 * 
	 * @Param [tempData]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@PutMapping("/co_opmember")
	public Result<Object> updateCo_opMember(@RequestBody Co_op_Member tempData) {
		return co_opManageService.updateCo_opMember(tempData);
	}

	/*
	 * *
	 * 
	 * @Description 合作社助手数量查询 (可以不用)
	 * 
	 * @Date 15:58 2018/8/30
	 * 
	 * @Param [coop_id]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@GetMapping("/co_op/assistantnum/{coop_id}")
	public Result<Object> getAssistantNum(@PathVariable int coop_id) {
		return co_opManageService.queryAssistantNum(coop_id);
	}

	/*
	 * *
	 * 
	 * @Description 合作社助手列表查询
	 * 
	 * @Date 16:00 2018/8/30
	 * 
	 * @Param [coop_id]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@GetMapping("/co_op/assistant/{coop_id}")
	public Result<Object> getAssistant(@PathVariable int coop_id) {
		return co_opManageService.queryAssistant(coop_id);
	}

	/*
	 * *
	 * 
	 * @Description 合作社非助手列表查询
	 * 
	 * @Date 16:01 2018/8/30
	 * 
	 * @Param [coop_id]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@GetMapping("/co_op/notassistant/{coop_id}")
	public Result<Object> getNotAssistant(@PathVariable int coop_id) {
		return co_opManageService.queryNotAssistant(coop_id);
	}

	/*
	 * *
	 * 
	 * @Description 合作社社员列表查询
	 * 
	 * @Date 18:15 2018/8/31
	 * 
	 * @Param [coop_id]
	 * 
	 * @return com.bonc.field.entity.Result<java.lang.Object>
	 */
	@GetMapping("/co_opmember/list/{coop_id}")
	public Result<Object> getCoopMemberList(@PathVariable int coop_id) {
		return co_opManageService.getCoopMemberList(coop_id);
	}

	/*
	 * 我的-所属合作社
	 */
	@GetMapping("/affiliatedCo_op/list/{user_id}")
	public Result<Object> affiliatedCo_op(@PathVariable int user_id) {
		return co_opManageService.affiliatedCo_op(user_id);
	}

	/*
	 * 给该合作社下所有人发通知
	 */
	@PutMapping("/Co_op/sendMsg/{user_id}/{msg}")
	public Result<Object> affiliatedCo_op(@PathVariable int user_id, @PathVariable String msg) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = co_opManageService.findAllMember(user_id);
		String allUserId = null;
		if (list != null && list.size() != 0) {
			allUserId = list.get(0).get("all_user_id").toString();
			co_opManageService.addNotice(allUserId, msg);
		}
		Result result = new Result();
		result.setData("发送完毕");
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 公告启用-停用
	 */
	@GetMapping("/Co_op/noticeState")
	public Result<Object> noticeState(int id, String state) {
		co_opManageService.noticeState(id, state);
		Result result = new Result();
		result.setData("更新完毕");
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 公告删除
	 */
	@GetMapping("/Co_op/dalete/notice")
	public Result<Object> deleteNotice(int id) {
		co_opManageService.deleteNotice(id);
		Result result = new Result();
		result.setData("更新完毕");
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 公告详情
	 */
	@GetMapping("/Co_op/noticeDetail")
	public Result<Object> noticeDetail(int id) {
		return co_opManageService.noticeDetail(id);
	}

	/*
	 * 更新公告
	 */
	@GetMapping("/Co_op/update/notice")
	public Result<Object> updateNotice(int id, String msg) {
		return co_opManageService.updateNotice(id, msg);
	}

	/*
	 * 新增公告
	 */
	@PostMapping("/Co_op/add/roleNotice")
	public Result<Object> addRoleNotice(@RequestBody Notice notice) {
		return co_opManageService.addRoleNotice(notice);
	}

	/*
	 * 查询公告列表
	 */
	@GetMapping("/Co_op/noticelist")
	public Result<Object> noticelist( String msg,  String role,
			 String start_time,  String end_time) {
		return co_opManageService.noticelist(msg, role, start_time, end_time);
	}

}
