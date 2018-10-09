package com.bonc.medicine.controller.mall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.CommentReplyService;
import com.bonc.medicine.service.thumb.ThumbService;
import com.bonc.medicine.utils.ResultUtil;

@RestController
@RequestMapping("/com_reply")
public class CommentReplyController {
	@Autowired
	CommentReplyService commentReplyService;
	
	 @Autowired
	 ThumbService thumbService;  //点赞
	
	@PostMapping("/comment")
	public Result releaseComment(@RequestBody Map param ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("reply_time", sdf.format(new Date().getTime()));
		//param.put("user_id", user_id);
		return ResultUtil.success(commentReplyService.insertComment(param));
	}
	
	@PostMapping("/reply")
	public Result releaseReply(@RequestBody Map param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("reply_time", sdf.format(new Date().getTime()));
		//param.put("user_id", user_id);
		return ResultUtil.success(commentReplyService.insertReply(param));
	}
	
	@GetMapping("/replies")
	public Result getCommentList(String object_type, String object_id,@CurrentUser String user_id) {
		Map param = new HashMap<>();
		param.put("object_type", object_type);
		param.put("object_id", object_id);
		List<Map> result = commentReplyService.queryComments(param);
		List commentids = new ArrayList<>();
		if(result.size()>0 && result.get(0)!=null && !result.get(0).isEmpty()){			            
			for (Map map : result) {			
				commentids.add(map.get("id"));
				 // 此处引用 点赞 的接口
	            Map<String, String> comment_param = new HashMap<>();
	            comment_param.put("type", "9");//评论类型为9
	            comment_param.put("acceptThumbId", map.get("id")+"");
	            Map<String, Object> dianzan = thumbService.thumbNumber(comment_param);
	            Object thumbNumber = dianzan.get("thumbNumber");
	            
	            // 此处引用 当前用户是否对该条评论点赞
	            int thumbStatus = thumbService.thumbStatus(user_id, "9", map.get("id")+"");
	            map.put("thumb",thumbNumber);
	            map.put("thumbStatus",thumbStatus);//1：点赞   0： 未点赞 ;-999:参数不全
			}
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
