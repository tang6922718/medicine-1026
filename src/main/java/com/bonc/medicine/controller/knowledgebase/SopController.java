package com.bonc.medicine.controller.knowledgebase;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.knowledgebase.SopService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sop")
public class SopController {

    @Autowired
    private SopService sopService;

    @SuppressWarnings("unchecked")
    @GetMapping("/plantStandardManage/{sop_type}/{record_status}")
    public Result<Object> getSops(@PathVariable(required = false) Integer sop_type, @PathVariable(required = false) Integer record_status) {
        return  ResultUtil.success(sopService.sopStandardList(sop_type,record_status));
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/plantDetail/{variety_id}")
    public Result<Object> getSopPlants(@PathVariable(required = false) Integer variety_id) {
        return ResultUtil.success(sopService.sopPlantList(variety_id));
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/addPlantSopStep")
    @Transactional
    public Result<Object> addSops(@RequestBody String addJson){
         //addJson传入两部分，一部分为sop基本信息，一部分为sop步骤信息
        Map map = JacksonMapper.INSTANCE.readJsonToMap(addJson);
        Map sopMap = (Map) map.get("sop");//获取sop基本信息
        //时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String addTime = df.format(new Date());
        sopMap.put("record_time", addTime);
        //sop状态
        sopMap.put("sop_status", 1);
        //审核状态
        sopMap.put("record_status", 2);
        List sopStepList = (List) map.get("sopStep");//获取sop步骤信息
        int count = sopService.sopAdd(sopMap);
        System.out.println(sopMap.get("id"));
        Long id = (Long) sopMap.get("id");
        for (int i = 0; i < sopStepList.size(); i++) {
            ((Map) sopStepList.get(i)).put("sop_id", id);
        }
        count += sopService.sopStepAdd(sopStepList);
        return ResultUtil.success(count);
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/editSop")
    @Transactional
    public Result<Object> editSops(@RequestBody String editJson){
        Map map = JacksonMapper.INSTANCE.readJsonToMap((editJson));
        Map sopMap = (Map) map.get("sop");//获取sop基本信息
        System.out.println(sopMap);
        List sopStepList = (List) map.get("sopStep");//获取sop步骤信息
        System.out.println(sopStepList);

        int count = sopService.sopUpdata(sopMap);
        count += sopService.sopStepUpdata(sopStepList);
        return ResultUtil.success(count);
    }

    @SuppressWarnings("unchecked")
    @DeleteMapping("/delSop/{variety_id}")
    public Result<Object> deleteSops(@PathVariable(required = false) Integer variety_id) {
        int count = sopService.sopDelete(variety_id);//sop基本信息删除
        return ResultUtil.success(count);
    }

    @GetMapping("/cancelSop/{variety_id}")
    public Result<Object> cancelSop(@PathVariable(required = false) Integer variety_id) {
        int count = sopService.sopCancle(variety_id);
        return ResultUtil.success(count);
    }

    @GetMapping("/getSopLists")
    public Result<Object> getSopLists() {
        return ResultUtil.success(sopService.getSopLists());
    }

}
