package com.bonc.medicine.controller.information;


import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.service.information.LiveService;
import com.bonc.medicine.service.information.TrainService;
import com.bonc.medicine.service.knowledgebase.AuditService;
import com.bonc.medicine.service.thumb.ThumbService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.TecentCloudUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.logging.Logger;

@RestController
public class LiveController {

    @Autowired
    LiveService liveService;

    @Autowired
    RedisService redisService;

    @Autowired
    private ViewNumberService viewNumberService;

    @Autowired
    TrainService trainService;

    @Autowired
    AuditService auditService;

    @Autowired
    ThumbService thumbService;

    private static final Logger logger = Logger.getLogger("LiveController");


    private static Set<String> channelSet = new HashSet<>();

    /**
     * @param map
     * @return
     * @description 创建直播(直播)
     */
    @RequestMapping("/createLive")
    @MethodLog(remark = "新增,创建直播,培训")
    @com.bonc.medicine.annotation.Authorization
    public Result createLive(@CurrentUser String user_id, @RequestBody Map<String, Object> map) {
        map.putIfAbsent("user_id", user_id);
        int count = liveService.createLive(map);
        map.put("km_type", "8");
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }


    /**
     * @return
     * @description 获取所有直播（直播课堂列表）（包含为直播未开启的）
     */
    @RequestMapping("/selectLive")
    public Result selectLive(@RequestBody Map<String, Object> map,
                             @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                             @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {



       /*  不在腾讯云上去检测直播状态，根据数据库开播时间每小时为单位扫描更改状态
       List<Map<String, String>> lists = TecentCloudUtils.getAllRoomList();
       for (Map<String, String> map1 : lists) {
            liveService.updateLiveStatus(map1.get("id"), map1.get("status"));
        }*/
        List list = liveService.selectAllLive(map, pageNum, pageSize);
    /*  Map map2 = new HashMap();
        map2.put("object_type", "2");
        for (Object map1 : list) {
            map2.put("object_id", String.valueOf(((Map) map1).get("id")));
            ((Map) map1).put("applyNum", trainService.selectApply(map2));
        }*/
        PageInfo<List> pageInfo = new PageInfo<List>(list);
        return ResultUtil.successTotal(list, pageInfo.getTotal());
    }

    /**
     * @param id
     * @return
     * @description 开启录制
     */
    @RequestMapping("/openRecord")
    public Result openRecord(@RequestParam String id) {
        return ResultUtil.success(TecentCloudUtils.openRecordTask(id));
    }

    /**
     * @param id
     * @return
     * @description 关闭录制
     */
    @RequestMapping("/closeRecord")
    public Result openRecord(@RequestParam String id, @RequestParam String taskId) {
        return ResultUtil.success(TecentCloudUtils.closeRecordTask(id, taskId));
    }

    /**
     * @param id
     * @return
     * @description 点播列表（视频点播）(查询录制视频)
     */
    @RequestMapping("/selectRecordList")
    public Result selectRecordList(@RequestParam String id) {
        return ResultUtil.success(TecentCloudUtils.getRecordList(id));
    }

    /**
     * @param map
     * @return
     * @description 编辑直播 （编辑直播 ）
     */
    @RequestMapping("/editLive")
    @MethodLog(remark = "修改,编辑直播,培训")
    public Result editLive(@CurrentUser String user_id, @RequestBody Map<String, Object> map) {
        map.put("user_id", user_id);
        int count = liveService.editLive(map);
        map.put("km_type", "8");
        count += auditService.czAudit(map);
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }

    /**
     * @param map
     * @return
     * @description 撤销直播 （撤销直播）
     */
    @RequestMapping("/repealLive")
    @MethodLog(remark = "修改,撤销直播,培训")
    public Result repealLive(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(liveService.repealLive(map));
    }

    /**
     * @param map
     * @return
     * @description 删除直播 （删除直播）
     */
    @RequestMapping("/delLive")
    @MethodLog(remark = "删除,删除直播,培训")
    public Result delLive(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(liveService.delLive(map));
    }


    /**
     * @param map
     * @return
     * @description 统一查看观看数，收藏数等接口 （你传我就帮你查）   关闭不用
     */
    @RequestMapping("/selectWatchNum")
    public Result selectWatchNum(@RequestBody Map<String, String> map) {
        Map map1 = new HashMap();
        //观看数 预约数  评论数 点赞数
        Map map2;
        String viewNum = "0";
        String appointmentNum = "0";
        String praiseNum = "0";
        if (!(map2 = viewNumberService.queryViewNumber(map)).isEmpty()) {
            viewNum = (String) map2.get("viewNumber");
        }
        if (!(map2 = trainService.queryAppointmentNumber(map)).isEmpty()) {
            appointmentNum = (String) map2.get("bmNum");
        }
        if (!(map2 = thumbService.selectThumbNumber(map)).isEmpty()) {
            praiseNum = (String) map2.get("praise_user_id");
        }
        map1.put("gk", viewNum);
        map1.put("bm", appointmentNum);
        map1.put("dz", praiseNum);
        return ResultUtil.success(map1);
    }


    /**
     * @param
     * @return
     * @description 通知回调接口
     */
    @RequestMapping("/adviceCallback")
    public Result adviceCallback(@RequestBody Map map) {
        //存储通知事件
        return ResultUtil.success("OK");
    }

    /**
     * @param
     * @return
     * @description 保存直播观众人员数
     */
    @RequestMapping("/saveWatchNum")
    public Result saveWatchNum() throws Exception {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        Map<String, Integer> mapList = new HashMap();
        for (String channel : channelSet) {
            int watchNum = (int) redisService.setCard(channel);
            mapList.put(channel, watchNum);
        }
        map.put("map", mapList);
        //批量更新观看人数
        liveService.updateWatchPeople(map);
        //数据库更新完，再删redis缓存数据
        channelSet.clear();
        for (String channel : channelSet) {
            redisService.del(channel);
        }
        return ResultUtil.success("ok");
    }

    /**
     * @return
     * @description 直接更新直播人数
     */
    @RequestMapping("/addWatchNum")
    public Result saveUser(@RequestParam String room_id) {
        return ResultUtil.success(liveService.updateWatchNum(room_id));
    }

    /**
     * @return
     * @description 回播上传（无直播端采用web上传的方式）
     */
    @RequestMapping("/replayUpload")
    public Result replayUpload(@RequestBody Map map) {
        return ResultUtil.success(liveService.replayUpload(map));
    }

    /**
     * @return
     * @description 回播上传（采用直播自动录制方式，完成录制回调接口）
     */
    @RequestMapping("/replayCallback")
    public Result replayCallback(@RequestBody Map map) {
        if (map.get("event_type").equals(100)
                && (!StringUtils.isEmpty(map.get("video_url")))
                && (!StringUtils.isEmpty(map.get("channel_id")))) {

            liveService.updateLiveReplay(map);

        }
        System.out.println(JacksonMapper.INSTANCE.writeObjectToJson(map));
        return ResultUtil.success(map);
    }

    /**
     * @param map
     * @return
     * @description 记录观众ToRedis
     */
    @RequestMapping("/addAudience")
    public Result saveUser(@RequestBody Map map) {
        String channel = (String) map.get("channel");
        channelSet.add(channel);
        long saveNum = 0;
        try {
            saveNum = redisService.setAdd(channel, map.get("user_id"));
            logger.info("添加用户到redis成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(saveNum);
    }
}
