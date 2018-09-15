package com.bonc.medicine.mapper.mall;

import com.bonc.medicine.entity.mall.Issue;
import com.bonc.medicine.entity.mall.Specialist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SpecialistMapper {
	public int insertSpecialist(Specialist specialist);
	public int insertSubSpec(Map param);
	public int delSubSpec(String spec_id);
	public int insertCatSpec(Map param);
	public int delCatSpec(String spec_id);
	
	
	public int updateSpecialist(Specialist specialist);
	public List<Map<String, Object>> specialList(Map param);
	public List<Map<String, Object>> specialIsFollow(Map param);
	public List<Map<String, Object>> specDetail(Map param);
	public List<Map> articleList(String spec_id);
	public List<Map> articleDetail(String id);
	public List<Map> caseList(String spec_id);
	public List<Map> caseDetail(String id);
	public List<Map> subjectList();
	public List<Map> catalogList(Map param);
	public List<Map> videoList(String spec_id);
	
	public int insertIssue(Issue issue);
	public List<Map> myIssues(String user_id);
	public List<Map> issueDetail(String issue_id);
	public int uploadFile(Map param);
	public List<Map> uploadRecord(Integer issue_id);
}
