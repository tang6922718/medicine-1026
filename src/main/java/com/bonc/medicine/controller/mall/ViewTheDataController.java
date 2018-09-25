package com.bonc.medicine.controller.mall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	 * 本周人气排行
	 */
	@GetMapping("/viewTheData/rank/interaction")
	public Result<Object> rankInteraction() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 基础用户信息
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 关注
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();// 收藏
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();// 提问数
		List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();// 返回排行前10的数据
		list = viewTheDataService.rankInteraction();
		list1 = viewTheDataService.follow();
		list2 = viewTheDataService.collection();
		list3 = viewTheDataService.issue();
		if (list != null && list.size() != 0) {
			if (list1 != null && list1.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list1.size(); j++) {
						if (list.get(i).get("user_id").toString().equals(list1.get(j).get("object_id").toString())) {
							list.get(i).put("follow", list1.get(j).get("follow"));
						}
					}
				}
			}
			if (list2 != null && list2.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list2.size(); j++) {
						if (list.get(i).get("user_id").toString()
								.equals(list2.get(j).get("collect_object_id").toString())) {
							list.get(i).put("collection", list2.get(j).get("collection"));
						}
					}
				}
			}
			if (list3 != null && list3.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list3.size(); j++) {
						if (list.get(i).get("user_id").toString().equals(list3.get(j).get("specid").toString())) {
							list.get(i).put("issue", list3.get(j).get("issue"));
						}
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("total",
						Integer.parseInt(list.get(i).get("follow").toString())
								+ Integer.parseInt(list.get(i).get("collection").toString())
								+ Integer.parseInt(list.get(i).get("issue").toString()));
			}
		}
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Integer name1 = Integer.valueOf(o1.get("total").toString());
				Integer name2 = Integer.valueOf(o2.get("total").toString());
				return name2.compareTo(name1);
			}
		});
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("id", i + 1);
			if (i == 9) {// 返回前10行
				Result result = new Result();
				list4.add(list.get(0));
				list4.add(list.get(1));
				list4.add(list.get(2));
				list4.add(list.get(3));
				list4.add(list.get(4));
				list4.add(list.get(5));
				list4.add(list.get(6));
				list4.add(list.get(7));
				list4.add(list.get(8));
				list4.add(list.get(9));
				result.setData(list4);
				result.setCode(200);
				result.setMsg("成功");
				return result;
			}
		}
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 自己人气数据
	 */
	@GetMapping("/viewTheData/my/interaction")
	public Result<Object> MyInteraction(String user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 基础用户信息
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();// 关注
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();// 收藏
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();// 提问数
		List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();// 返回排行前10的数据
		list = viewTheDataService.rankInteraction();
		list1 = viewTheDataService.follow();
		list2 = viewTheDataService.collection();
		list3 = viewTheDataService.issue();
		if (list != null && list.size() != 0) {
			if (list1 != null && list1.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list1.size(); j++) {
						if (list.get(i).get("user_id").toString().equals(list1.get(j).get("object_id").toString())) {
							list.get(i).put("follow", list1.get(j).get("follow"));
						}
					}
				}
			}
			if (list2 != null && list2.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list2.size(); j++) {
						if (list.get(i).get("user_id").toString()
								.equals(list2.get(j).get("collect_object_id").toString())) {
							list.get(i).put("collection", list2.get(j).get("collection"));
						}
					}
				}
			}
			if (list3 != null && list3.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list3.size(); j++) {
						if (list.get(i).get("user_id").toString().equals(list3.get(j).get("specid").toString())) {
							list.get(i).put("issue", list3.get(j).get("issue"));
						}
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("total",
						Integer.parseInt(list.get(i).get("follow").toString())
								+ Integer.parseInt(list.get(i).get("collection").toString())
								+ Integer.parseInt(list.get(i).get("issue").toString()));
			}
		}
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Integer name1 = Integer.valueOf(o1.get("total").toString());
				Integer name2 = Integer.valueOf(o2.get("total").toString());
				return name2.compareTo(name1);
			}
		});
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("id", i + 1);
			if (list.get(i).get("user_id").toString().equals(user_id)) {
				Result result = new Result();
				result.setData(list.get(i));
				result.setCode(200);
				result.setMsg("成功");
				return result;
			}
		}
		throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
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
