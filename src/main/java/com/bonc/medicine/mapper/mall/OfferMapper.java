package com.bonc.medicine.mapper.mall;

import com.bonc.medicine.entity.mall.Offer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface OfferMapper {
	public int insertOffer(Offer offer);
	public List<Map<String,Object>> offerList(Map param);
	public List<Map<String,Object>> offerDetail(String id);
	public List<Map<String, Object>> offerToMe(String user_id);
	public List<Map<String,Object>> countOffers(List<String> purchase_ids);
}
