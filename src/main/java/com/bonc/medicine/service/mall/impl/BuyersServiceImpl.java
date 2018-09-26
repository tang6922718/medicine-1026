package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.mall.Purchase;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.mall.BuyersMapper;
import com.bonc.medicine.service.mall.BuyersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyersServiceImpl implements BuyersService {
	
	@Autowired
	BuyersMapper buyersMapper;
	
	@Override
	public int releasePurchase(Purchase tempData) {
		return buyersMapper.insertPurchase(tempData);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> latestPurchaseList(int limit) {
		Map params = new HashMap();
		params.put("limit", limit);
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
	public List<Map<String, Object>> purchasepCatList(String cat_code,String goods_cat_code) {
		Map params = new HashMap();
		params.put("cat_code", cat_code);
		params.put("goods_cat_code", goods_cat_code);
		params.put("is_aduit", 1);
		return buyersMapper.latestPurchaseList(params);
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
	public int deletePurchase(String id) {
		return buyersMapper.deletePurchase(id);
	}
	
	@Override
	public int revokePurchase(String[] ids) {
		if (ids.length < 1) {
			throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
		}
		return buyersMapper.revokePurchase(ids);
	}

	@Override
	public List<Map<String, Object>> purchaseList(Map params) {
		return buyersMapper.latestPurchaseList(params);
	}
	
}
