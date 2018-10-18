package com.bonc.medicine.controller.mall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.mall.ViewTheDataService;

@RestController
public class ViewTheDataController {

	@Autowired
	private ViewTheDataService viewTheDataService;

	/*
	 * 最近7日互动总数折线图
	 */
	@GetMapping("/viewTheData/interaction")
	public Result<Object> interaction(Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = viewTheDataService.interaction(user_id);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 本周互动总数
	 */
	@GetMapping("/viewTheData/thisWeek/interaction")
	public Result<Object> thisWeekInteraction(Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = viewTheDataService.thisWeekInteraction(user_id);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 上周互动总数
	 */
	@GetMapping("/viewTheData/history/interaction")
	public Result<Object> historyInteraction(Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = viewTheDataService.historyInteraction(user_id);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 本周人气排行如果传入user_id接口变为自己人气数据
	 */
	@GetMapping("/viewTheData/rank/interaction")
	public Result<Object> rankInteraction(Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 基础用户信息
		list = viewTheDataService.rankInteraction(user_id);
		if (user_id == null) {
			Result result = new Result();
			result.setData(list);
			result.setCode(200);
			result.setMsg("成功");
			return result;
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).get("user_id").toString().equals(""+user_id)) {
					list.get(i).put("id", i + 1);
					Result result = new Result();
					result.setData(list.get(i));
					result.setCode(200);
					result.setMsg("成功");
					return result;
				}
			}
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
	}

	/*
	 * 查看数据-我的资源
	 */
	@GetMapping("/viewTheData/myResources")
	public Result<Object> myResources(Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = viewTheDataService.myResources(user_id);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 问题统计
	 */
	@GetMapping("/viewTheData/problemStatistics")
	public Result<Object> problemStatistics(Integer user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = viewTheDataService.problemStatistics(user_id);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

}
