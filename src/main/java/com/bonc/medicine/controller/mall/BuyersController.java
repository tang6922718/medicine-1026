package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Purchase;
import com.bonc.medicine.service.mall.BuyersService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/buyers")
public class BuyersController {
	
	@Autowired
	BuyersService buyersService;
	
	@SuppressWarnings("unchecked")
	@ResponseBody
    @PostMapping("/purchase")
    public Result<Object> releasePurchase(@RequestBody Purchase tempData){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publish_time = sdf.format(new Date());
		tempData.setPublish_time(publish_time);
		tempData.setState('1');
		tempData.setIs_aduit('0');
        return ResultUtil.success(buyersService.releasePurchase(tempData));
    }
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/deletePurchase")
	public Result<Object> deletePurchase(String id) {
		return ResultUtil.success(buyersService.deletePurchase(id));
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/revokePurchase")
	public Result<Object> revokePurchase(String id) {
		return ResultUtil.success(buyersService.revokePurchase(id));
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/latest")
	public Result<Object> latestPurchaseList(int limit) {
		return ResultUtil.success(buyersService.latestPurchaseList(limit));
	} 
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/detail")
	public Result<Object> purchasepDetail(String id) {
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
	public Result<Object> purchasepCatList(String cat_code) {
		return ResultUtil.success(buyersService.purchasepCatList(cat_code));
	} 
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/purchase/name")
	public Result<Object> purchasepNameList(String name) {
		return ResultUtil.success(buyersService.purchasepNameList(name));
	} 
	
}
