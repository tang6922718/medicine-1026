package com.bonc.medicine.controller.knowledgebase;

import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.knowledgebase.AuditService;
import com.bonc.medicine.service.knowledgebase.SopService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sop")
public class SopController {

    @Autowired
    private SopService sopService;

    @Autowired
    private AuditService auditService;

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
    @GetMapping("/plantDetailBack/{sop_id}")
    public Result<Object> getSopPlantsBack(@PathVariable(required = false) Integer sop_id) {
        return ResultUtil.success(sopService.sopPlantListBack(sop_id));
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/addPlantSopStep")
    @Transactional
    public Result<Object> addSops(@RequestBody String addJson, @CurrentUser String userId) {
         //addJson传入两部分，一部分为sop基本信息，一部分为sop步骤信息
        Map map = JacksonMapper.INSTANCE.readJsonToMap(addJson);
        Map sopMap = (Map) map.get("sop");//获取sop基本信息
        //sop状态
        sopMap.put("sop_status", 1);
        //审核状态
        sopMap.put("record_status", 2);
        //录入人id
        System.out.println("userId:" + userId);
        sopMap.put("record_user_id", userId);
        int count = sopService.sopAdd(sopMap);
        //从mybatis获取自增主键
        Long id = (Long) sopMap.get("id");
        //获取sop步骤信息
        List sopStepList = (List) map.get("sopStep");
        for (int i = 0; i < sopStepList.size(); i++) {
            ((Map) sopStepList.get(i)).put("sop_id", id);
            ((Map) sopStepList.get(i)).put("status", '1');
        }

        //插入审核表
        Map auditMap = new HashMap();
        auditMap.put("km_type","4");
        auditMap.put("id", id);
        String chinese_name = (String) sopMap.get("name");
        auditMap.put("title", chinese_name);
        count += auditService.addAudit(auditMap);
        count += sopService.sopStepAdd(sopStepList);
        return ResultUtil.success(count);
    }

    @SuppressWarnings("unchecked")
    @PutMapping("/editSop")
    @Transactional
    public Result<Object> editSops(@RequestBody String editJson, @CurrentUser String userId) {
        Map map = JacksonMapper.INSTANCE.readJsonToMap((editJson));
        Map sopMap = (Map) map.get("sop");//获取sop基本信息
        int id = (int) sopMap.get("id");//获取id
        //录入人id
        System.out.println("userId:" + userId);
        sopMap.put("record_user_id", userId);
        //删除当前所有步骤，再重新添加所有信息到sop步骤
        tombstoneStep(id);
        List sopStepList = (List) map.get("sopStep");//获取sop步骤信息
        for (int i = 0; i < sopStepList.size(); i++) {
            ((Map) sopStepList.get(i)).put("sop_id", id);
        }
        int count = sopService.sopUpdata(sopMap);
        count += sopService.sopStepAdd(sopStepList);

        //更改审核表
        Map auditMap = new HashMap();
        String chinese_name = (String) sopMap.get("name");
        System.out.println(chinese_name);
        auditMap.put("title", chinese_name);
        auditMap.put("km_type","4");
        auditMap.put("id", id);
        auditService.czAudit(auditMap);
        count += auditService.addAudit(auditMap);
        return ResultUtil.success(count);
    }

    @SuppressWarnings("unchecked")
    @DeleteMapping("/delSop/{id}")
    public Result<Object> deleteSops(@PathVariable(required = false) Integer id) {
        int count = sopService.sopDelete(id);//sop基本信息删除
        return ResultUtil.success(count);
    }

    @GetMapping("/cancelSop/{id}")
    public Result<Object> cancelSop(@PathVariable(required = false) Integer id) {
        int count = sopService.sopCancle(id);
        return ResultUtil.success(count);
    }

    @GetMapping("/getSopLists")
    public Result<Object> getSopLists() {
        return ResultUtil.success(sopService.getSopLists());
    }

    @GetMapping("/getStep")
    public Result<Object> getStep(Integer id, Integer step_order) {
        return ResultUtil.success(sopService.getStep(id, step_order));
    }

    @DeleteMapping("/delStep")
    public Result<Object> delStep(Integer id, Integer step_order) {
        return ResultUtil.success(sopService.delStep(id, step_order));
    }

    @PostMapping("/addStep")
    public Result<Object> addStep(@RequestBody String editJson) {
        Map map = JacksonMapper.INSTANCE.readJsonToMap((editJson));
        return ResultUtil.success(sopService.addStep(map));
    }

    public int tombstoneStep(Integer id) {
        return sopService.tombstoneStep(id);
    }
}
