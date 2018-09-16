package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.PriceService;
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
	 * 获取热词接口
	 */
	@GetMapping("/price/hotword")
	public Result<Object> hotword() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = priceService.hotword();
		Result result = new Result();
		result.setData(list);
		return result;
	}

	/*
	 * 增加热词接口
	 */
	@GetMapping("/price/add/hotword/{hotword}")
	public Result<Object> addHotword(@PathVariable String hotword) {
		priceService.addHotword(hotword);
		Result result = new Result();
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 市场价格
	 */
	@GetMapping("/price/market/{hotword}/{market}")
	public Result<Object> market(@PathVariable String hotword, @PathVariable String market) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = priceService.market(hotword, market);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 产地价格
	 */
	@GetMapping("/price/product/{hotword}/{product}")
	public Result<Object> product(@PathVariable String hotword, @PathVariable String product) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = priceService.product(hotword, product);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

	/*
	 * 历史价格走势type0：全部,1：一年,2：一月
	 */
	@GetMapping("/price/trend/{hotword}/{type}")
	public Result<Object> trend(@PathVariable String hotword, @PathVariable String type) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if ("0".equals(type.toString())) {
			String end_time = format.format(date);
			list = priceService.trend(hotword, "2017-01-01", end_time);
			Result result = new Result();
			result.setData(list);
			result.setCode(200);
			result.setMsg("成功");
			return result;
		} else if ("1".equals(type.toString())) {
			c.add(Calendar.YEAR, -1);
			Date y = c.getTime();
			String start_time = format.format(y);
			String end_time = format.format(date);
			list = priceService.trend(hotword, start_time, end_time);
			Result result = new Result();
			result.setData(list);
			result.setCode(200);
			result.setMsg("成功");
			return result;
		} else if ("2".equals(type.toString())) {
			c.add(Calendar.MONTH, -1);
			Date y = c.getTime();
			String start_time = format.format(y);
			String end_time = format.format(date);
			list = priceService.trend(hotword, start_time, end_time);
			Result result = new Result();
			result.setData(list);
			result.setCode(200);
			result.setMsg("成功");
			return result;
		}
		return null;
	}

	/*
	 * 价格明细表
	 */
	@GetMapping("/price/detail/{hotword}")
	public Result<Object> detail(@PathVariable String hotword) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = priceService.detail(hotword);
		Result result = new Result();
		result.setData(list);
		result.setCode(200);
		result.setMsg("成功");
		return result;
	}

}
