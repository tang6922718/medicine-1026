package com.bonc.medicine.controller.mall;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Article;
import com.bonc.medicine.entity.mall.Case;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.mall.MeetProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * 点评内容编辑
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
	@GetMapping("/meetProfessor/untreated/{spec_id}")
	public Result<Object> untreated(@PathVariable Integer spec_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = meetProfessorService.untreated(spec_id);
		String time = null;
		Integer time1 = 0;
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				time1 = Integer.parseInt(list.get(i).get("time").toString());
				if (time1 < 0) {
					time = "时间不正确";
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
		return null;
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
	@PostMapping("/meetProfessor/add/Invitation/{id}/{expert}")
	public Result<Object> Invitation(@PathVariable Integer id, @PathVariable String expert) {
		meetProfessorService.deleteInvitation(id);
		String[] a = expert.split(",");
		for (int i = 0; i < a.length; i++) {
			meetProfessorService.Invitation(id, a[i]);
		}
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData("更新完毕");
		return result;
	}

	/*
	 * 关闭问题
	 */
	@PutMapping("/meetProfessor/end/{id}/{score}")
	public Result<Object> end(@PathVariable Integer id, @PathVariable Integer score) {
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
	@GetMapping("/meetProfessor/fileManage/{key}/{starttime}/{entime}/{status}")
	public Result<Object> fileManage(@PathVariable String key, @PathVariable String starttime,
			@PathVariable String endtime, @PathVariable String status) {
		return meetProfessorService.fileManage(key, starttime, endtime, status);
	}

	/*
	 * 文章撤销status:0 可用 1 不可用
	 */
	@PutMapping("/meetProfessor/update/articleStatus/{id}/{status}")
	public Result<Object> articleStatus(@PathVariable Integer id, @PathVariable String status) {
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
	public Result<Object> addArticle(@RequestBody Article article) {
		meetProfessorService.addArticle(article);
		Result<Object> result = new Result<Object>();
		result.setCode(200);
		result.setMsg("成功");
		result.setData("更新完毕");
		return result;
	}

	/*
	 * 修改文章
	 */
	@PutMapping("/meetProfessor/update/Article")
	public Result<Object> updateArticle(@RequestBody Article article) {
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
