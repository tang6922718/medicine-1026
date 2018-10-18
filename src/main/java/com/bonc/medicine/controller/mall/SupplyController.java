package com.bonc.medicine.controller.mall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Supply;
import com.bonc.medicine.service.mall.SupplyService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ResultUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
@RequestMapping("/seller")
public class SupplyController {

	@Autowired
	private SupplyService supplyService;
	@Autowired
	private ViewNumberService viewNumberService;

	/*
	 * *
	 * 
	 * @Description 发布供应接口
	 * 
	 * @Date 19:15 2018/8/22
	 * 
	 * @Param [tempData]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@MethodLog(remark = "新增,发布供应,供求")
	@PostMapping("/supply")
	public Result<Object> releaseSupply(@RequestBody Supply tempData) {// 这里用Supply实体类来接受参数
		return supplyService.releaseSupply(tempData);
	}

	/*
	 * *
	 * 
	 * @Description 推荐查询
	 * 
	 * @Date 19:16 2018/8/22
	 * 
	 * @Param [site] 0 全部 1 app精品推荐 2 门户源头好货推荐 3 门户精品商家
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@GetMapping("/recommend/{site}")
	public Result<Object> getRecommend(@PathVariable String site) {
		return supplyService.getRecommend(site);
	}

	/*
	 * *
	 * 
	 * @Description 供应详情查询 1
	 * 
	 * @Date 19:42 2018/8/22
	 * 
	 * @Param [id]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@GetMapping("/details/{id}")
	public Result<Object> getDetails(@PathVariable String id) {

		Map<String, String> map = new HashMap<>();
		map.put("objectType", "3");
		map.put("objectId", id);
		map.put("viewNumber", "1");
		viewNumberService.addOrUpdateViewNumberCord(map);

		return supplyService.getDetails(id);
	}

	/*
	 * *
	 * 
	 * @Description 自己的供应列表查询 1
	 * 
	 * @Date 11:54 2018/8/23
	 * 
	 * @Param [SellerID]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@GetMapping("/ownsupply/{sellerID}")
	public Result<Object> getOwnSupply(@PathVariable int sellerID) {
		return supplyService.getOwnSupply(sellerID);
	}

	/*
	 * *
	 * 
	 * @Description 自己其它供应列表查询 1
	 * 
	 * @Date 16:39 2018/8/23
	 * 
	 * @Param [SellerID, ID, NUM]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@GetMapping("/othersupply/{sellerID}/{ID}/{NUM}")
	public Result<Object> getOtherSupply(@PathVariable int sellerID, @PathVariable int ID, @PathVariable int NUM) {
		return supplyService.getOtherSupply(sellerID, ID, NUM);
	}

	/*
	 * *
	 * 
	 * @Description 最新供应列表查询
	 * 
	 * @Date 19:17 2018/8/22
	 * 
	 * @Param [pageNumber, pageSize]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@PostMapping("/lastest")
	public Result<Object> getLastest(@RequestBody JSONObject json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempData = new HashMap<String, Object>();
		tempData.putAll(json);
		Integer pageNum = null;
		Integer pageSize = null;
		if (tempData.containsKey("pageNum") && tempData.containsKey("pageSize")) {
			pageNum = Integer.parseInt(tempData.get("pageNum").toString());
			pageSize = Integer.parseInt(tempData.get("pageSize").toString());
		}
		long total = 0L;
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		list = supplyService.getLastest(json);
		if (pageNum != null && pageSize != null) {
			total = list == null ? 0L : ((Page<Map<String, Object>>) list).getTotal();
		}
		return ResultUtil.successTotal(list, total);
	}

	/*
	 * *
	 * 
	 * @Description 下架供应查询 1
	 * 
	 * @Date 19:05 2018/8/23
	 * 
	 * @Param [sellerID]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@GetMapping("/xiajia/{sellerID}")
	public Result<Object> getXiaJia(@PathVariable int sellerID) {
		return supplyService.getXiaJia(sellerID);
	}

	/*
	 * *
	 * 
	 * @Description 删除下架 1
	 * 
	 * @Date 20:05 2018/8/23
	 * 
	 * @Param [ID]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@MethodLog(remark = "删除,供应下架操作,供求")
	@DeleteMapping("/xiajia/{ID}")
	public Result<Object> deleteXiaJia(@PathVariable int ID) {
		return supplyService.deleteXiaJia(ID);
	}

	/*
	 * *
	 * 
	 * @Description 查询审核的供应 1 需求暂时不清楚
	 * 
	 * @Date 10:09 2018/8/24
	 * 
	 * @Param [sellerID]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@GetMapping("/checkedsupply/{sellerID}")
	public Result<Object> getUncheckedSupply(@PathVariable int sellerID) {
		return supplyService.getCheckedSupply(sellerID);
	}

	/*
	 * *
	 * 
	 * @Description 删除未审核的供应 1
	 * 
	 * @Date 11:59 2018/8/24
	 * 
	 * @Param [ID]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@MethodLog(remark = "删除,删除审核未通过的供应,供求")
	@DeleteMapping("/unchecked/supply/{ID}")
	public Result<Object> deleteUncheckedSupply(@PathVariable int ID) {
		return supplyService.deleteUncheckedSupply(ID);
	}

	/*
	 * *
	 * 
	 * @Description 查询给我的留言 未实现
	 * 
	 * @Date 14:34 2018/8/24
	 * 
	 * @Param [sellerID]
	 * 
	 * @return com.bonc.mall.entity.Result<java.lang.Object>
	 */
	@GetMapping("/markstome/{sellerID}")
	public Result<Object> getMarks(@PathVariable int sellerID) {
		return supplyService.getMarks(sellerID);
	}

	/*
	 * 查询我的供应-审核中列表
	 */
	@GetMapping("/inReview/{sellerID}")
	public Result<Object> inReview(@PathVariable int sellerID) {
		return supplyService.inReview(sellerID);
	}

	/*
	 * 重新发布
	 */
	@PutMapping("/againSupply")
	public Result<Object> againSupply(@RequestBody Supply supply) {
		return supplyService.againSupply(supply);
	}

	/*
	 * 通过id查找某一个商品
	 */
	@GetMapping("/querySupplyById/{id}")
	public Result<Object> querySupplyById(@PathVariable int id) {
		return supplyService.querySupplyById(id);
	}

	/*
	 * 我的报价
	 */
	@GetMapping("/myOffer/{id}")
	public Result<Object> myOffer(@PathVariable int id) {
		return supplyService.myOffer(id);
	}

	/*
	 * 查看报价详情
	 */
	@GetMapping("/queryOffer/{id}")
	public Result<Object> queryOffer(@PathVariable int id) {
		return supplyService.queryOffer(id);
	}

}
