package com.bonc.medicine.mapper.mall;


import com.bonc.medicine.entity.mall.Article;
import com.bonc.medicine.entity.mall.Case;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface MeetProfessorMapper {

	public List<Map<String, Object>> queslist(Map map);

	public List<Map<String, Object>> assistlist(Map map);

	public int revisited(Map map);

	public int setAnli(Map map);
	
	public int anli(Case anli);

	public List<Map<String, Object>> untreated(Map map);
	
	public List<Map<String, Object>> solved(Map map);
	
	public List<Map<String, Object>> solving(Map map);
	
	public List<Map<String, Object>> issue_statistics_untreated(Map map);
	
	public List<Map<String, Object>> issue_statistics_solving(Map map);
	
	public int setRevisit(Map map);
	
	public int eddIssue(Map map);

	public List<Map<String, Object>> expert(Map map);

	public int deleteInvitation(Map map);

	public int Invitation(Map map);

	public int end(Map map);

	public List<Map<String, Object>> articlelist(Map map);

	public List<Map<String, Object>> fileManage(Map map);

	public int articleStatus(Map map);
	
	public int aduitArticle(Map map);
	
	public int addArticle(Article article);
	
	public int updateArticle(Article article);
	
	public List<Map<String, Object>> getArticlelist(Map map);
	
	public List<Map> queryArticleList();

}
