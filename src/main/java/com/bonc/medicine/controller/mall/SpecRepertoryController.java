package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Issue;
import com.bonc.medicine.entity.mall.Specialist;
import com.bonc.medicine.service.mall.SpecialistService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spec_repertory")
public class SpecRepertoryController {
	@Autowired
	SpecialistService specialistService;
	
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/spec_list")
	public Result specList(String name, String cat_code, String subject_code,String user_id) {
		Map param = new HashMap<>();
		param.put("name", name);
		param.put("cat_code", cat_code);
		param.put("subject_code", subject_code);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
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
		return ResultUtil.success(list);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/detail")
	public Result specDetail(String spec_id,String user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
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
		return ResultUtil.success(list);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/articles")
	public Result articleList(String spec_id) {
		return ResultUtil.success(specialistService.articleList(spec_id));
	}
	
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/articles/detail")
	public Result articleDetail(String id) {
		return ResultUtil.success(specialistService.articleDetail(id));
	}
	
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/cases")
	public Result caseList(String spec_id) {
		return ResultUtil.success(specialistService.caseList(spec_id));
	}
	
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/cases/detail")
	public Result caseDetail(String id) {
		return ResultUtil.success(specialistService.caseDetail(id));
	}
	
	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/issue")
	public Result releaseIssue(Issue issue) {
		return ResultUtil.success(specialistService.releaseIssue(issue));
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/my_issue")
	public Result myIssueList(String user_id) {
		return ResultUtil.success(specialistService.myIssues(user_id));
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/issue/detail")
	public Result issueDetail(String issue_id) {
		return ResultUtil.success(specialistService.issueDetail(issue_id));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/file")
	public Result uploadFile(String title, String spec_id, String file_size, String file_url, String upload_time, String status) {
		Map param = new HashMap();
		param.put("title", title);
		param.put("spec_id", spec_id);
		param.put("file_size", file_size);
		param.put("file_url", file_url);
		param.put("upload_time", upload_time);
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
	@GetMapping("/uploadRecord/{spec_id}")
	public Result uploadRecord(@PathVariable Integer spec_id) {
		return ResultUtil.success(specialistService.uploadRecord(spec_id));
	}
}
