package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.PriceService;
import com.bonc.medicine.utils.ResultUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
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
	public Result<Object> getArieties(String hotword) {
		return ResultUtil.success(priceService.getArieties(hotword));
	}

	/*
	 * 获取市场
	 */
	@GetMapping("/price/get/market")
	public Result<Object> getMarket(String hotword) {
		return ResultUtil.success(priceService.getMarket(hotword));
	}

	/*
	 * 获取规格
	 */
	@GetMapping("/price/get/specifaction")
	public Result<Object> getSpecifaction(String hotword) {
		return ResultUtil.success(priceService.getSpecifaction(hotword));
	}

	/*
	 * 获取产地
	 */
	@GetMapping("/price/get/product")
	public Result<Object> getProduct(String hotword) {
		return ResultUtil.success(priceService.getProduct(hotword));
	}

	/*
	 * 市场价格
	 */
	@GetMapping("/price/market")
	public Result<Object> market(String hotword, String market) {
		return ResultUtil.success(priceService.market(hotword, market));
	}

	/*
	 * 首页市场价格显示当天的最新6条
	 */
	@GetMapping("/price/homeMarket")
	public Result<Object> homeMarket() {
		return ResultUtil.success(priceService.homeMarket());
	}

	/*
	 * 价格走势
	 */
	@GetMapping("/price/trend")
	public Result<Object> trend(String hotword, String market, String product, String specifaction, String start_time,
			String end_time) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		list = priceService.trend(hotword, market, product, specifaction, start_time, end_time);
		String[] name = new String[list.size()];
		String[] value = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			name[i] = list.get(i).get("price_date").toString();
			value[i] = decimalFormat.format(Float.parseFloat(list.get(i).get("price").toString()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("value", value);
		list1.add(map);
		return ResultUtil.success(list1);
	}

	/*
	 * 价格明细表
	 */
	@GetMapping("/price/detail")
	public Result<Object> detail(String hotword, String market, String product, String specifaction, String start_time,
			String end_time) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		list = priceService.detail(hotword, market, product, specifaction, start_time, end_time);
		String[] name = new String[list.size()];
		String[] value = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			name[i] = list.get(i).get("price_date").toString();
			value[i] = decimalFormat.format(Float.parseFloat(list.get(i).get("price").toString()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("value", value);
		list1.add(map);
		return ResultUtil.success(list1);
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
			String end_time, Integer pageNum, Integer pageSize) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		long total = 0L;
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		list = priceService.pricelist(hotword, priceType, priceState, start_time, end_time);
		if (pageNum != null && pageSize != null) {
			total =  list == null ? 0L : ((Page<Map<String,Object>>)list).getTotal();
		}
		return ResultUtil.successTotal(list, total);
	}

	/*
	 * 价格启用停用，1启用0停用
	 */
	@GetMapping("/price/priceState")
	public Result<Object> priceState(Integer id, String state) {
		return ResultUtil.success(priceService.priceState(id, state));
	}

}
