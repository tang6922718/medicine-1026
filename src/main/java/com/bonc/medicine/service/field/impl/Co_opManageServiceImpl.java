package com.bonc.medicine.service.field.impl;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Co_op;
import com.bonc.medicine.entity.field.Co_op_Member;
import com.bonc.medicine.entity.field.Notice;
import com.bonc.medicine.mapper.field.Co_opManageMapper;
import com.bonc.medicine.mapper.field.FieldManageMapper;
import com.bonc.medicine.service.field.Co_opManageService;
import com.bonc.medicine.utils.ExchangeCategroyNameID;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
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

	@Autowired
	FieldManageMapper fieldManageMapper;

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
			for (Map obj:list ) {
				obj.putAll(co_opManageMapper.queryCo_opMemberNum((int)obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryPlantNum((int)obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryAssistantNum((int)obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryCo_opNoticeNum((int)obj.get("coopID")));
			}
			return ResultUtil.success(list);
		}else {
			return ResultUtil.success();
		}

	}


	@Override
	@Transactional
	public Result<Object> updateCo_op(Co_op tempData) {
		tempData.setState("0"); // 数据状态 0 可用 1 不可用

		if ( "0".equals(tempData.getIs_audit())){ // 合作社审核通过时  给用户添加合作社角色属性

			int i=co_opManageMapper.insertCommon_user_role_rel(tempData.getId());
			if (i>0){
				return ResultUtil.success(co_opManageMapper.updateCo_op(tempData));
			}else {
				return ResultUtil.error(500,"添加角色属性属性失败");
			}
		}else {
			return ResultUtil.success(co_opManageMapper.updateCo_op(tempData));
		}
	}

	@Override
	@Transactional
	public Result<Object> addCo_opMember(Co_op_Member tempData) {
		// 查询所有品种信息
		List<Map> categroyList=fieldManageMapper.queryAllCategroy();

		//根据tel 判断是否为平台用户   判断是否已经是当前合作社社员了（不允许重复添加）
		Map map = new HashMap();
		String tel = tempData.getTelephone();
		map = co_opManageMapper.queryUserID(tel);
		if (map != null) {
			tempData.setUser_id(String.valueOf(map.get("id")));
		}

		// 判断是否已经是合作社成员了
		Map isCoopMember=co_opManageMapper.queryIsAlreadyCoopMember(tempData.getCoop_id(),tel);
		if (Integer.parseInt(isCoopMember.get("isCoopMember").toString())>0){
			return ResultUtil.error(500,"该用户已经是合作社成员了");
		}


		if (tempData.getAssistant()!=null && tempData.getAssistant()!=""){  // 后台管理新增社员时可以指定是否为技术员

			if ("0".equals(tempData.getAssistant())){ //新增的社员被指定为技术员   这里为该社员添加技术员身份(注意技术员必须为平台用户 技术员只能属于一个合作社)

				if (map != null) {

					Map isAlreadyAssistantOtherCoopMap=co_opManageMapper.queryIsAlreadyAssistantOtherCoop((int)map.get("id"));

					int num=Integer.parseInt(isAlreadyAssistantOtherCoopMap.get("NUM").toString());

					if (num>0){
						return ResultUtil.error(500,"该用户已经是其它合作社的技术员了");
					}

					//为该合作社成员添加技术员角色  先删再插入
					int i=co_opManageMapper.deleteRole((Integer) map.get("id"),4);
					i=co_opManageMapper.insertRole((Integer) map.get("id"),4);

				}else {
					return ResultUtil.error(500,"技术员必须为本平台用户");
				}

			}
		}else { // APP段新增社员时没有指定是否技术员  默认不是技术员
			tempData.setAssistant("1"); // 助手（技术员）标识    0 是     1 不是
		}

		tempData.setState("0"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		tempData.setPlant_cat_id(ExchangeCategroyNameID.NameToId(tempData.getPlant_cat_id(),categroyList));

		int i=co_opManageMapper.insertCo_opMember(tempData);
		if (i>0){
			co_opManageMapper.updateCoopTotalAreaAdd(tempData.getCoop_id(),tempData.getPlant_area());
		}
		return ResultUtil.success(i);
	}

	@Override
	public Result<Object> getCo_opMember(int ID) {
		List<Map> categroyList=fieldManageMapper.queryAllCategroy();

		Map temp=co_opManageMapper.queryCo_opMember(ID);

		temp.put("plant_cat_id",ExchangeCategroyNameID.IDToName(temp.get("plant_cat_id").toString(),categroyList));

		return ResultUtil.success(temp);
	}

	@Override
	public Result<Object> deleteCo_opMember(int id) {
		Co_op_Member tempData = new Co_op_Member();
		tempData.setId(id);
		tempData.setState("1"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		int i = co_opManageMapper.updateCo_opMember(tempData);
		if (i>0){
			co_opManageMapper.updateCoopTotalAreaReduce(id);
		}
		return ResultUtil.success(co_opManageMapper.updateCo_opMember(tempData));
	}

	@Override
	@Transactional
	public Result<Object> updateCo_opMember(Co_op_Member tempData) {

		//先查询该社员之前的详细信息
		Map coopMemberInfo=co_opManageMapper.queryCo_opMember(tempData.getId());

		if (tempData.getAssistant()!=null){

			if ("0".equals(tempData.getAssistant())){ // 社员变更为技术员时  要判断其是不是平台用户   是否是其它合作社的技术员
				if (coopMemberInfo.get("user_id") !=null && coopMemberInfo.get("user_id") !=""){

					Map isAlreadyAssistantOtherCoopMap=
							co_opManageMapper.queryIsAlreadyAssistantOtherCoop((int)coopMemberInfo.get("user_id"));
					int num=Integer.parseInt(isAlreadyAssistantOtherCoopMap.get("NUM").toString());
					if (num>0){
						return ResultUtil.error(500,"该用户已经是其它合作社的技术员了");
					}

					//为该合作社成员添加技术员角色  先删再插入
					int i=co_opManageMapper.deleteRole((Integer) coopMemberInfo.get("user_id"),4);
					i=co_opManageMapper.insertRole((Integer) coopMemberInfo.get("user_id"),4);

				}else {
					return ResultUtil.error(500,"技术员必须为本平台用户");
				}
			}else {
				co_opManageMapper.deleteRole((Integer) coopMemberInfo.get("user_id"),4);
			}
		}

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
	public Result<Object> getCoopMemberList2(int coop_id) {
		// 所有品种信息
		List<Map> allCategroyInfo=fieldManageMapper.queryAllCategroy();

		List<Map> coopMemberList=co_opManageMapper.queryCoopMemberList2(coop_id);
		for (Map obj:coopMemberList
			 ) {
			obj.put("plant_cat_id",ExchangeCategroyNameID.IDToName(obj.get("plant_cat_id").toString(),allCategroyInfo));
		}

		return ResultUtil.success(coopMemberList);
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
	public Result<Object> addNotice(String allUserId, String msg, String coopID, int publishUserID, String picture_url) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String time = sdf.format(cal.getTime());
		Map map = new HashMap<>();
		map.put("allUserId", allUserId);
		map.put("msg", msg);
		map.put("time", time);
		map.put("object_id", coopID);
		map.put("publish_user_id", publishUserID);
		map.put("picture_url", picture_url);
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
