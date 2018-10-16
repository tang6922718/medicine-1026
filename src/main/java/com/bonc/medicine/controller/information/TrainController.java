package com.bonc.medicine.controller.information;


import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.information.TrainService;
import com.bonc.medicine.service.knowledgebase.AuditService;
import com.bonc.medicine.service.management.CollectionService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.Signature;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class TrainController {

    @Autowired
    TrainService trainService;

    @Autowired
    private ViewNumberService viewNumberService;

    @Autowired
    AuditService auditService;

    @Autowired
    CollectionService collectionService;

    /**
     * @param map
     * @return
     * @description 新建线下培训(发布线下培训)
     */
    @RequestMapping("/createTrain")
    public Result createTrain(@CurrentUser String user_id, @RequestBody Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        int count = trainService.createTrain(map);
        map.put("km_type", "7");
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }

    /**
     * @param map
     * @return
     * @description 线下培训列表(线下培训列表)  列表传 {}  详情传  id
     */
    @RequestMapping("/selectTrainList")
    public Result selectTrainList(@RequestBody Map<String, Object> map,
                                  @RequestParam(required = false, defaultValue = "1") int pageNum,
                                  @RequestParam(required = false, defaultValue = "10") int pageSize) {
        List list = trainService.selectTrainList(map, pageNum, pageSize);
        PageInfo<List> pageInfo = new PageInfo<List>(list);
        return ResultUtil.successTotal(list, pageInfo.getTotal());
    }


    /**
     * @param map
     * @return
     * @description 新建视频
     */
    @RequestMapping("/createVideo")
    @Authorization
    public Result createVideo(@RequestBody Map<String, Object> map, @CurrentUser String user_id) {
        map.putIfAbsent("user_id", user_id);//获取当前登录者id作为视频的操作人
        int count = trainService.createVideo(map);
        map.put("km_type", "5");
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }

    /**
     * @param map
     * @return
     * @description 交流互动(交流互动) (插入)
     */
    @RequestMapping("/addComment")
    @Authorization
    public Result addComment(@CurrentUser String user_id, @RequestBody Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        return ResultUtil.success(trainService.addComment(map));
    }

    /**
     * @param map
     * @return
     * @description 交流互动(交流互动) (查询)
     */
    @RequestMapping("/selectComment")
    public Result selectComment(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(trainService.selectComment(map));
    }


    /**
     * @return
     * @description 视频课程列表（有哪些类别）
     */
    @RequestMapping("/selectCourseClass")
    public Result selectCourseClass() {
        return ResultUtil.success(trainService.selectCourseClass());
    }

    /**
     * @param map
     * @return
     * @description 视频课程列表(视频课程列表)(通过id查询 视频课程详情) (查询)  不传全部，类别查询，id查询都可以
     */
    @RequestMapping("/selectCourseList")
    public Result selectCourseList(@CurrentUser String user_id,
                                   @RequestBody(required = false) Map<String, Object> map,
                                   @RequestParam(required = false, defaultValue = "1") int pageNum,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        if (null != map.get("id")) {
            Map<String, String> numberMap = new HashMap();
            numberMap.put("objectId", map.get("id") + "");
            numberMap.put("objectType", "4");
            viewNumberService.addOrUpdateViewNumberCord(numberMap);
        }
        List list = trainService.selectCourseList(map, pageNum, pageSize);
        Map map2 = new HashMap();
        map2.put("objectType", "4");
        for (Object map1 : list) {
            map2.put("objectId", String.valueOf(((Map) map1).get("id")));
            ((Map) map1).put("viewNum", viewNumberService.queryViewNumber(map2));
            ((Map) map1).put("isCollect", collectionService.isCollect("5", String.valueOf(((Map) map1).get("id")), user_id));
        }
        PageInfo<List> pageInfo = new PageInfo<List>(list);
        return ResultUtil.successTotal(list, pageInfo.getTotal());

    }

    /**
     * @param map
     * @return
     * @description 预约报名(预约报名)
     */
    @RequestMapping("/addTrainApply")
    @Authorization
    public Result addTrainApply(@CurrentUser String user_id, @RequestBody(required = false) Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        return ResultUtil.success(trainService.addTrainApply(map));
    }

    /**
     * @param map
     * @return
     * @description 查询是否报名(根据object_id, object_type, user_id)
     */
    @RequestMapping("/selectApply")
    public Result selectApply(@CurrentUser String user_id, @RequestBody(required = false) Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        return ResultUtil.success(trainService.selectApply(map));
    }

    /**
     * @param map
     * @return
     * @description 查询报名数量(根据object_id)
     */
    @RequestMapping("/selectApplyNum")
    public Result selectApplyNum(@CurrentUser String user_id, @RequestBody(required = false) Map<String, Object> map) {
        map.put("user_id", user_id);
        return ResultUtil.success(trainService.selectApply(map));
    }

    /**
     * @param map
     * @return
     * @description 我的培训(查询预约报名的直播和线下培训列表)
     */
    @RequestMapping("/selectTrainApply")
    public Result selectTrainApply(@CurrentUser String user_id, @RequestBody Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        return ResultUtil.success(trainService.selectTrainApply(map));
    }

    /**
     * @param map
     * @return
     * @description 我的贡献(查询我的贡献中直播和线下培训列表)
     */
    @RequestMapping("/selectTrainContribute")
    public Result selectTrainContribute(@CurrentUser String user_id, @RequestBody Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        return ResultUtil.success(trainService.selectTrainContribute(map));
    }

    /**
     * @param map
     * @return
     * @description 编辑线下视频  (编辑线下视频)
     */
    @RequestMapping("/editOfflineTrainVideo")
    @Authorization
    public Result editOfflineTrainVideo(@CurrentUser String user_id, @RequestBody(required = false) Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        return ResultUtil.success(trainService.editOfflineTrainVideo(map));
    }


    /**
     * @param map
     * @return
     * @description 编辑视频  (编辑视频)
     */
    @RequestMapping("/editVideoCourse")
    public Result editVideoCourse(@RequestBody(required = false) Map<String, Object> map) {
        int count = trainService.editVideoCourse(map);
        map.put("km_type", "5");
        count += auditService.czAudit(map);
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }

    /**
     * @param map
     * @return
     * @description 删除线下视频  (删除视频)
     */
    @RequestMapping("/delCourseTrainVideo")
    public Result delCourseTrainVideo(@RequestBody(required = false) Map<String, Object> map) {
        return ResultUtil.success(trainService.delCourseTrainVideo(map));
    }

    /**
     * @param map
     * @return
     * @description 删除线下视频  (删除视频)
     */
    @RequestMapping("/delOfflineTrainVideo")
    public Result delOfflineTrainVideo(@RequestBody(required = false) Map<String, Object> map) {
        return ResultUtil.success(trainService.delOfflineTrainVideo(map));
    }

    /**
     * @param map
     * @return
     * @description 撤销线下培训（撤销线下培训）
     */
    @RequestMapping("/repealOfflineTrain")
    public Result repealOfflineTrain(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(trainService.repealOfflineTrain(map));
    }

    /**
     * @param map
     * @return
     * @description 撤销课程视频（撤销课程视频）
     */
    @RequestMapping("/repealVideoCourse")
    public Result repealVideoCourse(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(trainService.repealVideoCourse(map));
    }

    /**
     * @param map
     * @return
     * @description 编辑线下培训(编辑线下培训)
     */
    @RequestMapping("/editOfflineTrain")
    public Result editOfflineTrain(@RequestBody(required = false) Map<String, Object> map) {
        int count = trainService.editOfflineTrain(map);
        map.put("km_type", "7");
        count += auditService.czAudit(map);
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }

    /**
     * @param map
     * @return
     * @description 删除线下培训(编辑线下培训)逻辑删除
     */
    @RequestMapping("/delOfflineTrain")
    public Result delOfflineTrain(@RequestBody(required = false) Map<String, Object> map) {
        return ResultUtil.success(trainService.delOfflineTrain(map));
    }

    /**
     * @return
     * @description 获取腾讯点播上传密钥
     */
    @RequestMapping("/getSignature")
    public Result getSignature() {
        Signature sign = new Signature();
        sign.setSecretId("AKID4xbz7yQtxlIYYJK20i9KEsGfuuvueEVW");
        sign.setSecretKey("uhP7kFUWSFn45yyqVxBAvUp9RbYGjxxl");
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2);
        String signature = "";
        try {
            signature = sign.getUploadSignature();
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
        }
        return ResultUtil.success(signature);
    }

    /**
     * @return
     * @description 查询专家列表 （在创建培训的时候用）
     */
    @RequestMapping("/selectSpecialist")
    public Result selectSpecialist(@RequestParam(required = false) String specId) {
        Integer spec = null;
        if (!StringUtils.isEmpty(specId)) {
            spec = Integer.parseInt(specId);
        }
        return ResultUtil.success(trainService.selectSpecialist(spec));
    }


    /**
     * @return
     * @description 返回视频热度前5
     */
    @RequestMapping("/selectVideoHot")
    public Result selectVideoHot() {
        return ResultUtil.success(trainService.selectVideoHot());
    }
}
