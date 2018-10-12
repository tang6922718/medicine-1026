package com.bonc.medicine.controller.information;


import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.information.InfoService;
import com.bonc.medicine.service.knowledgebase.AuditService;
import com.bonc.medicine.service.management.CollectionService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InfoController {

    @Autowired
    InfoService infoService;

    @Autowired
    private ViewNumberService viewNumberService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private CollectionService collectionService;

    /**
     * @param catCode
     * @return
     * @description 查询咨讯列表
     */
    @RequestMapping("/infoList")
    public Result infoList(@RequestParam(required = false) String catCode,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false) String source_code,
                           @RequestParam(required = false) String operationClass,
                           @RequestParam(required = false, defaultValue = "1") String pageNum,
                           @RequestParam(required = false, defaultValue = "10") String pageSize) {
        List list = infoService.getAllInfo(catCode, pageNum, pageSize, title, status, source_code,operationClass);
        for (Object map : list) {
            int collectionNum = collectionService.collectCount("1", String.valueOf(((Map) map).get("id")));
            ((Map) map).put("collectionNum", collectionNum);
        }
        PageInfo<List> pageInfo = new PageInfo<List>(list);
        return ResultUtil.successTotal(list, pageInfo.getTotal());
    }

    /**
     * @param map
     * @return
     * @description 添加咨询
     */
    @PostMapping("/addInfo")
    /*@com.bonc.medicine.annotation.Authorization*/
    public Result addInfo(/*@CurrentUser String user_id, */@RequestBody Map<String, Object> map) {
        /*map.put("user_id", user_id);*/
        int count = infoService.addInfo(map);
        map.put("km_type", "2");
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }


    /**
     * @param id
     * @return
     * @description 咨讯撤销
     */
    @RequestMapping("/infoRepeal")
    public Result infoRepeal(@RequestParam String id) {
        return ResultUtil.success(infoService.infoRepeal(id));
    }


    /**
     * @param map
     * @return
     * @description 咨讯审核
     */
    @RequestMapping("/infoAudit")
    public Result infoAudit(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(infoService.infoAudit(map));
    }

    /**
     * @param id
     * @return
     * @description 咨讯详情
     */
    @RequestMapping("/infoDetail")
    public Result infoDetail(@RequestParam String id) {
        Map<String, String> numberMap = new HashMap();
        numberMap.put("objectId", id);
        numberMap.put("objectType", "2");
        viewNumberService.addOrUpdateViewNumberCord(numberMap);
        return ResultUtil.success(infoService.infoDetailById(id));
    }


    /**
     * @param map
     * @return
     * @description 咨讯编辑
     */
    @RequestMapping("/infoEdit")
    @com.bonc.medicine.annotation.Authorization
    public Result infoEdit(@CurrentUser String user_id, @RequestBody Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        int count = infoService.infoEditById(map);
        map.put("km_type", "2");
        count += auditService.czAudit(map);
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }

    /**
     * @param map
     * @return
     * @description 相关咨讯
     */
    @RequestMapping("/infoClass")
    public Result infoClass(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(infoService.infoClass(map));
    }

    /**
     * @param map
     * @return
     * @description 资讯浏览数
     */
    @RequestMapping("/infoReadCount")
    public Result infoReadCount(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(infoService.infoReadCount(map));
    }

    /**
     * @param map
     * @return
     * @description 删除资讯
     */
    @RequestMapping("/delInfo")
    public Result delInfo(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(infoService.delInfo(map));
    }

}
