package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;

public interface CommentReplyService {
	public int insertComment(Map param);
	public int insertReply(Map param);
	public List<Map> queryComments(Map param);
	public int commentsCount(Map param);
	public List<Map> queryReplies(List comment_ids);
}
