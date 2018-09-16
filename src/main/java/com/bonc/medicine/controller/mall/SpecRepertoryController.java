package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Issue;
import com.bonc.medicine.entity.mall.Specialist;
import com.bonc.medicine.service.mall.SpecialistService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spec_repertory")
public class SpecRepertoryController {
	@Autowired
	SpecialistService specialistService;
	
	
	/**
	 * 新建专家角色
	 * @param specialist
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/new")
	@Transactional
	public Result insertSpecialist(Specialist specialist) {
		specialistService.craeteCharactor(specialist);
		Map param = new HashMap<>();
		param.put("spec_id", specialist.getSpec_id());
		if (specialist.getCat_code() != null && specialist.getCat_code().length != 0) {
			param.put("cat_code", specialist.getCat_code());
			specialistService.insertCatSpec(param);
		}
		if (specialist.getSubject_code() != null && specialist.getSubject_code().length != 0) {
			param.put("subject_code", specialist.getSubject_code());
			specialistService.insertSubSpec(param);
		}
		return ResultUtil.success();
	}
	
	/**
	 * 修改专家信息
	 * @param specialist
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping("/info")
	@Transactional
	public Result updateInfo(Specialist specialist) {
		int spec_id = specialist.getSpec_id();
		specialistService.delCatSpec(spec_id+"");
		specialistService.delSubSpec(spec_id+"");
		specialistService.updateInfo(specialist);
		Map param = new HashMap<>();
		param.put("spec_id", spec_id);
		if (specialist.getCat_code() != null && specialist.getCat_code().length != 0) {
			param.put("cat_code", specialist.getCat_code());
			specialistService.insertCatSpec(param);
		}
		if (specialist.getSubject_code() != null && specialist.getSubject_code().length != 0) {
			param.put("subject_code", specialist.getSubject_code());
			specialistService.insertSubSpec(param);
		}
		return ResultUtil.success();
	}
	
	/**
	 * 获取品类和学科列表
	 * @param limit
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/cat_sub")
	public Result cat_subList(int limit) {
		Map result = new HashMap();
		Map param = new HashMap<>();
		param.put("limit", limit);
		result.put("catList", specialistService.catalogList(param));
		result.put("subList", specialistService.subjectList());
		
		return ResultUtil.success(result);
	}
	
	/**
	 * 专家列表
	 * @param name 按名字查
	 * @param cat_code 按品类查
	 * @param subject_code 按学科查
	 * @param user_id 当前用户
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/spec_list")
	public Result specList(String name, String cat_code, String subject_code,String user_id) {
		Map param = new HashMap<>();
		param.put("name", name);
		param.put("cat_code", cat_code);
		param.put("subject_code", subject_code);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();//存关注数据
		list = specialistService.specialList(param);
		if(user_id!=null)
		{
			Map param1 = new HashMap<>();
			param1.put("user_id", user_id);
			list1 = specialistService.specialIsFollow(param1);
			for(int i=0;i<list.size();i++){
				for(int j=0;j<list1.size();j++){
					if(list.get(i).get("spec_id").toString().equals(list1.get(j).get("object_id").toString())){
						list.get(i).put("is_follow", 1);
					}
				}
			}
		}
		for(int i=0;i<list.size();i++){
			Map param1 = new HashMap<>();
			param1.put("spec_id", list.get(i).get("spec_id").toString());
			list.get(i).put("sub", specialistService.sub(param1).toString());
			list.get(i).put("cat", specialistService.cat(param1).toString());
		}
		return ResultUtil.success(list);
	}
	
	/**
	 * 专家详情
	 * @param spec_id 专家编号
	 * @param user_id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/detail")
	public Result specDetail(String spec_id,String user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();//返回关注数据
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();//返回专家学科数据数据
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();//返回专家品类数据
		Map param = new HashMap<>();
		param.put("spec_id", spec_id);
		list= specialistService.specDetail(param);
		if(user_id!=null)
		{
			Map param1 = new HashMap<>();
			param1.put("user_id", user_id);
			list1 = specialistService.specialIsFollow(param1);
			for(int i=0;i<list.size();i++){
				for(int j=0;j<list1.size();j++){
					if(list.get(i).get("spec_id").toString().equals(list1.get(j).get("object_id").toString())){
						list.get(i).put("is_follow", 1);
					}
				}
			}
		}
		list2 = specialistService.sub(param);
		if(list2.size()!=0 && list2!=null){
			list.get(0).put("sub", list2.get(0).get("sub").toString());
		}
		list3 = specialistService.cat(param);
		if(list3.size()!=0 && list3!=null){
			list.get(0).put("cat", list3.get(0).get("cat").toString());
		}
		return ResultUtil.success(list);
	}
	
	/**
	 * 专家文章列表
	 * @param spec_id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/articles")
	public Result articleList(String spec_id) {
		return ResultUtil.success(specialistService.articleList(spec_id));
	}
	
	/**
	 * 文章详情
	 * @param id 文章编号
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/articles/detail")
	public Result articleDetail(String id) {
		return ResultUtil.success(specialistService.articleDetail(id));
	}
	
	/**
	 * 专家案例列表
	 * @param spec_id 专家编号
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/cases")
	public Result caseList(String spec_id) {
		return ResultUtil.success(specialistService.caseList(spec_id));
	}
	
	/**
	 * 案例详情
	 * @param id 案例编号
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/cases/detail")
	public Result caseDetail(String id) {
		return ResultUtil.success(specialistService.caseDetail(id));
	}
	
	/**
	 * 向专家提问
	 * @param issue
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/issue")
	public Result releaseIssue(Issue issue) {
		return ResultUtil.success(specialistService.releaseIssue(issue));
	}
	
	/**
	 * 我的提问
	 * @param user_id 用户编号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/my_issue")
	public Result myIssueList(String user_id) {
		return ResultUtil.success(specialistService.myIssues(user_id));
	}
	
	/**
	 * 提问详情
	 * @param issue_id 问题编号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/issue/detail")
	public Result issueDetail(String issue_id) {
		return ResultUtil.success(specialistService.issueDetail(issue_id));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/file")
	public Result uploadFile(String title, String spec_id, String file_size, String file_url, String status) {
		Map param = new HashMap();
		param.put("title", title);
		param.put("spec_id", spec_id);
		param.put("file_size", file_size);
		param.put("file_url", file_url);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
		param.put("upload_time", sdf.format(new Date()));
		param.put("status", status);
		
		return ResultUtil.success(specialistService.uploadFile(param));
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/videos")
	public Result videoList(String spec_id) {
		return ResultUtil.success(specialistService.videoList(spec_id));
	}
	/*
	 * 资源上传记录
	 */
	@GetMapping("/uploadRecord")
	public Result uploadRecord(Integer spec_id, String title, String status, String start, String end) {
		Map param = new HashMap<>();
		param.put("spec_id", spec_id);
		param.put("title", title);
		param.put("status", status);
		param.put("start", start);
		param.put("end", end);
		return ResultUtil.success(specialistService.uploadRecord(param));
	}
}
