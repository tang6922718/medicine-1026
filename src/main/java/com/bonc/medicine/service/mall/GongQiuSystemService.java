package com.bonc.medicine.service.mall;

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

	public Result<Object> delGoods(Integer sellerId, String result);

	public Result<Object> goodsOn(String sellerId);

	public Result<Object> goodsOff(String sellerId);

	public Result<Object> goodsRatio(Integer sellerId);

	public Result<Object> log(Integer user_id, String result);

	public Result<Object> range(Integer sellerId);

	public Result<Object> marks(Marks marks);

	public Result<Object> reply(Reply reply);

	public Result<Object> msglist(Integer id);

	public Result<Object> supplylist(String goods_name, String goodType,String is_audit,String carriage_status);
	
	public Result<Object> purchaselist(String key, String goodType);

}
