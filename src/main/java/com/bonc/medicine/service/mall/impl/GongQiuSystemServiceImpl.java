package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Marks;
import com.bonc.medicine.entity.mall.Recommend;
import com.bonc.medicine.entity.mall.Reply;
import com.bonc.medicine.mapper.mall.GongQiuSystemMapper;
import com.bonc.medicine.service.mall.GongQiuSystemService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class GongQiuSystemServiceImpl implements GongQiuSystemService {

	@Autowired
	private GongQiuSystemMapper gongQiuSystemMapper;

	@Override
	public Result<Object> auditSSupply(Integer supplyId, String result, String comment) {

		Map map = new HashMap<>();
		map.put("supplyId", supplyId);
		map.put("result", result);
		map.put("comment", comment);
		return ResultUtil.success(gongQiuSystemMapper.auditSSupply(map));
	}

	@Override
	public Result<Object> auditFSupply(Integer supplyId, String result, String comment) {

		Map map = new HashMap<>();
		map.put("supplyId", supplyId);
		map.put("result", result);
		map.put("comment", comment);
		return ResultUtil.success(gongQiuSystemMapper.auditFSupply(map));
	}

	@Override
	public Result<Object> auditSPurchase(Integer purchaseId, String result, String comment) {

		Map map = new HashMap<>();
		map.put("purchaseId", purchaseId);
		map.put("result", result);
		map.put("comment", comment);
		return ResultUtil.success(gongQiuSystemMapper.auditSPurchase(map));
	}

	@Override
	public Result<Object> auditFPurchase(Integer purchaseId, String result, String comment) {

		Map map = new HashMap<>();
		map.put("purchaseId", purchaseId);
		map.put("result", result);
		map.put("comment", comment);
		return ResultUtil.success(gongQiuSystemMapper.auditFPurchase(map));
	}

	@Override
	public Result<Object> recommend(Recommend recommend) {
		return ResultUtil.success(gongQiuSystemMapper.recommend(recommend));
	}

	@Override
	public Result<Object> delGoods(Integer supplyId, String result) {

		Map map = new HashMap<>();
		map.put("supplyId", supplyId);
		map.put("result", result);
		return ResultUtil.success(gongQiuSystemMapper.delGoods(map));
	}

	@Override
	public Result<Object> goodsOn(String supplyId) {

		Map map = new HashMap<>();
		String[] arrSupplyId = supplyId.split(",");
		int[] arrIntSupply = new int[arrSupplyId.length];
		for (int i = 0; i < arrSupplyId.length; i++) {
			arrIntSupply[i] = Integer.parseInt(arrSupplyId[i]);
		}
		int total = 0;// 受影响行数
		for (int i = 0; i < arrIntSupply.length; i++) {
			map.put("supplyId", arrIntSupply[i]);
			total += gongQiuSystemMapper.goodsOn(map);
		}
		return ResultUtil.success(total);
	}

	@Override
	public Result<Object> goodsOff(String supplyId) {

		Map map = new HashMap<>();
		String[] arrSupplyId = supplyId.split(",");
		int[] arrIntSupply = new int[arrSupplyId.length];
		for (int i = 0; i < arrSupplyId.length; i++) {
			arrIntSupply[i] = Integer.parseInt(arrSupplyId[i]);
		}
		int total = 0;// 受影响行数
		for (int i = 0; i < arrIntSupply.length; i++) {
			map.put("supplyId", arrIntSupply[i]);
			total += gongQiuSystemMapper.goodsOff(map);
		}
		return ResultUtil.success(total);
	}

	@Override
	public Result<Object> goodsRatio(Integer sellerId) {

		Map map = new HashMap<>();
		map.put("sellerId", sellerId);
		return ResultUtil.success(gongQiuSystemMapper.goodsRatio(map));
	}
	
	@Override
	public Result<Object> log(Integer user_id,String result) {
		
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("result", result);
		return ResultUtil.success(gongQiuSystemMapper.log(map));
	}
	
	@Override
	public Result<Object> range(Integer sellerId) {
		
		Map map = new HashMap<>();
		map.put("sellerId", sellerId);
		return ResultUtil.success(gongQiuSystemMapper.range(map));
	}
	
	@Override
	public Result<Object> marks(Marks marks) {
		return ResultUtil.success(gongQiuSystemMapper.marks(marks));
	}
	
	@Override
	public Result<Object> reply(Reply reply) {
		return ResultUtil.success(gongQiuSystemMapper.reply(reply));
	}
	
	@Override
	public Result<Object> msglist(Integer id) {
		
		Map map = new HashMap<>();
		map.put("id", id);
		return ResultUtil.success(gongQiuSystemMapper.msglist(map));
	}
	
	@Override
	public Result<Object> supplylist(String goods_name, String goodType, String tel, String name) {
		
		Map map = new HashMap<>();
		map.put("goods_name", goods_name);
		map.put("goodType", goodType);
		map.put("tel", tel);
		map.put("name", name);
		return ResultUtil.success(gongQiuSystemMapper.supplylist(map));
	}
}
