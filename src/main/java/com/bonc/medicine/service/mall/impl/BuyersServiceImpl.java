package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.mall.Purchase;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.mall.BuyersMapper;
import com.bonc.medicine.service.mall.BuyersService;
import com.bonc.medicine.service.thumb.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyersServiceImpl implements BuyersService {
	
	@Autowired
	BuyersMapper buyersMapper;

	@Autowired
	IntegralService integralService;
	
	@Override
	public int releasePurchase(Purchase tempData) {

		Map<String, String> ppparamMap = new HashMap<>();
		//userId;actionCode
		ppparamMap.put("userId", tempData.getUser_id() + "");
		ppparamMap.put("actionCode", "RELEASE_PURCHASE");
		try{

			integralService.addIntegralHistory(ppparamMap);
		}catch (Exception e){
			System.out.println("ERROR ：发布求购操作中---增加积分异常");
		}

		return buyersMapper.insertPurchase(tempData);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> latestPurchaseList() {
		Map params = new HashMap();
		params.put("is_aduit", 1);
		return buyersMapper.latestPurchaseList(params);
	}
	
	

	@Override
	public Map<String,Object> purchasepDetail(String id) {
		List<Map<String, Object>> result = buyersMapper.purchasepDetail(id);
		if (result.size() != 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
		return result.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> myPurchasepList(String user_id) {
		Map params = new HashMap();
		params.put("user_id", user_id);
		return buyersMapper.latestPurchaseList(params);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> purchasepCatList(String cat_code,String goods_cat_code,String goods_name) {
		Map params = new HashMap();
		params.put("cat_code", cat_code);
		params.put("goods_cat_code", goods_cat_code);
		params.put("goods_name", goods_name);
		return buyersMapper.purchasepCatList(params);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> purchasepNameList(String goods_name) {
		Map params = new HashMap();
		params.put("goods_name", goods_name);
		params.put("is_aduit", 1);
		return buyersMapper.latestPurchaseList(params);
	}

	@Override
	public int deletePurchase(List ids) {
		if (ids.size() < 1) {
			throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
		}
		return buyersMapper.deletePurchase(ids);
	}
	
	@Override
	public int revokePurchase(List ids) {
		if (ids.size() < 1) {
			throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
		}
		return buyersMapper.revokePurchase(ids);
	}

	@Override
	public List<Map<String, Object>> purchaseList(Map params) {
		return buyersMapper.latestPurchaseList(params);
	}
	
}
