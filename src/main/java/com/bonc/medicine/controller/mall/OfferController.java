package com.bonc.medicine.controller.mall;

import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Offer;
import com.bonc.medicine.service.mall.OfferService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offer")
public class OfferController {
	@Autowired
	OfferService offerService;
	
	@SuppressWarnings("unchecked")
	@PostMapping("/offer")
	@Authorization
	public Result<Object> realseOffer(@RequestBody Offer offer,@CurrentUser String user_id) {
		offer.setUser_id(Integer.parseInt(user_id));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String publish_time = sdf.format(new Date());
		offer.setPublish_time(publish_time);
		offer.setState('1');
		return ResultUtil.success(offerService.realseOffer(offer));
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/mine")
	public Result<Object> myOfferList(String user_id) {
		List<Map<String, Object>> result = offerService.myOfferList(user_id);
		List<String> purchase_ids = new ArrayList<>();
		for (Map<String, Object> map : result) {
			purchase_ids.add(map.get("purchase_id")+"");
		}
		List<Map<String, Object>> counts = new ArrayList<>();
		if (purchase_ids.size() > 0) {
			counts = offerService.countOffers(purchase_ids);
		}
		for (Map<String,Object> off : result) {
			String purchase_id = off.get("purchase_id")+"";
			for (Map<String, Object> cou : counts) {
				if (purchase_id.equals(cou.get("purchase_id")+"")) {
					off.put("count", cou.get("count"));
					break;
				}
			}
		}
		return ResultUtil.success(result);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/tome")
	public Result<Object> offerToMe(String user_id) {
		return ResultUtil.success(offerService.offerToMe(user_id));
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/detail")
	public Result<Object> offerDetail(String id) {
		return ResultUtil.success(offerService.offerDetail(id));
	}
}
