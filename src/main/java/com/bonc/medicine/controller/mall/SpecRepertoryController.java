package com.bonc.medicine.controller.mall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Issue;
import com.bonc.medicine.entity.mall.Specialist;
import com.bonc.medicine.entity.user.User;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.mall.CommentReplyService;
import com.bonc.medicine.service.mall.MeetProfessorService;
import com.bonc.medicine.service.mall.SpecialistService;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.service.user.UserManagerService;
import com.bonc.medicine.service.user.UserService;
import com.bonc.medicine.utils.ResultUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
@RequestMapping("/spec_repertory")
public class SpecRepertoryController {
	@Autowired
	SpecialistService specialistService;
	@Autowired
	private MeetProfessorService meetProfessorService;
	@Autowired
	private ViewNumberService viewNumberService;
	@Autowired
	private AttentionService attentionService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentReplyService commentReplyService;

	@Autowired
	private UserManagerService userManagerService;
	
	/**
	 * 新建专家角色
	 * 
	 * @param specialist
	 * @return
	 */
	@MethodLog(remark = "新增,新增专家账号,专家")
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
	 * 
	 * @param specialist
	 * @return
	 */
	@MethodLog(remark = "修改,修改专家信息,专家")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping("/info")
	@Transactional
	public Result updateInfo(Specialist specialist) {
		int spec_id = specialist.getSpec_id();
		specialistService.delCatSpec(spec_id + "");
		specialistService.delSubSpec(spec_id + "");
		specialistService.updateInfo(specialist);
		//修改用户基本信息 用户名和头像
		specialistService.updateUserBasicInfo(specialist);
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
	 * 
	 * @param limit
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/cat_sub")
	public Result cat_subList(String limit) {
		Map result = new HashMap();
		Map param = new HashMap<>();
		if (limit != null && !"".equals(limit.toString())) {
			param.put("limit", Integer.parseInt(limit));
		}
		result.put("catList", specialistService.catalogList(param));
		result.put("subList", specialistService.subjectList());

		return ResultUtil.success(result);
	}
	/**
	 * 获取品类和学科列表
	 * 
	 * @param limit
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/cat_subList_all")
	public Result cat_subList_all(String limit) {
		Map result = new HashMap();
		Map param = new HashMap<>();
		if (limit != null && !"".equals(limit)) {
			param.put("limit", Integer.parseInt(limit));
		}
		result.put("catListAll", specialistService.catalogListAll(param));
		result.put("subListAll", specialistService.subjectListAll(param));
		
		return ResultUtil.success(result);
	}

	/**
	 * 专家列表
	 * 
	 * @param name
	 *            按名字查
	 * @param cat_code
	 *            按品类查
	 * @param subject_code
	 *            按学科查
	 * @param user_id
	 *            当前用户
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/spec_list")
	public Result specList(String name, String cat_code, String subject_code, String user_id, Integer pageNum,
			Integer pageSize) {

		Map param = new HashMap<>();
		param.put("name", name);
		param.put("cat_code", cat_code);
		param.put("subject_code", subject_code);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 存关注数据
		// 开始分页,不传默认查询全部
		long total = 0L;
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		list = specialistService.specialList(param);
		
		// 如果查询返回的时候没有，那就不进行下面的操作
		if(null == list || list.isEmpty() || list.size() < 1){
			return ResultUtil.successTotal(list, 0);
		}
		
		
		//  TODO  判断
		if (pageNum != null && pageSize != null) {
			total =  list == null ? 0L : ((Page<Map<String,Object>>)list).getTotal();
		}

		//interact_count 活跃天数
		if (user_id != null) {
			for (Map<String, Object> map : list) {
				Map param1 = new HashMap<>();
				param1.put("userId", user_id);
				param1.put("attedUserId", map.get("spec_id") + "");
				param1.put("type", "1");
				Map res = attentionService.attentionRelation(param1);
				map.put("is_follow", res.get("followed"));
			}
		}

		List<String> allIds = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			allIds.add(list.get(i).get("spec_id").toString());
			//follow 
			Map succeed = attentionService.fansList(list.get(i).get("spec_id").toString());

	        Set<String> idSet = (Set<String>)(succeed.get("fansList"));
	        list.get(i).put("follow", idSet.size());
		}
		String idddss = allIds.toString().replaceAll(" ", "");
		List<Map<String, String>> daysList = userManagerService.queryInteractTimes(idddss.toString().substring(1,
				idddss.toString().length() - 1));
		Map<String, String> ddayMap = new HashMap<>();
		for (Map<String, String> daysMap: daysList) {
			String number  = daysMap.get("interactNumber") == null || StringUtils.isBlank(daysMap.get("interactNumber")) ? "0" :  daysMap.get("interactNumber");
			ddayMap.put(daysMap.get("proId") + "", number + "");

		}
		for (int i = 0; i < list.size(); i++) {
			Map param1 = new HashMap<>();
			param1.put("spec_id", list.get(i).get("spec_id").toString());
			list.get(i).put("sub", specialistService.sub(param1).get(0).get("sub").toString());
			list.get(i).put("cat", specialistService.cat(param1).get(0).get("cat").toString());
			list.get(i).put("interact_count", ddayMap.get(list.get(i).get("spec_id") + "") == null ? "0"
                    : ddayMap.get(list.get(i).get("spec_id") + ""));
		}



		return ResultUtil.successTotal(list, total);
	}
	/**
	 * 专家列表（提供给门户使用）
	 * 
	 * @param name
	 *            按名字查
	 * @param cat_code
	 *            按品类查
	 * @param subject_code
	 *            按学科查
	 * @param user_id
	 *            当前用户
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/spec_list_all")
	public Result specListAll(String name, String cat_code, String subject_code, String user_id, Integer pageNum,
			Integer pageSize) {
		
		Map param = new HashMap<>();
		param.put("name", name);
		param.put("cat_code", cat_code);
		param.put("subject_code", subject_code);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 存关注数据
		// 开始分页,不传默认查询全部
		long total = 0L;
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		list = specialistService.specialListAll(param);
		if (pageNum != null && pageSize != null) {
			total =  list == null ? 0L : ((Page<Map<String,Object>>)list).getTotal();
		}
		if (user_id != null) {
			for (Map<String, Object> map : list) {
				Map param1 = new HashMap<>();
				param1.put("userId", user_id);
				param1.put("attedUserId", map.get("spec_id") + "");
				param1.put("type", "1");
				Map res = attentionService.attentionRelation(param1);
				map.put("is_follow", res.get("followed"));
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Map param1 = new HashMap<>();
			param1.put("spec_id", list.get(i).get("spec_id").toString());
			list.get(i).put("sub", specialistService.sub(param1).toString());
			list.get(i).put("cat", specialistService.cat(param1).toString());
			Map succeed = attentionService.fansList(list.get(i).get("spec_id").toString());

	        Set<String> idSet = (Set<String>)(succeed.get("fansList"));
	        list.get(i).put("follow", idSet.size());
		}
		
		return ResultUtil.successTotal(list, total);
	}

	/**
	 * 专家详情
	 * 
	 * @param spec_id
	 *            专家编号
	 * @param user_id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/detail")
	public Result specDetail(String spec_id, String user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 返回关注数据
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();// 返回专家学科数据数据
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();// 返回专家品类数据
		Map param = new HashMap<>();
		param.put("spec_id", spec_id);
		list = specialistService.specDetail(param);
		User user = userService.getUserInfoById(user_id);
		String acount = user.getActive_count();
		Map<String, Object> acMap =  userManagerService.activeDays(spec_id);
        List<Map<String, String>> inMap =  userManagerService.queryInteractTimes(spec_id);

		list.get(0).put("active_count", acMap.get("acDays"));
		list.get(0).put("interact_count", inMap.get(0).get("interactNumber"));
		
		//粉丝数
		Map succeed = attentionService.fansList(list.get(0).get("spec_id").toString());

        Set<String> idSet = (Set<String>)(succeed.get("fansList"));
        list.get(0).put("follow", idSet.size());
		if (user_id != null) {

			Map param1 = new HashMap<>();
			param1.put("userId", user_id);
			param1.put("attedUserId", spec_id);
			param1.put("type", "1");
			Map reMap = attentionService.attentionRelation(param1);
			list.get(0).put("is_follow", reMap.get("followed"));
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list1.size(); j++) {
					if (list.get(i).get("spec_id").toString().equals(list1.get(j).get("object_id").toString())) {
						list.get(i).put("is_follow", reMap.get("followed"));
					}
				}
			}
		}
		list2 = specialistService.sub(param);
		if (list2.size() != 0 && list2 != null) {
			list.get(0).put("sub", list2.get(0).get("sub").toString());
		}
		list3 = specialistService.cat(param);
		if (list3.size() != 0 && list3 != null) {
			list.get(0).put("cat", list3.get(0).get("cat").toString());
		}
		return ResultUtil.success(list);
	}

	/**
	 * 专家文章列表
	 * 
	 * @param spec_id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/articles")
	public Result articleList(String spec_id, String title, String status, String start, String end , Integer pageNum, Integer pageSize) {
		Map param = new HashMap<>();
		param.put("spec_id", spec_id);
		param.put("title", title);
		param.put("status", status);
		param.put("start", start);
		param.put("end", end);
		// 开始分页,不传默认查询全部
		long total = 0L;
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		List<Map> articleMap=specialistService.articleList(param);
		if (pageNum != null && pageSize != null) {
			total =  articleMap == null ? 0L : ((Page<Map>)articleMap).getTotal();
		}
		return ResultUtil.successTotal(articleMap,total);
	}

	@PutMapping("/revokeArt")
	public Result revokeArticle(@RequestBody Map param) {
		return ResultUtil.success(specialistService.revokeArt(param.get("id") + ""));
	}

	/**
	 * 文章详情
	 * 
	 * @param id
	 *            文章编号
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/articles/detail")
	public Result articleDetail(String id) {
		Map result = specialistService.articleDetail(id);
		Map param = new HashMap<>();
		param.put("objectType", "5");
		param.put("objectId", id);
		Map map = new HashMap<>();
		String viewNum = "0";
		if (!(map = viewNumberService.queryViewNumber(param)).isEmpty()) {
			viewNum = (String) map.get("viewNumber");
		}
		result.put("viewNum", viewNum);
		viewNumberService.addOrUpdateViewNumberCord(param);
		return ResultUtil.success(result);
	}

	/**
	 * 专家案例列表
	 * 
	 * @param spec_id
	 *            专家编号
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/cases")
	public Result caseList(String spec_id) {
		return ResultUtil.success(specialistService.caseList(spec_id));
	}

	/**
	 * 案例详情
	 * 
	 * @param id
	 *            案例编号
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/cases/detail")
	public Result caseDetail(String id) {
		return ResultUtil.success(specialistService.caseDetail(id));
	}

	/**
	 * 向专家提问
	 * 
	 * @param issue
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/issue")
	@Transactional
	public Result releaseIssue(@RequestBody Issue issue) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		issue.setIssue_time(sdf.format(new Date().getTime()));
		specialistService.releaseIssue(issue);
		Map param = new HashMap<>();
		param.put("specid", issue.getSpec_id() + "");
		param.put("issueid", issue.getId() + "");
		param.put("is_assigned", "0");
		List params = new ArrayList<>();
		params.add(param);
		specialistService.insertIssueRel(params);
		return ResultUtil.success();
	}

	@SuppressWarnings({ "rawtypes" })
	@PutMapping("/invite")
	public Result inviteOtherSpec(@RequestBody Map params) {
		String issueid = params.get("issueid") + "";
		List specids = (List) params.get("specids");
		List list = new ArrayList<>();
		for (int i = 0; i < specids.size(); i++) {
			Map one = new HashMap<>();
			one.put("specid", specids.get(i) + "");
			one.put("issueid", issueid);
			one.put("is_assigned", "1");
			list.add(one);
		}
		return ResultUtil.success(specialistService.insertIssueRel(list));
	}

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/inviteList")
	@Transactional
	public Result inviteList(String cat_code, String subject_code, String issue_id) {
		Map param = new HashMap<>();
		param.put("cat_code", cat_code);
		param.put("subject_code", subject_code);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		list = specialistService.specialList(param);
		Map resultMap = new HashMap<>();
		resultMap.put("invitedNum", 0);
		if (issue_id != null && !issue_id.equals("")) {
			Result result = meetProfessorService.expert(Integer.parseInt(issue_id));
			if (result.getCode() == 200) {
				List<Map> res = (List) result.getData();
				int num=0;
				for (Map map : res) {
					String specid = map.get("specid") + "";
					for (Map<String, Object> map2 : list) {
						if (specid.equals(map2.get("spec_id") + "")) {
							map2.put("invited", "1");
							num++;
						}
						if(!"1".equals(map2.get("invited"))){
							map2.put("invited", "0");
						}
					}
				}
				resultMap.put("invitedNum", num);
			}
		}
		 Collections.sort(list, new Comparator<Map>() {  
			  
	            @Override  
	            public int compare(Map o1,Map o2) {  
	            	if(o1.get("invited")==null){
	            		return -1;
	            	}
	            	
	            	if(o2.get("invited")==null){
	            		return 1;
	            	}
	                // 按照专家是否邀请降序排列  
	                if (Integer.parseInt(o1.get("invited")+"") > Integer.parseInt(o2.get("invited")+"") ) {  
	                    return -1;  
	                }  
	                if (Integer.parseInt(o1.get("invited")+"") == Integer.parseInt(o2.get("invited")+"")) {  
	                    return 0;  
	                }  
	                return 1;  
	            }  
	        });  
		 
		resultMap.put("list", list);
		return ResultUtil.success(resultMap);
	}

	/**
	 * 单独邀请专家并返回该问题已邀请专家
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@PutMapping("/inviteSpec")
	@Transactional
	public Result inviteSpec(@RequestBody Map params) {
		String issueid = params.get("issueid") + "";
		String specid = params.get("specid") + "";
		if (issueid.equals("") || specid.equals("")) {
			throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
		}
		List list = new ArrayList<>();
		Map one = new HashMap<>();
		one.put("specid", specid);
		one.put("issueid", issueid);
		one.put("is_assigned", "1");
		list.add(one);
		specialistService.insertIssueRel(list);
		//更新common_comment状态
//		Map param=new HashMap();
//		param.put("object_id", issueid);
//		commentReplyService.updateReplyStatus(param);
		return meetProfessorService.expert(Integer.parseInt(issueid));
	}

	/**
	 * 我的提问
	 * 
	 * @param user_id
	 *            用户编号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/my_issue")
	public Result myIssueList(String user_id) {
		return ResultUtil.success(specialistService.myIssues(user_id));
	}

	/**
	 * 提问详情
	 * 
	 * @param issue_id
	 *            问题编号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/issue/detail")
	public Result issueDetail(String issue_id) {
		return ResultUtil.success(specialistService.issueDetail(issue_id));
	}

	@MethodLog(remark = "新增,上传资源,资源管理")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/file")
	public Result uploadFile(@RequestBody Map param) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("upload_time", sdf.format(new Date().getTime()));
		param.put("status", "0");

		return ResultUtil.success(specialistService.uploadFile(param));
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/videos")
	public Result videoList(String spec_id, String title, String status, String start, String end) {
		Map param = new HashMap<>();
		param.put("spec_id", spec_id);
		param.put("title", title);
		param.put("status", status);
		param.put("start", start);
		param.put("end", end);
		return ResultUtil.success(specialistService.videoList(param));
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

	/**
	 * 更改审阅状态（标记已下载）
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@PutMapping("/is_downloaded")
	public Result is_downloaded(@RequestBody Map params) {
		String id = params.get("id") + "";
		return ResultUtil.success(specialistService.is_downloaded(id));
	}

}
