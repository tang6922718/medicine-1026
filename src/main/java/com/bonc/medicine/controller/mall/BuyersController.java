package com.bonc.medicine.controller.mall;

import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Purchase;
import com.bonc.medicine.service.mall.BuyersService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ResultUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyers")
public class BuyersController {
	
	@Autowired
	BuyersService buyersService;
	@Autowired
    private ViewNumberService viewNumberService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
    @PostMapping("/purchase")
	@MethodLog(remark = "新增,新增求购,供求")
    public Result<Object> releasePurchase(@RequestBody Purchase tempData){
		tempData.setState('1');
		tempData.setIs_aduit('0');
        return ResultUtil.success(buyersService.releasePurchase(tempData));
    }
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@DeleteMapping("/purchase")
	@MethodLog(remark = "删除,删除求购,供求")
	public Result<Object> deletePurchase(@RequestBody Map param) {
		List ids = (List)param.get("ids");
		return ResultUtil.success(buyersService.deletePurchase(ids));
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@PutMapping("/revokePurchase")
	@MethodLog(remark = "修改,下架求购,供求")
	public Result<Object> revokePurchase(@RequestBody Map param) {
		List ids = (List)param.get("ids");
		return ResultUtil.success(buyersService.revokePurchase(ids));
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/latest")
	public Result<Object> latestPurchaseList(Integer pageNum, Integer pageSize) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		long total = 0L;
		if (pageNum != null && pageSize != null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		list = buyersService.latestPurchaseList();
		if (pageNum != null && pageSize != null) {
			total =  list == null ? 0L : ((Page<Map<String,Object>>)list).getTotal();
		}
		return ResultUtil.successTotal(list, total);
	} 
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/manage")
	public Result<Object> purchaseList(String name, String cat, String is_audit,String goods_cat_code) {
		Map params = new HashMap<>();
		params.put("goods_name", name);
		params.put("cat_code", cat);
		params.put("is_aduit", is_audit);
		params.put("goods_cat_code", goods_cat_code);
		return ResultUtil.success(buyersService.purchaseList(params));
	} 
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/detail")
	public Result<Object> purchasepDetail(String id) {
		
		Map<String, String> map = new HashMap<>();
        map.put("objectType", "9");
        map.put("objectId", id);
        map.put("viewNumber", "1");
        viewNumberService.addOrUpdateViewNumberCord(map);
		return ResultUtil.success(buyersService.purchasepDetail(id));
	} 
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/mine")
	public Result<Object> myPurchasepList(String user_id) {
		return ResultUtil.success(buyersService.myPurchasepList(user_id));
	} 
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/catalog")
	public Result<Object> purchasepCatList(String cat_code, String goods_cat_code, String goods_name) {
		return ResultUtil.success(buyersService.purchasepCatList(cat_code, goods_cat_code, goods_name));
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/name")
	public Result<Object> purchasepNameList(String name) {
		return ResultUtil.success(buyersService.purchasepNameList(name));
	} 
	
}
