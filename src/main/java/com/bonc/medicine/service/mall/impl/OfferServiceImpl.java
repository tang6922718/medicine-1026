package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.mall.Offer;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.mall.OfferMapper;
import com.bonc.medicine.service.mall.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OfferServiceImpl implements OfferService {
	@Autowired
	OfferMapper offerMapper;
	
	@Override
	public int realseOffer(Offer offer) {
		return offerMapper.insertOffer(offer);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> myOfferList(String user_id) {
		Map param = new HashMap();
		param.put("user_id", user_id);
		return offerMapper.offerList(param);
	}

	@Override
	public Map<String, Object> offerDetail(String id) {
		List<Map<String, Object>> result = offerMapper.offerDetail(id);
		if (result.size() != 1) {
			throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
		}
		return result.get(0);
	}

	@Override
	public List<Map<String, Object>> countOffers(List<String> purchase_ids) {
		return offerMapper.countOffers(purchase_ids);
	}

	@Override
	public List<Map<String, Object>> offerToMe(String user_id) {
		return offerMapper.offerToMe(user_id);
	}

}
