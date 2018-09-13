package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.mapper.mall.CommentReplyMapper;
import com.bonc.medicine.service.mall.CommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentReplyServiceImpl implements CommentReplyService {

	@Autowired
	CommentReplyMapper commentReplyMapper;
	
	@Override
	public int insertComment(Map param) {
		return commentReplyMapper.insertComment(param);
	}

	@Override
	public int insertReply(Map param) {
		return commentReplyMapper.insertReply(param);
	}

	@Override
	public List<Map> queryComments(Map param) {
		return commentReplyMapper.queryComments(param);
	}

	@Override
	public List<Map> queryReplies(String[] comment_ids) {
		return commentReplyMapper.queryReplies(comment_ids);
	}

}
