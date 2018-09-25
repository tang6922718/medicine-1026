package com.bonc.medicine.service.mall.impl;

import com.bonc.medicine.mapper.mall.ViewTheDataMapper;
import com.bonc.medicine.service.mall.ViewTheDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class ViewTheDatalmpl implements ViewTheDataService {

    @Autowired
    private ViewTheDataMapper viewTheDataMapper;

    @Override
    public List<Map<String, Object>> interaction(Integer user_id) {

        Map map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int days = -6;
        Calendar cal = Calendar.getInstance();
        String end_time = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, days);
        String start_time = sdf.format(cal.getTime());
        map.put("user_id", user_id);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        return viewTheDataMapper.interaction(map);
    }
    
    @Override
    public List<Map<String, Object>> thisWeekInteraction(Integer user_id) {
    	
    	Map map = new HashMap<>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	int days = -6;
    	Calendar cal = Calendar.getInstance();
    	String end_time = sdf.format(cal.getTime());
    	cal.add(Calendar.DATE, days);
    	String start_time = sdf.format(cal.getTime());
    	map.put("user_id", user_id);
    	map.put("start_time", start_time);
    	map.put("end_time", end_time);
    	return viewTheDataMapper.thisWeekInteraction(map);
    }
    
    @Override
    public List<Map<String, Object>> historyInteraction(Integer user_id) {
    	
    	Map map = new HashMap<>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String end_time = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String start_time = sdf.format(cal.getTime());
    	map.put("user_id", user_id);
    	map.put("start_time", start_time);
    	map.put("end_time", end_time);
    	return viewTheDataMapper.historyInteraction(map);
    }
    
    @Override
    public List<Map<String, Object>> rankInteraction() {
    	return viewTheDataMapper.rankInteraction();
    }
    
    @Override
    public List<Map<String, Object>> follow() {
    	return viewTheDataMapper.follow();
    }
    
    @Override
    public List<Map<String, Object>> collection() {
    	return viewTheDataMapper.collection();
    }
    
    @Override
    public List<Map<String, Object>> issue() {
    	return viewTheDataMapper.issue();
    }

    @Override
    public List<Map<String, Object>> problemStatistics(Integer user_id) {

        Map map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int days = -6;
        Calendar cal = Calendar.getInstance();
        String end_time = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, days);
        String start_time = sdf.format(cal.getTime());
        map.put("user_id", user_id);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        return viewTheDataMapper.problemStatistics(map);
    }

}
