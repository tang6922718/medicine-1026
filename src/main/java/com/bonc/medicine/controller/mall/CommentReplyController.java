package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.CommentReplyService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/com_reply")
public class CommentReplyController {
	@Autowired
	CommentReplyService commentReplyService;
	
	@PostMapping("/comment")
	public Result releaseComment(@RequestBody Map param) {
		return ResultUtil.success(commentReplyService.insertComment(param));
	}
	
	@PostMapping("/reply")
	public Result releaseReply(@RequestBody Map param) {
		return ResultUtil.success(commentReplyService.insertReply(param));
	}
	
	@GetMapping("/replies")
	public Result getCommentList(String object_type, String object_id) {
		Map param = new HashMap<>();
		param.put("object_type", object_type);
		param.put("object_id", object_id);
		return ResultUtil.success(commentReplyService.queryComments(param));
	}
	
	@GetMapping("/count")
	public Result commentsCount(String object_type, String object_id) {
		Map param = new HashMap<>();
		param.put("object_type", object_type);
		param.put("object_id", object_id);
		return ResultUtil.success(commentReplyService.commentsCount(param));
	}
	
	
	
	
}
