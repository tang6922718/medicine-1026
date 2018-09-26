package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.CommentReplyService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/com_reply")
public class CommentReplyController {
	@Autowired
	CommentReplyService commentReplyService;
	
	@PostMapping("/comment")
	public Result releaseComment(@RequestBody Map param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("reply_time", sdf.format(new Date().getTime()));
		return ResultUtil.success(commentReplyService.insertComment(param));
	}
	
	@PostMapping("/reply")
	public Result releaseReply(@RequestBody Map param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("reply_time", sdf.format(new Date().getTime()));
		return ResultUtil.success(commentReplyService.insertReply(param));
	}
	
	@GetMapping("/replies")
	public Result getCommentList(String object_type, String object_id) {
		Map param = new HashMap<>();
		param.put("object_type", object_type);
		param.put("object_id", object_id);
		List<Map> result = commentReplyService.queryComments(param);
		List commentids = new ArrayList<>();
		for (Map map : result) {
			commentids.add(map.get("id"));
		}
		List<Map> replies = new ArrayList<>();
		if (commentids.size()>0) {
			replies = commentReplyService.queryReplies(commentids);
		}
		result.addAll(replies);
		return ResultUtil.success(result);
	}
	
	@GetMapping("/count")
	public Result commentsCount(String object_type, String object_id) {
		Map param = new HashMap<>();
		param.put("object_type", object_type);
		param.put("object_id", object_id);
		return ResultUtil.success(commentReplyService.commentsCount(param));
	}
	
	@DeleteMapping("/delete")
	public Result deleteComment(String id) {
		return ResultUtil.success(commentReplyService.deleteComment(id));
	}
	
	
	
	
}
