package com.bonc.medicine.mapper.mall;


import com.bonc.medicine.entity.mall.Marks;
import com.bonc.medicine.entity.mall.Recommend;
import com.bonc.medicine.entity.mall.Reply;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface GongQiuSystemMapper {

	public int auditSSupply(Map map);

	public int auditFSupply(Map map);

	public int auditSPurchase(Map map);

	public int auditFPurchase(Map map);

	public int recommend(Recommend recommend);

	public int delGoods(Map map);

	public int goodsOn(Map map);

	public int goodsOff(Map map);

	public Map goodsRatio(Map map);

	public int log(Map map);

	public List<Map> range(Map map);

	public int marks(Marks marks);

	public int reply(Reply reply);
	
	public List<Map> msglist(Map map);
	
	public List<Map> supplylist(Map map);
	
	public List<Map> purchaselist(Map map);
}
