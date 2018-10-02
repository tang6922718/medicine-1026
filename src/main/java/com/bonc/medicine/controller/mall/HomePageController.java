package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.HomePageService;
import com.bonc.medicine.utils.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class HomePageController {

	@Autowired
	private HomePageService homePageService;

	/*
	 * 代办通知
	 */
	@GetMapping("/homePage/notice")
	public Result<Object> notice() {
		return ResultUtil.success(homePageService.notice());
	}
	
	/*
	 * 今日更新
	 */
	@GetMapping("/homePage/today")
	public Result<Object> today() {
		return ResultUtil.success(homePageService.today());
	}

}
