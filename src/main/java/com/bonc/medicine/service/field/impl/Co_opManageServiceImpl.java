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
import org.apache.commons.lang.StringUtils;
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
	@Transactional
	public Result<Object> addCo_op(Co_op tempData) {
		// 后台管理员新建合作社没这边的 addCo_op 方法

		// 所有品种信息 后面转码用
		List<Map> categroyList = fieldManageMapper.queryAllCategroy();

		// 根据电话号码去查用户信息   该SQL返回: id,name , age,head_portrait, address, sex
		Map map = new HashMap();
		map = co_opManageMapper.queryUserID(tempData.getTelephone());

		// 判断申请人是否已经有其它合作社了 （一个人只能是一个合作社的管理员）
		Map isOtherCoopManager=co_opManageMapper.queryIsAlreadyCoopManager((int)map.get("id"));
		if (Integer.parseInt(isOtherCoopManager.get("NUM").toString())>0){
			return ResultUtil.error(500,"该用户已经申请其它合作社了(有可能还在审核),不能再申请合作社");
		}

		// 判断申请人是否有技术员角色  （用户不能同时有合作社 技术员 角色属性）
		Map isAlreadyAssistantOtherCoopMap=co_opManageMapper.queryIsAlreadyAssistantOtherCoop((int)map.get("id"));
		if (Integer.parseInt(isAlreadyAssistantOtherCoopMap.get("NUM").toString())>0){
			return ResultUtil.error(500,"该用户已经是其它合作社的技术员了,不能再申请合作社");
		}

		tempData.setOfficial_user_id((int) map.get("id"));
		tempData.setOfficial_user_name((String) map.get("name"));
		tempData.setCultivar(ExchangeCategroyNameID.NameToId(tempData.getCultivar(),categroyList));
		tempData.setState("0"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		tempData.setIs_audit("1"); //is_audit: 0 已审核   1 未审核   2 审核不通过

		// 新建合作社 没传图片时 赋值""
		if (tempData.getPhoto_url() == null) {
			tempData.setPhoto_url("");
		}
		if (tempData.getImg_url()==null) {
			tempData.setImg_url("");
		}
		if (tempData.getPortrait()==null) {
			tempData.setPortrait("");
		}


		int i=co_opManageMapper.insertCo_op(tempData);
		if (i>0){
			return ResultUtil.success(i);

		}else {
			return ResultUtil.error(500,"合作社信息插入失败");
		}

	}

	@Override
	public Result<Object> deleteCo_op(int ID) {
		Co_op tempData = new Co_op();
		tempData.setId(ID);
		tempData.setState("1");// 数据状态 0 可用 1 不可用
		return ResultUtil.success(co_opManageMapper.updateCo_op(tempData));
	}


	@Override
	public Result<Object> getCo_opInfo(Map params) {

		// 所有品种信息 后面转码用
		List<Map> categroyList = fieldManageMapper.queryAllCategroy();

		List<Map> list = new ArrayList<Map>();
		list = co_opManageMapper.queryCo_opByCondition(params);
		if (list.size() > 0) {
			for (Map obj : list) {
				obj.put("cultivar",ExchangeCategroyNameID.IDToName(obj.get("cultivar").toString(),categroyList));
				obj.putAll(co_opManageMapper.queryCo_opMemberNum((int) obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryPlantNum((int) obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryAssistantNum((int) obj.get("coopID")));
				obj.putAll(co_opManageMapper.queryCo_opNoticeNum((int) obj.get("coopID")));
			}
			return ResultUtil.success(list);
		} else {
			return ResultUtil.success();
		}

	}

	@Override
	@Transactional
	public Result<Object> updateCo_op(Co_op tempData) {

		// 所有品种信息 后面转码用
		List<Map> categroyList = fieldManageMapper.queryAllCategroy();

		if (tempData.getCultivar()!=null){
			tempData.setCultivar(ExchangeCategroyNameID.NameToId(tempData.getCultivar(),categroyList));
		}

		tempData.setState("0"); // 数据状态 0 可用 1 不可用
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if ("0".equals(tempData.getIs_audit())) { // 合作社审核通过时 给用户添加合作社角色属性

			// 给申请者加合作社角色属性
			int i= co_opManageMapper.insertCommon_user_role_rel(tempData.getId(),3);
//			int i = co_opManageMapper.insertCommon_user_role_rel(tempData.getId(),4);   // 合作社  技术员不能同时属于一个用户
			if (i > 0) {
				list = co_opManageMapper.queryCoopInfo(tempData.getId());
				Map map = new HashMap<>();
				map.put("object_id", tempData.getId());
				map.put("notice_content", "您申请的" + list.get(0).get("name").toString() + "审核通过，可以添加和管理您的社员。");
				map.put("notice_receiver", list.get(0).get("official_user_id"));
				co_opManageMapper.addCoopAduitNotice(map);

				Map coopInfo=list.get(0);

				//把合作社管理员作为管理员插入社员表中
				// 根据电话号码去查用户信息   该SQL返回: id,name , age,head_portrait, address, sex
				Map userInfo = co_opManageMapper.queryUserID(coopInfo.get("telephone").toString());

				Co_op_Member coOpMember=new Co_op_Member();
				coOpMember.setName(userInfo.get("name").toString());
				coOpMember.setImg_url(userInfo.get("head_portrait").toString());
				coOpMember.setSex(userInfo.get("sex").toString());
				coOpMember.setAge(Integer.parseInt(userInfo.get("age").toString()));
				coOpMember.setTelephone(coopInfo.get("telephone").toString());
				coOpMember.setAddress(userInfo.get("address").toString());
				coOpMember.setPlant_age(0);
				coOpMember.setPlant_cat_id(coopInfo.get("cultivar").toString());
				coOpMember.setPlant_area(0);
				coOpMember.setCoop_id(tempData.getId());
				coOpMember.setUser_id(userInfo.get("id").toString());
				coOpMember.setAssistant("2");  // 0 技术员   1 普通社员   2 管理员
				coOpMember.setState("0");
				co_opManageMapper.insertCo_opMember(coOpMember);

				return ResultUtil.success(co_opManageMapper.updateCo_op(tempData));

			} else {
				return ResultUtil.error(500, "添加角色属性属性失败");
			}
		} else if ("2".equals(tempData.getIs_audit())) {
			co_opManageMapper.updateCo_op(tempData);
			list = co_opManageMapper.queryCoopInfo(tempData.getId());
			Map map = new HashMap<>();
			map.put("object_id", tempData.getId());
			map.put("notice_content", "您申请的" + list.get(0).get("name").toString() + "审核不通过，原因是"
					+ list.get(0).get("comment").toString() + "。");
			map.put("notice_receiver", tempData.getOfficial_user_id());

			return ResultUtil.success(co_opManageMapper.addCoopAduitNotice(map));
		} else {
			return ResultUtil.success(co_opManageMapper.updateCo_op(tempData));
		}
	}

	@Override
	@Transactional
	public Result<Object> addCo_opMember(Co_op_Member tempData) {
		// 查询所有品种信息
		List<Map> categroyList = fieldManageMapper.queryAllCategroy();

		// 社员如果没有上传头像 则给个默认头像
		if ("".equals(tempData.getImg_url())) {
			tempData.setImg_url("1537932302213128.png");
		}

		// 根据tel 判断是否为平台用户 判断是否已经是当前合作社社员了（不允许重复添加）
		Map map = new HashMap();
		String tel = tempData.getTelephone();
		map = co_opManageMapper.queryUserID(tel);
		if (map != null) {
			tempData.setUser_id(String.valueOf(map.get("id")));
		}

		// 判断是否已经是合作社成员了
		Map isCoopMember = co_opManageMapper.queryIsAlreadyCoopMember(tempData.getCoop_id(), tel);
		if (Integer.parseInt(isCoopMember.get("isCoopMember").toString()) > 0) {
			return ResultUtil.error(500, "该用户已经是合作社成员了");
		}


		if (tempData.getAssistant() != null && tempData.getAssistant() != "") { // 后台管理新增社员时可以指定是否为技术员

			if ("0".equals(tempData.getAssistant())) { // 新增的社员被指定为技术员
														// 这里为该社员添加技术员身份(注意技术员必须为平台用户
														// 技术员只能属于一个合作社   技术员 合作社角色不能同时属于一个用户)
				if (map != null) {

					// 判断是否已经是技术员
					Map isAlreadyAssistantOtherCoopMap = co_opManageMapper
							.queryIsAlreadyAssistantOtherCoop((int) map.get("id"));
					if (Integer.parseInt(isAlreadyAssistantOtherCoopMap.get("NUM").toString()) > 0) {
						return ResultUtil.error(500, "该用户已经是其它合作社的技术员了");
					}


					// 判断是否是合作社管理员
					Map isOtherCoopManager=co_opManageMapper.queryIsAlreadyCoopManager((int)map.get("id"));
					if (Integer.parseInt(isOtherCoopManager.get("NUM").toString())>0){
						return ResultUtil.error(500,"该用户已经申请其它合作社了(有可能还在审核),不能成为技术员");
					}


					// 为该合作社成员添加技术员角色 先删再插入
					int i = co_opManageMapper.deleteRole((Integer) map.get("id"), 4);
					i = co_opManageMapper.insertRole((Integer) map.get("id"), 4);

				} else {
					return ResultUtil.error(500, "技术员必须为本平台用户");
				}

			}
		} else { // APP段新增社员时没有指定是否技术员 默认不是技术员
			tempData.setAssistant("1"); // 助手（技术员）标识 0 是 1 不是
		}

		tempData.setState("0"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		tempData.setPlant_cat_id(ExchangeCategroyNameID.NameToId(tempData.getPlant_cat_id(), categroyList));

		int i = co_opManageMapper.insertCo_opMember(tempData);
		if (i > 0) {
			co_opManageMapper.updateCoopTotalAreaAdd(tempData.getCoop_id(), tempData.getPlant_area());
		}
		return ResultUtil.success(i);
	}

	@Override
	public Result<Object> getCo_opMember(int ID) {
		List<Map> categroyList = fieldManageMapper.queryAllCategroy();

		Map temp = co_opManageMapper.queryCo_opMember(ID);

		temp.put("plant_cat_id", ExchangeCategroyNameID.IDToName(temp.get("plant_cat_id").toString(), categroyList));

		return ResultUtil.success(temp);
	}

	@Override
	@Transactional
	public Result<Object> deleteCo_opMember(int id) {
		Co_op_Member tempData = new Co_op_Member();
		tempData.setId(id);
		tempData.setState("1"); // 数据是否可用： 0 可用 1 不可用（数据删除时至为1）
		int i = co_opManageMapper.updateCo_opMember(tempData);
		if (i > 0) { // 社员删除时  合作社的种植面积对应减少
			co_opManageMapper.updateCoopTotalAreaReduce(id);
		}
		return ResultUtil.success(co_opManageMapper.updateCo_opMember(tempData));
	}

	@Override
	@Transactional
	public Result<Object> updateCo_opMember(Co_op_Member tempData) {

		// 先查询该社员之前的详细信息
		Map coopMemberInfo = co_opManageMapper.queryCo_opMember(tempData.getId());

		if (tempData.getAssistant() != null) {

			if ("0".equals(tempData.getAssistant())) { // 社员变更为技术员时 要判断其是不是平台用户
														// 是否是其它合作社的技术员  是否是其它合作社的管理员
				if (coopMemberInfo.get("user_id") != null && coopMemberInfo.get("user_id") != "") {

					// 是否是其它合作社的技术员
					Map isAlreadyAssistantOtherCoopMap = co_opManageMapper
							.queryIsAlreadyAssistantOtherCoop((int) coopMemberInfo.get("user_id"));
					if (Integer.parseInt(isAlreadyAssistantOtherCoopMap.get("NUM").toString()) > 0) {
						return ResultUtil.error(500, "该用户已经是其它合作社的技术员了");
					}


					// 是否是其它合作社的管理员 (不能同时有合作社角色和技术员角色)
					Map isOtherCoopManager=co_opManageMapper.queryIsAlreadyCoopManager((int)coopMemberInfo.get("user_id"));
					if (Integer.parseInt(isOtherCoopManager.get("NUM").toString())>0){
						return ResultUtil.error(500,"该用户已经申请其它合作社了(有可能还在审核),不能再成为技术员");
					}


					// 为该合作社成员添加技术员角色 先删再插入
					int i = co_opManageMapper.deleteRole((Integer) coopMemberInfo.get("user_id"), 4);
					i = co_opManageMapper.insertRole((Integer) coopMemberInfo.get("user_id"), 4);


					// 给社员发送通知 告知其已被设为技术员
					Map param=new HashMap();
					param.put("coopID",coopMemberInfo.get("coop_id"));
					Map coopInfo=co_opManageMapper.queryCo_opByCondition(param).get(0);

					Notice notice=new Notice();
					notice.setNotice_type("2");
					notice.setObject_id((int)coopMemberInfo.get("coop_id"));
					notice.setNotice_content("你已被设为技术员,请退出重新登录变更角色权限。");
					notice.setPublish_user_id((int)coopInfo.get("adminID"));
					notice.setNotice_receiver(coopMemberInfo.get("user_id").toString());
//					notice.setPublish_time("发布时间");
					notice.setStatus("1");
					notice.setState("1");
//					notice.setSend_time("发送时间");
					notice.setIs_send("1");

					co_opManageMapper.insertNotice(notice);

				} else {
					return ResultUtil.error(500, "技术员必须为本平台用户");
				}
			} else {
				co_opManageMapper.deleteRole((Integer) coopMemberInfo.get("user_id"), 4);
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
		List<Map> list1 = new ArrayList<Map>();
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
		List<Map> allCategroyInfo = fieldManageMapper.queryAllCategroy();

		List<Map> coopMemberList = co_opManageMapper.queryCoopMemberList2(coop_id);
		for (Map obj : coopMemberList) {
			obj.put("plant_cat_id",
					ExchangeCategroyNameID.IDToName(String.valueOf(obj.get("plant_cat_id")), allCategroyInfo));
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
			if (list.get(i).get("official").toString().equals("1")) {
				list.get(i).put("role", "管理员");
			} else {
				if (list.get(i).get("assistant").toString().equals("0")) {
					list.get(i).put("role", "技术员");
				} else {
					list.get(i).put("role", "社员");
				}
			}
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
	public Result<Object> addNotice(String allUserId, String msg, String coopID, int publishUserID,
			String picture_url) {
		Map map = new HashMap<>();
		map.put("allUserId", allUserId);
		map.put("msg", msg);
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
