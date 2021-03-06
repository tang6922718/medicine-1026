package com.bonc.medicine.controller.mall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Article;
import com.bonc.medicine.entity.mall.Case;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.mall.MeetProfessorService;
import com.bonc.medicine.utils.ResultUtil;

@RestController
public class MeetProfessorController {

	@Autowired
	private MeetProfessorService meetProfessorService;

	/*
	 * 答疑管理 疑问列表msg是内容recisited是否回访0否1是
	 */
	@GetMapping("/meetProfessor/queslist")
	public Result<Object> queslist(String issue_status, String revisited) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		list = meetProfessorService.queslist(issue_status, revisited);
		list1 = meetProfessorService.assistlist();
		if (list != null && list.size() != 0) {
			if (list1 != null && list1.size() != 0) {
				for (int i = 0; i < list1.size(); i++) {
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).get("id").equals(list1.get(i).get("issueid"))) {
							list.get(j).put("assit", list1.get(i).get("assit"));
						}
					}
				}
			}
			Result<Object> result = new Result<Object>();
			result.setCode(200);
			result.setMsg("成功");
			result.setData(list);
			return result;
		}
		throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
	}

	/*
	 * 设为案例
	 */
	@PostMapping("/meetProfessor/case")
	public Result<Object> anli(@RequestBody Case anli) {
		return meetProfessorService.anli(anli);
	}

	/*
	 * 设置回访
	 */
	@GetMapping("/meetProfessor/revisited")
	public Result<Object> revisited(Integer id, String comment, String close) {
		return meetProfessorService.revisited(id, comment, close);
	}

	/*
	 * 答疑未开始列表
	 */
	@GetMapping("/meetProfessor/untreated")
	public Result<Object> untreated(Integer spec_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = meetProfessorService.untreated(spec_id);
		String time = null;
		Integer time1 = 0;
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				time1 = Integer.parseInt(list.get(i).get("time").toString());
				if (time1 < 0) {
					time = "最近发布";
				}
				if (time1 >= 0 && time1 <= 59) {
					time = time + "秒前";
				}
				if (time1 >= 60 && time1 <= 3599) {
					time = (time1 / 60) + "分钟前";
				}
				if (time1 >= 3600 && (time1 <= (24 * 60 * 60 - 1))) {
					time = (time1 / 3600) + "小时";
				}
				if (time1 >= 24 * 60 * 60) {
					time = (time1 / (24 * 60 * 60)) + "天前";
				}
				list.get(i).put("time", time);
			}
			Result<Object> result = new Result<Object>();
			result.setCode(200);
			result.setMsg("成功");
			result.setData(list);
			return result;
		}
		return ResultUtil.error(ResultEnum.NO_CONTENT);
	}

	/*
	 * 答疑进行中列表
	 */
	@GetMapping("/meetProfessor/solving")
	public Result<Object> solving(Integer spec_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = meetProfessorService.solving(spec_id);
		String time = null;
		Integer time1 = 0;
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				time1 = Integer.parseInt(list.get(i).get("reply_time").toString());
				if (time1 < 0) {
					time = "最近发布";
				}
				if (time1 >= 0 && time1 <= 59) {
					time = time + "秒前";
				}
				if (time1 >= 60 && time1 <= 3599) {
					time = (time1 / 60) + "分钟前";
				}
				if (time1 >= 3600 && (time1 <= (24 * 60 * 60 - 1))) {
					time = (time1 / 3600) + "小时";
				}
				if (time1 >= 24 * 60 * 60) {
					time = (time1 / (24 * 60 * 60)) + "天前";
				}
				list.get(i).put("reply_time", time);
			}
			for (int i = 0; i < list.size(); i++) {
				if (String.valueOf(list.get(i).get("from_uid")).equals(String.valueOf(list.get(i).get("to_uid")))) {
				} else if (String.valueOf(list.get(i).get("from_uid")).equals(spec_id.toString())) {
					list.get(i).put("reply_person_msg", "我回复" + String.valueOf(list.get(i).get("NAME")));
				} else if (String.valueOf(list.get(i).get("to_uid")).equals(spec_id.toString())) {
					list.get(i).put("reply_person_msg", list.get(i).get("NAME").toString() + "回复我");
				}
			}
			Result<Object> result = new Result<Object>();
			result.setCode(200);
			result.setMsg("成功");
			result.setData(list);
			return result;
		}
		return ResultUtil.error(ResultEnum.NO_CONTENT);
	}

	/*
	 * 答疑已解决列表
	 */
	@GetMapping("/meetProfessor/solved")
	public Result<Object> solved(Integer spec_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = meetProfessorService.solved(spec_id);
		String time = null;
		Integer time1 = 0;
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				time1 = Integer.parseInt(list.get(i).get("issue_time").toString());
				if (time1 < 0) {
					time = "最近发布";
				}
				if (time1 >= 0 && time1 <= 59) {
					time = time + "秒前";
				}
				if (time1 >= 60 && time1 <= 3599) {
					time = (time1 / 60) + "分钟前";
				}
				if (time1 >= 3600 && (time1 <= (24 * 60 * 60 - 1))) {
					time = (time1 / 3600) + "小时";
				}
				if (time1 >= 24 * 60 * 60) {
					time = (time1 / (24 * 60 * 60)) + "天前";
				}
				list.get(i).put("issue_time", time);
			}
			Result<Object> result = new Result<Object>();
			result.setCode(200);
			result.setMsg("成功");
			result.setData(list);
			return result;
		}
		return ResultUtil.error(ResultEnum.NO_CONTENT);
	}

	/*
	 * 设置回访
	 */
	@GetMapping("/meetProfessor/setRevisit")
	public Result<Object> setRevisit(Integer id, Integer follow_days, String revisit_url, String revisited_advice) {
		return ResultUtil.success(meetProfessorService.setRevisit(id, follow_days, revisit_url, revisited_advice));
	}

	/*
	 * 管理员关闭问题issue_status关闭传3否则不传
	 */
	@GetMapping("/meetProfessor/eddIssue")
	public Result<Object> eddIssue(Integer id, String revisited_mark, String issue_status) {
		return ResultUtil.success(meetProfessorService.eddIssue(id, revisited_mark, issue_status));
	}

	/*
	 * 答疑状态数据统计
	 */
	@GetMapping("/meetProfessor/issue_statistics")
	public Result<Object> issue_statistics(Integer spec_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 未处理
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 进行中
		list = meetProfessorService.issue_statistics_untreated(spec_id);
		list1 = meetProfessorService.issue_statistics_solving(spec_id);
		list.addAll(list1);
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData(list);
		return result;
	}

	/*
	 * 查询此问题已经被协助的专家id,传入问题id
	 */
	@GetMapping("/meetProfessor/expert/{id}")
	public Result<Object> expert(@PathVariable Integer id) {
		return meetProfessorService.expert(id);
	}

	/*
	 * 邀请专家解答问题传入问题id,专家expert格式为1,2,3,4
	 */
	@GetMapping("/meetProfessor/add/Invitation")
	public Result<Object> Invitation(Integer id, String expert, Integer user_id) {
		meetProfessorService.deleteInvitation(id);
		// 问题状态修改，邀请专家将未处理改为进行中
		meetProfessorService.setSolving(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map = meetProfessorService.queryIssue(id);
		Map param = new HashMap();
		param.put("object_id", id);
		String[] a = expert.split(",");
		for (int i = 0; i < a.length; i++) {
			meetProfessorService.Invitation(id, a[i]);
		}
		meetProfessorService.addInvitationNotice(id, expert, map.get("issue_desc").toString(), user_id);
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData("更新完毕");
		return result;
	}

	/*
	 * 用户关闭问题
	 */
	@GetMapping("/meetProfessor/end")
	public Result<Object> end(Integer id, Integer score) {
		return meetProfessorService.end(id, score);
	}

	/*
	 * 我的贡献--文章列表，传入专家id
	 */
	@GetMapping("/meetProfessor/articlelist/{id}")
	public Result<Object> articlelist(@PathVariable Integer id) {
		return meetProfessorService.articlelist(id);
	}

	/*
	 * 上传文件管理
	 */
	@GetMapping("/meetProfessor/fileManage")
	public Result<Object> fileManage(String key, String starttime, String endtime, String status) {
		return meetProfessorService.fileManage(key, starttime, endtime, status);
	}

	/*
	 * 文章撤销status:0 可用 1 不可用
	 */
	@GetMapping("/meetProfessor/update/articleStatus")
	public Result<Object> articleStatus(Integer id, String status) {
		meetProfessorService.articleStatus(id, status);
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData("更新完毕");
		return result;
	}

	/*
	 * 文章审核is_audit:
	 */
	@GetMapping("/meetProfessor/aduitArticle")
	public Result<Object> aduitArticle(Integer id, String is_audit, String fail_opinion) {
		meetProfessorService.aduitArticle(id, is_audit, fail_opinion);
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData("更新完毕");
		return result;
	}

	/*
	 * 新增文章注意审核字段
	 */
	@PutMapping("/meetProfessor/add/Article")
	@Authorization
	public Result<Object> addArticle(@RequestBody Article article, @CurrentUser String crreate_user_id) {
		article.setCreate_user_id(Integer.valueOf(crreate_user_id));

		meetProfessorService.addArticle(article);
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData("更新完毕");
		return result;
	}

	/*
	 * 获取新增专家资源专家信息
	 */
	@GetMapping("/meetProfessor/queryArticleList")
	public Result<Object> queryArticleList() {

		return meetProfessorService.queryArticleList();
	}

	/*
	 * 修改文章
	 */
	@MethodLog(remark = "修改,修改文章,知识库")
	@PutMapping("/meetProfessor/update/Article")
	public Result<Object> updateArticle(@RequestBody Article article) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String update_time = sdf.format(new Date());
		article.setUpdate_time(update_time);
		meetProfessorService.updateArticle(article);
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData("更新完毕");
		return result;
	}

	/*
	 * 审核文章查询列表
	 */
	@GetMapping("/meetProfessor/get/articlelist")
	public Result<Object> getArticlelist(String key) {
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData(meetProfessorService.getArticlelist(key));
		return result;
	}
}
