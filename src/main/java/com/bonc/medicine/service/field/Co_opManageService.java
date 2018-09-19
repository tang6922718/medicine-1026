package com.bonc.medicine.service.field;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.field.Co_op;
import com.bonc.medicine.entity.field.Co_op_Member;
import com.bonc.medicine.entity.field.Notice;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName Co_opManageService
 * @Description
 * @Author YQ
 * @Date 2018/8/27 20:08
 * @Version 1.0
 */
public interface Co_opManageService {

	public Result<Object> addCo_op(Co_op tempData); // 新增合作社

	public Result<Object> deleteCo_op(int ID); // 删除合作社

	public Result<Object> getCo_opInfo(Map param); // 合作社信息查询

	public Result<Object> updateCo_op(Co_op tempData); // 合作社信息修改

	public Result<Object> addCo_opMember(Co_op_Member tempData); // 新增合作社成员

	public Result<Object> getCo_opMember(int ID); // 合作社社员详情查询

	public Result<Object> deleteCo_opMember(int id); // 合作社社员删除

	public Result<Object> updateCo_opMember(Co_op_Member tempData); // 合作社社员信息修改

	public Result<Object> queryAssistantNum(int coop_id);// 合作社助手数量查询

	public Result<Object> queryAssistant(int coop_id); // 合作社助手列表查询

	public Result<Object> queryNotAssistant(int coop_id); // 合作社非助手列表查询

	public Result<Object> getCoopMemberList(int coop_id); // 合作社社员列表查询(全带种植数)

	public Result<Object> getCoopMemberList2(int coop_id); // 合作社社员列表查询(部分带种植数)

	public Result<Object> affiliatedCo_op(int user_id);

	public List<Map<String, Object>> findAllMember(Integer user_id);

	public Result<Object> addNotice(String allUserId, String msg,String coopID, int publishUserID,String picture_url);

	public Result<Object> noticeState(Integer id, String state);

	public Result<Object> deleteNotice(Integer id);

	public Result<Object> noticeDetail(Integer id);

	public Result<Object> updateNotice(Integer id, String msg);

	public Result<Object> addRoleNotice(Notice notice);

	public Result<Object> noticelist(String msg, String role, String start_time, String end_time);

	public Result<Object> getCoopNoticeList(int coopID);

}
