package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.mall.Issue;
import com.bonc.medicine.entity.mall.Specialist;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.mall.SpecialistMapper;
import com.bonc.medicine.service.mall.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpecialistServiceImpl implements SpecialistService {
	@Autowired
	SpecialistMapper specialistMapper;
	
	@Override
	public int craeteCharactor(Specialist specialist) {
		return specialistMapper.insertSpecialist(specialist);
	}

	@Override
	public int updateInfo(Specialist specialist) {
		return specialistMapper.updateSpecialist(specialist);
	}

	@Override
	public List<Map> catalogList(Map param) {
		return specialistMapper.catalogList(param);
	}

	@Override
	public List<Map> subjectList() {
		return specialistMapper.subjectList();
	}

	@Override
	public List<Map<String, Object>> specialList(Map param) {
		return specialistMapper.specialList(param);
	}
	
	@Override
	public List<Map<String, Object>> specialIsFollow(Map param) {
		return specialistMapper.specialIsFollow(param);
	}

	@Override
	public List<Map<String, Object>> specDetail(Map param) {
		return specialistMapper.specDetail(param);
	}
	
	@Override
	public List<Map<String, Object>> sub(Map param) {
		return specialistMapper.sub(param);
	}
	
	@Override
	public List<Map<String, Object>> cat(Map param) {
		return specialistMapper.cat(param);
	}

	@Override
	public List<Map> articleList(Map param) {
		return specialistMapper.articleList(param);
	}

	@Override
	public Map articleDetail(String id) {
		List<Map> result = specialistMapper.articleDetail(id);
		if (result.size() != 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
		return result.get(0);
	}

	@Override
	public List<Map> caseList(String spec_id) {
		return specialistMapper.caseList(spec_id);
	}

	@Override
	public Map caseDetail(String id) {
		List<Map> result = specialistMapper.caseDetail(id);
		if (result.size() != 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
		return result.get(0);
	}

	@Override
	public int releaseIssue(Issue issue) {
		return specialistMapper.insertIssue(issue);
	}

	@Override
	public List<Map> myIssues(String user_id) {
		return specialistMapper.myIssues(user_id);
	}

	@Override
	public Map issueDetail(String issue_id) {
		List<Map> result = specialistMapper.issueDetail(issue_id);
		if (result.size() != 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
		return result.get(0);
	}

	@Override
	public int uploadFile(Map param) {
		return specialistMapper.uploadFile(param);
	}

	@Override
	public List<Map> videoList(Map param) {
		return specialistMapper.videoList(param);
	}

	@Override
	public int insertSubSpec(Map param) {
		return specialistMapper.insertSubSpec(param);
	}

	@Override
	public int insertCatSpec(Map param) {
		return specialistMapper.insertCatSpec(param);
	}

	@Override
	public int delSubSpec(String spec_id) {
		return specialistMapper.delSubSpec(spec_id);
	}

	@Override
	public int delCatSpec(String spec_id) {
		return specialistMapper.delCatSpec(spec_id);
	}
	
	@Override
	public List<Map> uploadRecord(Map param) {
		return specialistMapper.uploadRecord(param);
	}

	@Override
	public int revokeArt(String id) {
		return specialistMapper.revokeArt(id);
	}

	@Override
	public int insertIssueRel(List param) {
		return specialistMapper.insertIssueRel(param);
	}

	@Override
	public int is_downloaded(String id) {
		return specialistMapper.is_downloaded(id);
	}

}
