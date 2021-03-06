package com.bonc.medicine.service.mall;

import java.util.List;
import java.util.Map;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Marks;
import com.bonc.medicine.entity.mall.Recommend;
import com.bonc.medicine.entity.mall.Reply;

public interface GongQiuSystemService {

	public Result<Object> auditSSupply(Integer supplyId, String result, String comment);

	public Result<Object> auditFSupply(Integer supplyId, String result, String comment);

	public Result<Object> auditSPurchase(Integer purchaseId, String result, String comment);

	public Result<Object> auditFPurchase(Integer purchaseId, String result, String comment);

	public Result<Object> recommend(Recommend recommend);

	public Result<Object> delGoods(String sellerId, String result);

	public Result<Object> goodsOn(String sellerId);

	public Result<Object> goodsOff(String sellerId);

	public Result<Object> goodsRatio(Integer sellerId);

	public Result<Object> log(Integer user_id, String result);

	public Result<Object> range(Integer sellerId);

	public Result<Object> marks(Marks marks);

	public Result<Object> reply(Reply reply);

	public Result<Object> msglist(Integer id);

	public Result<Object> supplylist(String key, String goodType,String is_audit,String carriage_status);
	
	public Result<Object> purchaselist(String key, String goodType);
	
	public Result<Object> my_supply_statistics(Integer user_id);
	
	public List<Map<String, Object>> my_supply_type(Integer user_id,String type);
	
	public Result<Object> my_purchase_statistics(Integer user_id);
	
	public List<Map<String, Object>> my_purchase_type(Integer user_id,String type);

}
