package com.bonc.medicine.controller.information;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.RedisService;
import com.bonc.medicine.service.information.LiveService;
import com.bonc.medicine.utils.ResultUtil;
import com.bonc.medicine.utils.TecentCloudUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger logger = Logger.getLogger("LiveController");


    private static  Set<String> channelSet = new HashSet<>();

    /**
     * @param map
     * @return
     * @description 创建直播(直播)
     */
    @RequestMapping("/createLive")
    public Result createLive(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(liveService.createLive(map));
    }

    /**
     * @return
     * @description 获取所有直播（直播课堂列表）（包含为直播未开启的）
     */
    @RequestMapping("/selectLive")
    public Result selectLive() {
        List<Map<String, String>> lists = TecentCloudUtils.getAllRoomList();
        for (Map<String, String> map : lists) {
            liveService.updateLiveStatus(map.get("id"), map.get("status"));
        }
        return ResultUtil.success(liveService.selectAllLive());
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
    public Result editLive(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(liveService.editLive(map));
    }

    /**
     * @param map
     * @return
     * @description 撤销直播 （撤销直播）
     */
    @RequestMapping("/repealLive")
    public Result repealLive(@RequestBody Map<String, Object> map) {
        return ResultUtil.success(liveService.repealLive(map));
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
        for (String channel : channelSet){
            redisService.del(channel);
        }

        return ResultUtil.success("ok");
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
