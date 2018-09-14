package com.bonc.medicine.service.mall;


import com.bonc.medicine.entity.mall.Issue;
import com.bonc.medicine.entity.mall.Specialist;

import java.util.List;
import java.util.Map;

public interface SpecialistService {
	public int craeteCharactor(Specialist specialist);
	public int insertSubSpec(Map param);
	public int delSubSpec(String spec_id);
	public int insertCatSpec(Map param);
	public int delCatSpec(String spec_id);
	
	public int updateInfo(Specialist specialist);
	public List<Map> catalogList();
	public List<Map> subjectList();
	public List<Map<String, Object>> specialList(Map param);
	public List<Map<String, Object>> specialIsFollow(Map param);
	public List<Map<String, Object>> specDetail(Map param);
	public List<Map> articleList(String spec_id);
	public List<Map> caseList(String spec_id);
	public Map articleDetail(String id);
	public Map caseDetail(String id);
	public List<Map> videoList(String spec_id);
	
	public int releaseIssue(Issue issue);
	public List<Map> myIssues(String user_id);
	public Map issueDetail(String issue_id);
	public int uploadFile(Map param);
	public List<Map> uploadRecord(Integer user_id);
}
