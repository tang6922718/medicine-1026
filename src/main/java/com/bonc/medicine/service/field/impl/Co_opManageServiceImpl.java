package com.bonc.medicine.service.field.impl;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Co_op;
import com.bonc.medicine.entity.field.Co_op_Member;
import com.bonc.medicine.entity.field.Notice;
import com.bonc.medicine.mapper.field.Co_opManageMapper;
import com.bonc.medicine.service.field.Co_opManageService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName Co_opManagerServiceImpl
 * @Description
 * @Author YQ
 * @Date 2018/8/27 20:09
 * @Version 1.0
 */
@Service
public class Co_opManageServiceImpl implements Co_opManageService {

	@Autowired
	Co_opManageMapper co_opManageMapper;

	@Override
	public Result<Object> addCo_op(Co_op tempData) {
		if (tempData.getOfficial_user_id() == 0) {
			Map map = new HashMap();
			map = co_opManageMapper.queryUserID(tempData.getTelephone());
			if ("admin".equals(map.get("table_name"))) {
				tempData.setIs_audit("0");
			} else {
				tempData.setIs_audit("1");
			}

			tempData.setOfficial_user_id((int) map.get("id"));
			tempData.setOfficial_user_name((String) map.get("name"));
		}
		tempData.setState("0"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		return ResultUtil.success(co_opManageMapper.insertCo_op(tempData));
	}

	@Override
	public Result<Object> deleteCo_op(int ID) {
		Co_op tempData = new Co_op();
		tempData.setId(ID);
		tempData.setState("1");// 数据状态 0 可用 1 不可用
		return ResultUtil.success(co_opManageMapper.updateCo_op(tempData));
	}

//	@Override
//	public Result<Object> getCo_opInfo(int userID) {
//		Map map = new HashMap();
//		map = co_opManageMapper.queryCo_opInfo(userID); // 合作社基本信息
//		int coop_ID=(int)map.get("id");
//		map.putAll(co_opManageMapper.queryCo_opMemberNum(coop_ID)); // 合作社总人数
//		map.putAll(co_opManageMapper.queryPlantNum(coop_ID)); // 合作社登记种植总数
//		map.putAll(co_opManageMapper.queryAssistantNum(coop_ID)); // 合作社助手总数
//		map.putAll(co_opManageMapper.queryCo_opNoticeNum(coop_ID));
//		return ResultUtil.success(map);
//	}


	@Override
	public Result<Object> getCo_opInfo(Map params) {

		List<Map> list=new ArrayList<Map>();
		list=co_opManageMapper.queryCo_opByCondition(params);
		if (list.size()>0){
			for (Map obj:list
					) {
				obj.putAll(co_opManageMapper.queryCo_opMemberNum((int)obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryPlantNum((int)obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryAssistantNum((int)obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryCo_opNoticeNum((int)obj.get("coopID")));
			}
			return ResultUtil.success(list);
		}else {
			return ResultUtil.error(404,"数据为空");
		}

	}


	@Override
	public Result<Object> updateCo_op(Co_op tempData) {
		tempData.setState("0"); // 数据状态 0 可用 1 不可用
		return ResultUtil.success(co_opManageMapper.updateCo_op(tempData));
	}

	@Override
	public Result<Object> addCo_opMember(Co_op_Member tempData) {
		Map map = new HashMap();
		String tel = tempData.getTelephone();
		map = co_opManageMapper.queryUserID(tel);
		if (map != null) {
			tempData.setUser_id(String.valueOf(map.get("id")));
		}
		tempData.setState("0"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		tempData.setAssistant("1"); // 助手（技术员）标识    0 是     1 不是
		return ResultUtil.success(co_opManageMapper.insertCo_opMember(tempData));
	}

	@Override
	public Result<Object> getCo_opMember(int ID) {
		return ResultUtil.success(co_opManageMapper.queryCo_opMember(ID));
	}

	@Override
	public Result<Object> deleteCo_opMember(int id) {
		Co_op_Member tempData = new Co_op_Member();
		tempData.setId(id);
		tempData.setState("1"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		return ResultUtil.success(co_opManageMapper.updateCo_opMember(tempData));
	}

	@Override
	public Result<Object> updateCo_opMember(Co_op_Member tempData) {
		tempData.setState("0"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		return ResultUtil.success(co_opManageMapper.updateCo_opMember(tempData));
	}

	@Override
	public Result<Object> queryAssistantNum(int coop_id) {
		return ResultUtil.success(co_opManageMapper.queryAssistantNum(coop_id));
	}

	@Override
	public Result<Object> queryAssistant(int coop_id) {
		List<Map> list1=new ArrayList<Map>();
		list1.addAll(co_opManageMapper.queryAssistant(coop_id));
		list1.addAll(co_opManageMapper.queryNotAssistant(coop_id));
		return ResultUtil.success(list1);
	}

	@Override
	public Result<Object> queryNotAssistant(int coop_id) {
		return ResultUtil.success(co_opManageMapper.queryNotAssistant(coop_id));
	}

	@Override
	public Result<Object> getCoopMemberList(int coop_id) {
		return ResultUtil.success(co_opManageMapper.queryCoopMemberList(coop_id));
	}

	@Override
	public Result<Object> affiliatedCo_op(int user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = co_opManageMapper.affiliatedCo_op(user_id);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("total", co_opManageMapper
					.co_opTotal(Integer.parseInt(list.get(i).get("coop_id").toString())).get(0).get("total"));
		}
		return ResultUtil.success(list);
	}

	@Override
	public List<Map<String, Object>> findAllMember(Integer user_id) {
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		return co_opManageMapper.findAllMember(map);
	}

	@Override
	public Result<Object> addNotice(String allUserId, String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String time = sdf.format(cal.getTime());
		Map map = new HashMap<>();
		map.put("allUserId", allUserId);
		map.put("msg", msg);
		map.put("time", time);
		return ResultUtil.success(co_opManageMapper.addNotice(map));
	}

	@Override
	public Result<Object> noticeState(Integer id, String state) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("state", state);
		return ResultUtil.success(co_opManageMapper.noticeState(map));
	}

	@Override
	public Result<Object> deleteNotice(Integer id) {
		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(co_opManageMapper.deleteNotice(map));
	}

	@Override
	public Result<Object> noticeDetail(Integer id) {
		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(co_opManageMapper.noticeDetail(map));
	}

	@Override
	public Result<Object> updateNotice(Integer id, String msg) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("msg", msg);
		return ResultUtil.success(co_opManageMapper.updateNotice(map));
	}

	@Override
	public Result<Object> addRoleNotice(Notice notice) {
		return ResultUtil.success(co_opManageMapper.addRoleNotice(notice));
	}

	@Override
	public Result<Object> noticelist(String msg, String role, String start_time, String end_time) {
		Map map = new HashMap<>();
		map.put("msg", msg);
		map.put("role", role);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		return ResultUtil.success(co_opManageMapper.noticelist(map));
	}

	@Override
	public Result<Object> getCoopNoticeList(int coopID) {
		return ResultUtil.success(co_opManageMapper.getCoopNoticeList(coopID));
	}

}
