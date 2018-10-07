package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Article;
import com.bonc.medicine.entity.mall.Case;
import com.bonc.medicine.mapper.mall.MeetProfessorMapper;
import com.bonc.medicine.service.mall.MeetProfessorService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class MeetProfessorImpl implements MeetProfessorService {

	@Autowired
	private MeetProfessorMapper meetProfessorMapper;

	@Override
	public List<Map<String, Object>> queslist(String issue_status, String revisited) {

		Map map = new HashMap<>();
		map.put("issue_status", issue_status);
		map.put("revisited", revisited);
		return meetProfessorMapper.queslist(map);
	}

	@Override
	public List<Map<String, Object>> assistlist() {

		Map map = new HashMap<>();
		return meetProfessorMapper.assistlist(map);
	}

	@Override
	@Transactional
	public Result<Object> anli(Case anli) {
		Map map = new HashMap<>();
		Map map2 = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = format.format(date);
		anli.setPublish_time(time);
		map.put("issue_id", anli.getIssue_id());
		meetProfessorMapper.anli(anli);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1 = meetProfessorMapper.queryIssue(map);
		map2.put("notice_type", "1");
		map2.put("object_id", anli.getId());
		map2.put("notice_content", "您的提问《" + anli.getTitle() + "》被设置为案例");
		map2.put("notice_receiver", map1.get("issue_user_id"));
		meetProfessorMapper.addCaseNotice(map2);
		return ResultUtil.success(meetProfessorMapper.setAnli(map));
	}

	@Override
	public Result<Object> revisited(Integer id, String comment, String close) {

		Map map = new HashMap<>();
		map.put("id", id);
		map.put("comment", comment);
		map.put("close", close);
		return ResultUtil.success(meetProfessorMapper.revisited(map));
	}

	@Override
	public List<Map<String, Object>> untreated(Integer spec_id) {
		Map map = new HashMap<>();
		map.put("spec_id", spec_id);
		return meetProfessorMapper.untreated(map);
	}

	@Override
	public List<Map<String, Object>> solving(Integer spec_id) {
		Map map = new HashMap<>();
		map.put("spec_id", spec_id);
		return meetProfessorMapper.solving(map);
	}

	@Override
	public List<Map<String, Object>> solved(Integer spec_id) {
		Map map = new HashMap<>();
		map.put("spec_id", spec_id);
		return meetProfessorMapper.solved(map);
	}

	@Override
	public List<Map<String, Object>> issue_statistics_untreated(Integer spec_id) {
		Map map = new HashMap<>();
		map.put("spec_id", spec_id);
		return meetProfessorMapper.issue_statistics_untreated(map);
	}

	@Override
	public List<Map<String, Object>> issue_statistics_solving(Integer spec_id) {
		Map map = new HashMap<>();
		map.put("spec_id", spec_id);
		return meetProfessorMapper.issue_statistics_solving(map);
	}

	@Override
	public int setRevisit(Integer id, Integer follow_days, String revisit_url, String revisited_advice) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("follow_days", follow_days);
		map.put("revisit_url", revisit_url);
		map.put("revisited_advice", revisited_advice);
		return meetProfessorMapper.setRevisit(map);
	}

	@Override
	public int eddIssue(Integer id, String revisited_mark, String issue_status) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("revisited_mark", revisited_mark);
		map.put("issue_status", issue_status);
		return meetProfessorMapper.eddIssue(map);
	}

	@Override
	public Result<Object> expert(Integer id) {
		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(meetProfessorMapper.expert(map));
	}

	@Override
	public void deleteInvitation(Integer id) {
		Map map = new HashMap<>();
		map.put("id", id);
		ResultUtil.success(meetProfessorMapper.deleteInvitation(map));
	}

	@Override
	public void Invitation(Integer id, String expert_id) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("expert_id", expert_id);
		ResultUtil.success(meetProfessorMapper.Invitation(map));
	}

	@Override
	public Result<Object> end(Integer id, Integer score) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("score", score);
		return ResultUtil.success(meetProfessorMapper.end(map));
	}

	@Override
	public Result<Object> articlelist(Integer id) {
		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(meetProfessorMapper.articlelist(map));
	}

	@Override
	public Result<Object> fileManage(String key, String starttime, String endtime, String status) {
		Map map = new HashMap<>();
		map.put("key", key);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("status", status);
		return ResultUtil.success(meetProfessorMapper.fileManage(map));
	}

	@Override
	public void articleStatus(Integer id, String status) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("status", status);
		ResultUtil.success(meetProfessorMapper.articleStatus(map));
	}

	@Override
	public void aduitArticle(Integer id, String is_audit, String fail_opinion) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("is_audit", is_audit);
		map.put("fail_opinion", fail_opinion);
		ResultUtil.success(meetProfessorMapper.aduitArticle(map));
	}

	@Override
	public void addArticle(Article article) {
		ResultUtil.success(meetProfessorMapper.addArticle(article));
	}

	@Override
	public void updateArticle(Article article) {
		ResultUtil.success(meetProfessorMapper.updateArticle(article));
	}

	@Override
	public List<Map<String, Object>> getArticlelist(String key) {
		Map map = new HashMap<>();
		map.put("key", key);
		return meetProfessorMapper.getArticlelist(map);
	}

	@Override
	public Result<Object> queryArticleList() {

		return ResultUtil.success(meetProfessorMapper.queryArticleList());
	}
}
