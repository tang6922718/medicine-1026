package com.bonc.medicine.mapper.mall;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CommentReplyMapper {
	public int insertComment(Map param);
	public int insertReply(Map param);
	public List<Map> queryComments(Map param);
	public List<Map> commentsCount(Map param);
	public List<Map> queryReplies(List comment_ids);
}
