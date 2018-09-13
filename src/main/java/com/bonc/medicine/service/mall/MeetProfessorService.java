package com.bonc.medicine.service.mall;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Article;
import com.bonc.medicine.entity.mall.Case;

import java.util.List;
import java.util.Map;

public interface MeetProfessorService {

	public List<Map<String, Object>> queslist(String msg, String revisited);

	public List<Map<String, Object>> assistlist();

	public Result<Object> revisited(Integer id, String comment, String close);

	public Result<Object> anli(Case anli);

	public List<Map<String, Object>> untreated(Integer spec_id);

	public Result<Object> expert(Integer id);

	public void deleteInvitation(Integer id);

	public void Invitation(Integer id, String expert);

	public Result<Object> end(Integer id, Integer score);

	public Result<Object> articlelist(Integer id);

	public Result<Object> fileManage(String key, String starttime, String endtime, String status);

	public void articleStatus(Integer id, String status);

	public void aduitArticle(Integer id, String is_audit, String fail_opinion);

	public void addArticle(Article article);
	
	public void updateArticle(Article article);

}
