package com.bonc.medicine.service.mall;


import com.bonc.medicine.entity.mall.Purchase;

import java.util.List;
import java.util.Map;

public interface BuyersService {
	public int releasePurchase(Purchase tempData);
	public List<Map<String,Object>> latestPurchaseList();
	public List<Map<String,Object>> purchaseList(Map params);
	public Map<String,Object> purchasepDetail(String id);
	public int deletePurchase(List ids);
	public int revokePurchase(List ids);
	public List<Map<String,Object>> myPurchasepList(String user_id);
	public List<Map<String,Object>> purchasepCatList(String cat_code,String goods_cat_code);
	public List<Map<String,Object>> purchasepNameList(String goods_name);
}
