package com.bonc.medicine.mapper.field;


import com.bonc.medicine.entity.field.Co_op;
import com.bonc.medicine.entity.field.Co_op_Member;
import com.bonc.medicine.entity.field.Notice;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName Co_opManageMapper
 * @Description
 * @Author YQ
 * @Date 2018/8/28 11:18
 * @Version 1.0
 */
public interface Co_opManageMapper {

    public int insertCo_op(Co_op tempData); // 新增合作社

    public int insertCo_opMember(Co_op_Member tempData); // 新增合作社成员


    public List<Map> queryCo_opByCondition(Map params); // 按条件查询合作社信息

    public Map queryPlantNum(int coop_id); // 合作社登记种植总数查询

    public Map queryAssistantNum(int coop_id);// 合作社助手数量查询

    public Map queryCo_opMemberNum(int coop_id); //合作社总人数查询

    public Map queryCo_opNoticeNum(int coop_id); //合作社总通知数查询


    public Map queryUserID(String tel); // 去user表查询是否存在 并返回信息

    public List<Map> queryAssistant(int coop_id); // 合作社助手列表查询

    public List<Map> queryNotAssistant(int coop_id); // 合作社非助手列表查询

    public Map queryCo_opMember(int ID); // 合作社社员详情查询

    public List<Map> queryCoopMemberList(int coop_id); // 合作社社员列表查询
    
    public List<Map<String, Object>> affiliatedCo_op(int user_id);
    
    public List<Map<String, Object>> co_opTotal(int coop_id);

    public int updateCo_op(Co_op tempData); //合作社信息修改   删除合作社（逻辑上删除）

    public int updateCo_opMember(Co_op_Member tempData); // 合作社社员删除 (逻辑上删除)  合作社社员信息修改
    
    public List<Map<String, Object>> findAllMember(Map map);
    
    public int addNotice(Map map); //通知表插入信息
    
    public int noticeState(Map map);
    
    public int deleteNotice(Map map);
    
    public List<Map<String, Object>> noticeDetail(Map map);

    public int updateNotice(Map map);
    
    public int addRoleNotice(Notice notice);
    
    public List<Map<String, Object>> noticelist(Map map);
}
