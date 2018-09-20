package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.PriceService;
import com.bonc.medicine.utils.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PriceController {

	@Autowired
	private PriceService priceService;

	/*
	 * 获取品种
	 */
	@GetMapping("/price/get/arieties")
	public Result<Object> getArieties() {
		return ResultUtil.success(priceService.getArieties());
	}

	/*
	 * 获取市场
	 */
	@GetMapping("/price/get/market")
	public Result<Object> getMarket() {
		return ResultUtil.success(priceService.getMarket());
	}

	/*
	 * 获取规格
	 */
	@GetMapping("/price/get/specifaction")
	public Result<Object> getSpecifaction() {
		return ResultUtil.success(priceService.getSpecifaction());
	}

	/*
	 * 获取产地
	 */
	@GetMapping("/price/get/product")
	public Result<Object> getProduct() {
		return ResultUtil.success(priceService.getProduct());
	}

	/*
	 * 市场价格
	 */
	@GetMapping("/price/market")
	public Result<Object> market(String hotword, String market) {
		return ResultUtil.success(priceService.market(hotword, market));
	}

	/*
	 * 价格走势
	 */
	@GetMapping("/price/trend")
	public Result<Object> trend(String hotword, String market, String product, String specifaction, String start_time,
			String end_time) {
		return ResultUtil.success(priceService.trend(hotword, market, product, specifaction, start_time, end_time));
	}

	/*
	 * 价格明细表
	 */
	@GetMapping("/price/detail")
	public Result<Object> detail(String hotword, String market, String product, String specifaction, String start_time,
			String end_time) {
		return ResultUtil.success(priceService.detail(hotword, market, product, specifaction, start_time, end_time));
	}

	/*
	 * 今日价格
	 */
	@GetMapping("/price/todayPrice")
	public Result<Object> detail(String hotword, String market, String product, String specifaction) {
		return ResultUtil.success(priceService.todayPrice(hotword, market, product, specifaction));
	}

	/*
	 * 价格管理列表查询
	 */
	@GetMapping("/price/pricelist")
	public Result<Object> pricelist(String hotword, String priceType, String priceState, String start_time,
			String end_time) {
		return ResultUtil.success(priceService.pricelist(hotword, priceType, priceState, start_time, end_time));
	}

	/*
	 * 价格启用停用，1启用0停用
	 */
	@GetMapping("/price/priceState")
	public Result<Object> priceState(Integer id, String state) {
		return ResultUtil.success(priceService.priceState(id, state));
	}

}
