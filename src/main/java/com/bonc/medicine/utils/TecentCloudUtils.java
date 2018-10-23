package com.bonc.medicine.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class TecentCloudUtils {

    private static final Logger logger = Logger.getLogger("TecentCloudUtils");

    // 用于 生成推流防盗链的key
    public static final String key = "c1101494bc9fa98fa4a0178923fdeaec";

    public static final String bizid = "30435";

    public static final String APPID = "1257359387";

    // 用于主动查询和被动通知的key:API鉴定key
    public static final String API_KEY = "a83d83bddb9cf3d517e79d1f65cb9709";

    // API回调地址
    public static final String API_ADDRESS = "http://fcgi.video.qcloud.com/common_access";

    /**
     * 推流地址
     */
    public static final String PUSH_URL = "rtmp://" + bizid + ".livepush.myqcloud.com/live/" + bizid + "_";

    /**
     * PC拉流地址
     */
    public static final String PULL_RTMP_URL = "rtmp://" + bizid + ".liveplay.myqcloud.com/live/" + bizid + "_";
    /**
     * app拉流地址
     */
    public static final String PULL_URL = "http://" + bizid + ".liveplay.myqcloud.com/live/" + bizid + "_";

    /**
     * 这是推流防盗链的生成 KEY+ streamId + txTime
     *
     * @param key      防盗链使用的key
     * @param streamId 通常为直播码.示例:bizid+房间id
     * @param txTime   到期时间
     * @return
     * @author lnexin@aliyun.com
     */
    public static String getSafeUrl(String key, String streamId, long txTime) {
        String input = new StringBuilder().append(key).append(streamId).append(Long.toHexString(txTime).toUpperCase()).toString();

        String txSecret;

        txSecret = MD5Encode.stringToMD5(input);

        return txSecret == null ? "" : new StringBuilder().append("txSecret=").append(txSecret).append("&").append("txTime=").append(Long.toHexString(txTime).toUpperCase()).toString();
    }

    /**
     * 推流地址生成
     */
    public static String getPushUrl(String roomId) {
        Long now = System.currentTimeMillis() + 60L * 60L * 24L * 30L * 1000L;// 要转成long类型，不然为负数
        // 当前毫秒数+需要加上的时间毫秒数 = 过期时间毫秒数
        Long txTime = now / 1000;// 推流码过期时间秒数
        String safeUrl = getSafeUrl(key, bizid + "_" + roomId, txTime);
        String realPushUrl = PUSH_URL + roomId + "?bizid=" + bizid + "&" + safeUrl;
        return realPushUrl;
    }

    /**
     * APP拉流地址获得
     */
    public static String getPullUrl(String owenrId) {
        String appPullUrl = PULL_URL + owenrId + ".flv";
        return appPullUrl;
    }

    /**
     * PC拉流地址获得
     */
    public static String getPullRtmpUrl(String owenrId) {
        String pullRtmpUrl = PULL_RTMP_URL + owenrId;
        return pullRtmpUrl;
    }

    /**
     * 获取关闭直播的url关闭直播 需要发送请求给腾讯服务器,然后返回结果
     *
     * @param id 需要关闭的房间ID
     * @return 关闭直播的url
     * @author lnexin@aliyun.com
     * @date 2017年7月22日 下午2:54:14
     */
    public static String getCloseLiveUrl(String id) {
        // 此请求的有效时间
        Long current = System.currentTimeMillis() / 1000 + 60L * 60L * 24L;
        // 生成sign签名
        String sign = MD5Encode.stringToMD5(new StringBuffer().append(API_KEY).append(current).toString());
        // 生成需要关闭的直播码
        String code = bizid + "_" + id;
        //   String code = id;
        // 生成关闭的参数列表
        String params = new StringBuffer().append("&interface=Live_Channel_SetStatus").append("&Param.s.channel_id=").append(code).append("&Param.n.status=0").append("&t=").append(current).append("&sign=").append(sign).toString();

        // 拼接关闭URL
        String url = API_ADDRESS + "?appid=" + APPID + params;
        return url;
    }

    /**
     * 获取录播查询请求地址
     *
     * @param id
     * @return
     * @author lnexin@aliyun.com
     * @date 2017年7月22日 下午12:45:57
     */
    public static String getRecordUrl(String id) {
        Long current = (System.currentTimeMillis() + 60 * 60 * 24 * 1000) / 1000;
        String sign = MD5Encode.stringToMD5(new StringBuffer().append(API_KEY).append(current).toString());
        String code = bizid + "_" + id;
        String params = new StringBuffer().append("&interface=Live_Tape_GetFilelist").append("&Param.s.channel_id=").append(code).append("&t=").append(current).append("&sign=").append(sign).toString();
        // 拼接URL
        String url = API_ADDRESS + "?appid=" + APPID + params;
        return url;
    }


    public static String getLiveListUrl() {
        Long current = (System.currentTimeMillis() + 60 * 60 * 24 * 1000) / 1000;
        String sign = MD5Encode.stringToMD5(new StringBuffer().append(API_KEY).append(current).toString());

        String params = new StringBuffer().append("&interface=Live_Channel_GetChannelList").append("&t=").append(current).append("&sign=").append(sign).toString();
        // 拼接URL
        String url = API_ADDRESS + "?appid=" + APPID + params;
        return url;
    }


    /**
     * 获取腾讯点播录制列表
     *
     * @return
     */
    public static List<Map<String, String>> getRecordList(String uid) {

        List<Map<String, String>> list = new ArrayList<>();
        String json = HttpRequestUtil.sendGet(getRecordUrl(uid));
        logger.info(json);
        JSONObject jsonObject = JSON.parseObject(json).getJSONObject("output");
        JSONArray jsonObject2 = jsonObject.getJSONArray("file_list");
        for (int i = 0; i < jsonObject2.size(); i++) {
            String id = jsonObject2.getJSONObject(i).getString("id");
            String room_id = jsonObject2.getJSONObject(i).getString("stream_id");
            String file_size = jsonObject2.getJSONObject(i).getString("file_size");
            String start_time = jsonObject2.getJSONObject(i).getString("start_time");
            String end_time = jsonObject2.getJSONObject(i).getString("end_time");
            String record_file_url = jsonObject2.getJSONObject(i).getString("record_file_url");

            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("room_id", room_id);
            map.put("file_size", file_size);
            map.put("start_time", start_time);
            map.put("end_time", end_time);
            map.put("record_file_url", record_file_url);
            list.add(map);
        }

        return list;
    }

    /**
     * 获取腾讯直播列表
     *
     * @return
     */
    public static List<Map<String, String>> getAllRoomList() {

        List<Map<String, String>> list = new ArrayList<>();
        String json = HttpRequestUtil.sendGet(getLiveListUrl());
       logger.info("查询所有房间信息--->"+json);
        JSONObject jsonObject = JSON.parseObject(json).getJSONObject("output");
        JSONArray jsonObject2 = jsonObject.getJSONArray("channel_list");
        for (int i = 0; i < jsonObject2.size(); i++) {
            String id = jsonObject2.getJSONObject(i).getString("channel_id");
            String status = jsonObject2.getJSONObject(i).getString("channel_status");
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("status", status);
            list.add(map);
        }
        return list;
    }

    /**
     * 开启录制任务,拼接录制url
     *
     * @return
     */
    public static String getStartRecordUrl(String id) {
        Long current = (System.currentTimeMillis() + 60 * 1000) / 1000;
        String startTime = URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String endTime = URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime() + 1000 * 60 * 60 * 2)));

        String sign = MD5Encode.stringToMD5(new StringBuffer().append(API_KEY).append(current).toString());

        String params = new StringBuffer().append("&interface=Live_Tape_Start")
                .append("&Param.s.channel_id=" + bizid + "_" + id)
                .append("&Param.s.start_time=" + startTime).append("&Param.s.end_time=" + endTime)
                .append("&Param.n.task_sub_type=1")
                .append("&t=").append(current).append("&sign=").append(sign).toString();
        // 拼接URL
        String url = API_ADDRESS + "?appid=" + APPID + params;
        return url;
    }

    /**
     * 关闭录制任务,拼接关闭录制url
     *
     * @return
     */
    public static String getCloseRecordUrl(String id, String taskId) {
        Long current = (System.currentTimeMillis() + 60 * 60 * 1000) / 1000;
        String sign = MD5Encode.stringToMD5(new StringBuffer().append(API_KEY).append(current).toString());
        String params = new StringBuffer().append("&interface=Live_Tape_Stop")
                .append("&Param.s.channel_id=" + bizid + "_" + id)
                .append("&Param.n.task_id=" + taskId)
                .append("&t=").append(current).append("&sign=").append(sign).toString();
        // 拼接URL
        String url = API_ADDRESS + "?appid=" + APPID + params;
        return url;
    }

    /**
     * 开启录制任务
     *
     * @return
     */
    public static String openRecordTask(String id) {
        String jsonInfo = HttpRequestUtil.sendGet(getStartRecordUrl(id));
        return jsonInfo;
    }

    /**
     * 关闭录制任务
     *
     * @return
     */
    public static String closeRecordTask(String id, String taskId) {
        String jsonInfo = HttpRequestUtil.sendGet(getCloseRecordUrl(id, taskId));
        return jsonInfo;
    }


    public static void main(String[] args) {
//        String url = getRecordAllUrl();
//        System.out.println(url);
//        String json = HttpRequestUtil.sendGet(url);
//        System.out.println(json);
//        List list = getAllRoomUrl();
//        System.out.println(list);
//        System.out.println("关闭该房间->>>" + getCloseLiveUrl("ca5d0df8f6"));

//        System.out.println(createLiveTape("572d75d433"));
//      System.out.println(closeLiveTape("572d75d433","1220683794"));

        //获取推流url（创建一个直播房间）
        //   System.out.println(getPushUrl("myLive"));
        //获取指定id录制url(查询云端存储录制视屏信息接口url)
        // System.out.println(getRecordUrl("572d75d433"));
        //获取录制列表
        //  System.out.println(getRecordList("572d75d433"));
        //获取直播列表
        //System.out.println(getAllRoomList());

    }

}
