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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class MeetProfessorImpl implements MeetProfessorService {

	@Autowired
	private MeetProfessorMapper meetProfessorMapper;

	@Override
	public List<Map<String, Object>> queslist(String msg, String revisited) {

		Map map = new HashMap<>();
		map.put("msg", msg);
		map.put("revisited", revisited);
		return meetProfessorMapper.queslist(map);
	}

	@Override
	public List<Map<String, Object>> assistlist() {

		Map map = new HashMap<>();
		return meetProfessorMapper.assistlist(map);
	}

	@Override
	public Result<Object> anli(Case anli) {

		return ResultUtil.success(meetProfessorMapper.anli(anli));
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
}
