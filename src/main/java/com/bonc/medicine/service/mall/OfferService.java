package com.bonc.medicine.service.mall;


import com.bonc.medicine.entity.mall.Offer;

import java.util.List;
import java.util.Map;

public interface OfferService {
	public int realseOffer(Offer offer);
	public List<Map<String,Object>> myOfferList(String user_id);
	public Map<String,Object> offerDetail(String id);
	public List<Map<String,Object>> countOffers(List<String> purchase_ids);
	public List<Map<String,Object>> offerToMe(String user_id);
}
