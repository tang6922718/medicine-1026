package com.bonc.medicine.service.mall.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bonc.medicine.service.thumb.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.mall.CommentReplyMapper;
import com.bonc.medicine.service.mall.CommentReplyService;

@Service
public class CommentReplyServiceImpl implements CommentReplyService {

	@Autowired
	CommentReplyMapper commentReplyMapper;

	@Autowired
	private IntegralService integralService;
	
	@Override
	public int insertComment(Map param) {
		int num =commentReplyMapper.insertComment(param);
		if("未处理".equals(param.get("status"))){
			updateReplyStatus(param);
		}
		//评论时往common_notice插入信息
		Map notice_param=new HashMap();
		notice_param.put("notice_type", "3");
		notice_param.put("object_id", param.get("object_id")==null?"0":param.get("object_id"));
		notice_param.put("notice_content", param.get("content"));
		notice_param.put("publish_user_id", param.get("from_uid"));
		notice_param.put("notice_receiver", param.get("issue_user_id"));
		insertNoticeCommentOrReply(notice_param);

		//积分代码
		Map<String, String> ppparamMap = new HashMap<>();
		//userId;actionCode
		ppparamMap.put("userId", param.get("from_uid") + "");
		ppparamMap.put("actionCode", "DO_REPLY");
		try{

			integralService.addIntegralHistory(ppparamMap);
		}catch (Exception e){
			System.out.println("ERROR ：新建田间操作中---增加积分异常");
		}
		return num;
	}

	@Override
	public int insertReply(Map param) {
		//回复时向common_notice插入信息
		Map notice_param=new HashMap();
		notice_param.put("notice_type", "3");
		notice_param.put("object_id", param.get("object_id")==null?"0":param.get("object_id"));
		notice_param.put("notice_content", param.get("content"));
		notice_param.put("publish_user_id", param.get("from_uid"));
		notice_param.put("notice_receiver", param.get("issue_user_id"));
		insertNoticeCommentOrReply(notice_param);
		return commentReplyMapper.insertReply(param);
	}

	@Override
	public List<Map> queryComments(Map param) {
		return commentReplyMapper.queryComments(param);
	}

	@Override
	public List<Map> queryReplies(List comment_ids) {
		return commentReplyMapper.queryReplies(comment_ids);
	}

	@Override
	public int commentsCount(Map param) {
		List<Map> result = commentReplyMapper.commentsCount(param);
		if (result.size() != 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
		return Integer.parseInt(result.get(0).get("count")+"");
	}

	@Override
	public int deleteComment(String id) {
		int rows = commentReplyMapper.deleteComment(id);
		if (rows < 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
		return rows;
	}

	@Override
	public void updateReplyStatus(Map param) {
		commentReplyMapper.updateReplyStatus(param);
	}

	@Override
	public int insertNoticeCommentOrReply(Map param) {
		
		return commentReplyMapper.insertNoticeCommentOrReply(param);
	}

}
