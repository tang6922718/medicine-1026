package com.bonc.medicine.mapper.mall;

import com.bonc.medicine.entity.mall.Purchase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface BuyersMapper {
	public int insertPurchase(Purchase tempData);
	
	public List<Map<String,Object>> latestPurchaseList(Map params); // 最新求供列表,我的求购列表
	public List<Map<String,Object>> purchasepCatList(Map params);
	public List<Map<String,Object>> purchasepDetail(String id); // 求供详情
	public int deletePurchase(List ids); // 删除求购
	public int revokePurchase(List ids); // 撤销求购
}
