package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Marks;
import com.bonc.medicine.entity.mall.Recommend;
import com.bonc.medicine.entity.mall.Reply;
import com.bonc.medicine.service.mall.GongQiuSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GongQiuController {

	@Autowired
	private GongQiuSystemService gongQiuSystemService;

	/*
	 * 审核供应通过后改为审核通过且上架，未通过字段为未上架审核不通过result=1通过2不通过
	 */
	@GetMapping("/gongQiuSystem/auditSupply/{supplyId}/{result}/{comment}")
	public Result<Object> auditSupply(@PathVariable Integer supplyId, @PathVariable String result,
			@PathVariable String comment) {
		if ("1".equals(result)) {
			return gongQiuSystemService.auditSSupply(supplyId, result, comment);// 通过
		} else {
			return gongQiuSystemService.auditFSupply(supplyId, result, comment);// 未通过
		}
	}

	/*
	 * 审核求购通过后改为审核通过，未通过字段为未上架审核不通过result=1通过2不通过
	 */
	@GetMapping("/gongQiuSystem/auditPurchase/{purchaseId}/{result}/{comment}")
	public Result<Object> auditPurchase(@PathVariable Integer purchaseId, @PathVariable String result,
			@PathVariable String comment) {
		if ("1".equals(result)) {
			return gongQiuSystemService.auditSPurchase(purchaseId, result, comment);// 通过
		} else {
			return gongQiuSystemService.auditFPurchase(purchaseId, result, comment);// 未通过
		}
	}

	/*
	 * 设置商品推荐
	 */
	@PostMapping("/gongQiuSystem/recommend")
	public Result<Object> recommend(@RequestBody Recommend recommend) {
		return gongQiuSystemService.recommend(recommend);
	}

	/*
	 * 商品激活与删除 0删除1激活
	 */
	@GetMapping("/gongQiuSystem/goods/{supplyId}/{result}")
	public Result<Object> delGoods(@PathVariable Integer supplyId, @PathVariable String result) {
		return gongQiuSystemService.delGoods(supplyId, result);
	}

	/*
	 * 商品上架商品的主键supplyId传入字符串例如1,2,3,4,5
	 */
	@GetMapping("/gongQiuSystem/goodsOn/{supplyId}")
	public Result<Object> goodsOn(@PathVariable String supplyId) {
		return gongQiuSystemService.goodsOn(supplyId);
	}

	/*
	 * 商品下架商品的主键supplyId传入字符串例如1,2,3,4,5
	 */
	@GetMapping("/gongQiuSystem/goodsOff/{supplyId}")
	public Result<Object> goodsOff(@PathVariable String supplyId) {
		return gongQiuSystemService.goodsOff(supplyId);
	}

	/*
	 * 我的店铺查询上架下架未审核的商品数目
	 */
	@GetMapping("/gongQiuSystem/goodsRatio/{user_id}")
	public Result<Object> goodsRatio(@PathVariable Integer user_id) {
		return gongQiuSystemService.goodsRatio(user_id);
	}

	/*
	 * 管理能否登陆0禁止1允许
	 */
	@GetMapping("/gongQiuSystem/log/{userId}/{result}")
	public Result<Object> log(@PathVariable Integer userId, @PathVariable String result) {
		return gongQiuSystemService.log(userId, result);
	}

	/*
	 * 经营动态范围
	 */
	@GetMapping("/gongQiuSystem/range/{sellerId}")
	public Result<Object> range(@PathVariable Integer sellerId) {
		return gongQiuSystemService.range(sellerId);
	}

	/*
	 * 商家信息
	 */
	@GetMapping("/gongQiuSystem/info/{sellerId}")
	public String info(@PathVariable Integer sellerId) {
		return "未开发";
	}

	/*
	 * 商家信息页面的统计数据
	 */
	@GetMapping("/gongQiuSystem/statistics/{sellerId}")
	public String statistics(@PathVariable Integer sellerId) {
		return "未开发";
	}

	/*
	 * 留言功能
	 */
	@GetMapping("/gongQiuSystem/marks")
	public Result<Object> marks(@RequestBody Marks marks) {
		return gongQiuSystemService.marks(marks);
	}

	/*
	 * 回复功能
	 */
	@GetMapping("/gongQiuSystem/reply")
	public Result<Object> reply(@RequestBody Reply reply) {
		return gongQiuSystemService.reply(reply);
	}

	/*
	 * 留言详情
	 */
	@GetMapping("/gongQiuSystem/msglist")
	public Result<Object> msglist(@PathVariable Integer id) {
		return gongQiuSystemService.msglist(id);
	}

	/*
	 * 后台查询审核商品的列表
	 */
	@GetMapping("/gongQiuSystem/supplylist")
	public Result<Object> supplylist(String goods_name, String goodType, String tel, String name) {
		return gongQiuSystemService.supplylist(goods_name, goodType, tel, name);
	}

}
