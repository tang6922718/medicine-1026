package com.bonc.medicine.service.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Article;
import com.bonc.medicine.entity.mall.Case;

import java.util.List;
import java.util.Map;

public interface MeetProfessorService {

	public List<Map<String, Object>> queslist(String issue_status, String revisited);

	public List<Map<String, Object>> assistlist();

	public Result<Object> revisited(Integer id, String comment, String close);

	public Result<Object> setAnli(Integer issue_id);
	
	public Result<Object> anli(Case anli);

	public List<Map<String, Object>> untreated(Integer spec_id);

	public List<Map<String, Object>> solved(Integer spec_id);

	public List<Map<String, Object>> solving(Integer spec_id);

	public List<Map<String, Object>> issue_statistics_untreated(Integer spec_id);

	public List<Map<String, Object>> issue_statistics_solving(Integer spec_id);

	public Result<Object> expert(Integer id);

	public int setRevisit(Integer id, Integer follow_days, String revisit_url,
			String revisited_advice);
	
	public int eddIssue(Integer id, String revisited_mark, String issue_status);

	public void deleteInvitation(Integer id);

	public void Invitation(Integer id, String expert);

	public Result<Object> end(Integer id, Integer score);

	public Result<Object> articlelist(Integer id);

	public Result<Object> fileManage(String key, String starttime, String endtime, String status);

	public void articleStatus(Integer id, String status);

	public void aduitArticle(Integer id, String is_audit, String fail_opinion);

	public void addArticle(Article article);
	
	public Result<Object> queryArticleList();

	public void updateArticle(Article article);

	public List<Map<String, Object>> getArticlelist(String key);

}
