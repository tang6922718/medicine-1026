package com.bonc.medicine.controller.mall;


import com.bonc.medicine.annotation.Authorization;
import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Dyanimic;
import com.bonc.medicine.service.mall.CommentReplyService;
import com.bonc.medicine.service.mall.DyanimicService;
import com.bonc.medicine.service.thumb.AttentionService;
import com.bonc.medicine.service.thumb.ThumbService;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/dyanimic")
public class DyanimicController {

    @Autowired
    DyanimicService dyanimicService;
    @Autowired
    ThumbService thumbService;  //点赞
    @Autowired
    ViewNumberService viewNumberService; // 浏览
    @Autowired
    CommentReplyService commentReplyService; // 回复
    @Autowired
    AttentionService attentionService; //关注


    //新增一条动态
    @SuppressWarnings("unchecked")
    @PostMapping("/insert")
    public Result<Object> insertDyanimic(@RequestBody Map map, @CurrentUser String userid){
        Dyanimic dyanimic = new Dyanimic();
        dyanimic.setDyn_cat_id((int)map.get("dyn_cat_id"));
        dyanimic.setDesciption((String)map.get("desciption"));
        dyanimic.setImg_url((String)map.get("img_url"));
        dyanimic.setVideo_url((String)map.get("video_url"));
        dyanimic.setTelephone((String)map.get("telephone"));
//        dyanimic.setPublish_user_id((int)map.get("publish_user_id")); // 发布者id即当前用户id，其实该由后台获取
        int publish_user_id = Integer.parseInt(userid);
        dyanimic.setPublish_user_id(publish_user_id);
        System.out.println(dyanimic);
        return ResultUtil.success(dyanimicService.insertDyanimic(dyanimic));}

    // 查询所有人动态 APP
    @SuppressWarnings("unchecked")
    @GetMapping("/select/dyanimic")
    public Result<Object> selectAllDyanimic(int dyn_cat_id,String publish_time,@CurrentUser String user_id) {
        List returnList = new ArrayList();
        List<Map> list = dyanimicService.selectAllDyanimic(dyn_cat_id,publish_time);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();

                Map amap = list.get(i);
                int dyanimicId = (int)amap.get("id");
                // 此处引用 点赞 的接口
                Map<String, String> param = new HashMap<>();
                param.put("type", "0");
                param.put("acceptThumbId", dyanimicId+"");
                Map<String, Object> dianzan = thumbService.thumbNumber(param);
                Object thumbNumber = dianzan.get("thumbNumber");
                // 此处引用 浏览数 的接口
                param.clear();
                param.put("objectType", "0");
                param.put("objectId", dyanimicId+"");
                Map<String, Object> liulan = viewNumberService.queryViewNumber(param);
                Object viewNumber = liulan.get("viewNumber");
                // 此处引用 回复消息数 的接口
                param.clear();
                param.put("object_type", "2");
                param.put("object_id", dyanimicId+"");
                int messageNumber = commentReplyService.commentsCount(param);

                //此处引用 当前用户是否关注 动态发布者
                param.clear();
                int attedUserId = (int) amap.get("publish_user_id");

                param.put("userId", user_id);
                param.put("attedUserId", attedUserId+"");
                param.put("type", "0");
                Map attention = attentionService.attentionRelation(param);
                Object attentionTF = attention.get("followed");

               // 此处引用 当前用户是否对该条动态点赞
                int thumbStatus = thumbService.thumbStatus(user_id, "0", dyanimicId+"");

                map.put("dyanimic",amap);
                map.put("thumb",thumbNumber);
                map.put("message",messageNumber);
                map.put("view",viewNumber);
                map.put("attentionStatus",attentionTF); //1-关注 0-未关注
                map.put("thumbStatus",thumbStatus); //1：点赞 0： 未点赞 ;-999:参数不全
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }


    // 查询所有人动态  后台管理
    @SuppressWarnings("unchecked")
    @GetMapping("/select/dyanimicBackstage")
    public Result<Object> dyanimicBackstage(int dyn_cat_id,String publish_time) {
        List returnList = new ArrayList();
        List<Map> list = dyanimicService.selectAllDyanimic(dyn_cat_id,publish_time);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();

                Map amap = list.get(i);
                int dyanimicId = (int)amap.get("id");
                // 此处引用 点赞 的接口
                Map<String, String> param = new HashMap<>();
                param.put("type", "0");
                param.put("acceptThumbId", dyanimicId+"");
                Map<String, Object> dianzan = thumbService.thumbNumber(param);
                Object thumbNumber = dianzan.get("thumbNumber");
                // 此处引用 浏览数 的接口
                param.clear();
                param.put("objectType", "0");
                param.put("objectId", dyanimicId+"");
                Map<String, Object> liulan = viewNumberService.queryViewNumber(param);
                Object viewNumber = liulan.get("viewNumber");
                // 此处引用 回复消息数 的接口
                param.clear();
                param.put("object_type", "2");
                param.put("object_id", dyanimicId+"");
                int messageNumber = commentReplyService.commentsCount(param);

                map.put("dyanimic",amap);
                map.put("thumb",thumbNumber);
                map.put("message",messageNumber);
                map.put("view",viewNumber);
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }



    // 查询某一用户(不是当前用户)发布的动态  app
    @SuppressWarnings("unchecked")
    @GetMapping("/select/user")
    public Result<Object> selectUserDyanimic(int publish_user_id,int dyn_cat_id,@CurrentUser String user_id) {
        List returnList = new ArrayList();
        List<Map> list = dyanimicService.selectUserDyanimic( publish_user_id, dyn_cat_id);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();

                Map amap = list.get(i);
                int dyanimicId = (int)amap.get("id");
                // 此处引用 点赞 的接口
                Map<String, String> param = new HashMap<>();
                param.put("type", "0");
                param.put("acceptThumbId", dyanimicId+"");
                Map<String, Object> dianzan = thumbService.thumbNumber(param);
                Object thumbNumber = dianzan.get("thumbNumber");
                // 此处引用 浏览数 的接口
                param.clear();
                param.put("objectType", "0");
                param.put("objectId", dyanimicId+"");
                Map<String, Object> liulan = viewNumberService.queryViewNumber(param);
                Object viewNumber = liulan.get("viewNumber");
                // 此处引用 回复消息数 的接口
                param.clear();
                param.put("object_type", "2");
                param.put("object_id", dyanimicId+"");
                int messageNumber = commentReplyService.commentsCount(param);

                //此处引用 当前用户是否关注 动态发布者
                param.clear();
                int attedUserId = (int) amap.get("publish_user_id");

                param.put("userId", user_id);
                param.put("attedUserId", attedUserId+"");
                param.put("type", "0");
                Map attention = attentionService.attentionRelation(param);
                Object attentionTF = attention.get("followed");

                // 此处引用 当前用户是否对该条动态点赞
                int thumbStatus = thumbService.thumbStatus(user_id, "0", attedUserId+"");

                map.put("dyanimic",amap);
                map.put("thumb",thumbNumber);
                map.put("message",messageNumber);
                map.put("view",viewNumber);
                map.put("attentionStatus",attentionTF); //1-关注 0-未关注
                map.put("thumbStatus",thumbStatus); //1：点赞 0： 未点赞 ;-999:参数不全
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }



    // 查询当前用户发布的动态
    @SuppressWarnings("unchecked")
    @GetMapping("/select/mine")
    public Result<Object> selectMineDyanimic(int dyn_cat_id,@CurrentUser String user_id) {
        List returnList = new ArrayList();
        int publish_user_id = Integer.parseInt(user_id); // publish_user_id 是 当前用户id
        List<Map> list = dyanimicService.selectUserDyanimic( publish_user_id, dyn_cat_id);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();

                Map amap = list.get(i);
                int dyanimicId = (int)amap.get("id");
                // 此处引用 点赞 的接口
                Map<String, String> param = new HashMap<>();
                param.put("type", "0");
                param.put("acceptThumbId", dyanimicId+"");
                Map<String, Object> dianzan = thumbService.thumbNumber(param);
                Object thumbNumber = dianzan.get("thumbNumber");
                // 此处引用 浏览数 的接口
                param.clear();
                param.put("objectType", "0");
                param.put("objectId", dyanimicId+"");
                Map<String, Object> liulan = viewNumberService.queryViewNumber(param);
                Object viewNumber = liulan.get("viewNumber");
                // 此处引用 回复消息数 的接口
                param.clear();
                param.put("object_type", "2");
                param.put("object_id", dyanimicId+"");
                int messageNumber = commentReplyService.commentsCount(param);

                //此处引用 当前用户是否关注 动态发布者,因为是自己，所以不存在是否关注
                param.clear();
                int attedUserId = (int) amap.get("publish_user_id");
                param.put("userId", user_id);
                param.put("attedUserId", attedUserId+"");
                param.put("type", "0");
                Map attention = attentionService.attentionRelation(param);
                Object attentionTF = attention.get("followed");

                // 此处引用 当前用户是否对该条动态点赞
                int thumbStatus = thumbService.thumbStatus(user_id, "0", attedUserId+"");

                map.put("dyanimic",amap);
                map.put("thumb",thumbNumber);
                map.put("message",messageNumber);
                map.put("view",viewNumber);
                map.put("attentionStatus",attentionTF); //1-关注 0-未关注
                map.put("thumbStatus",thumbStatus); //1：点赞 0： 未点赞 ;-999:参数不全
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }


    // 查询当前用户参与过（不包含发布）的动态
    @SuppressWarnings("unchecked")
    @GetMapping("/select/mineJoin")
    public Result<Object> selectMineJoinDyanimic(@CurrentUser String userid) {
        List returnList = new ArrayList();
        int user_id = Integer.parseInt(userid);
        List<Map> list = dyanimicService.selectJoinDyanimic(user_id);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();

                Map amap = list.get(i);
                int dyanimicId = (int)amap.get("id");
                // 此处引用 点赞 的接口
                Map<String, String> param = new HashMap<>();
                param.put("type", "0");
                param.put("acceptThumbId", dyanimicId+"");
                Map<String, Object> dianzan = thumbService.thumbNumber(param);
                Object thumbNumber = dianzan.get("thumbNumber");
                // 此处引用 浏览数 的接口
                param.clear();
                param.put("objectType", "0");
                param.put("objectId", dyanimicId+"");
                Map<String, Object> liulan = viewNumberService.queryViewNumber(param);
                Object viewNumber = liulan.get("viewNumber");
                // 此处引用 回复消息数 的接口
                param.clear();
                param.put("object_type", "2");
                param.put("object_id", dyanimicId+"");
                int messageNumber = commentReplyService.commentsCount(param);

                //此处引用 当前用户是否关注 动态发布者
                param.clear();
                int attedUserId = (int) amap.get("publish_user_id");

                param.put("userId", userid);
                param.put("attedUserId", attedUserId+"");
                param.put("type", "0");
                Map attention = attentionService.attentionRelation(param);
                Object attentionTF = attention.get("followed");

                // 此处引用 当前用户是否对该条动态点赞
                int thumbStatus = thumbService.thumbStatus(userid, "0", attedUserId+"");

                map.put("dyanimic",amap);
                map.put("thumb",thumbNumber);
                map.put("message",messageNumber);
                map.put("view",viewNumber);
                map.put("attentionStatus",attentionTF); //1-关注 0-未关注
                map.put("thumbStatus",thumbStatus); //1：点赞 0： 未点赞 ;-999:参数不全
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }



    // 查询具体的一条动态
    @SuppressWarnings("unchecked")
    @GetMapping("/select/detailOne")
    public Result<Object> selectDetailOneDyanimic(int id,@CurrentUser String user_id) {
        System.out.println(id);
        System.out.println("userid:"+user_id);
        List returnList = new ArrayList();
        List<Map> list = dyanimicService.selectDetailOneDyanimic(id);  //查询出为一条数据，还是返回的list
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();

                Map amap = list.get(i);
                int dyanimicId = (int)amap.get("id");
                // 此处引用 点赞 的接口
                Map<String, String> param = new HashMap<>();
                param.put("type", "0");
                param.put("acceptThumbId", dyanimicId+"");
                Map<String, Object> dianzan = thumbService.thumbNumber(param);
                Object thumbNumber = dianzan.get("thumbNumber");
                // 此处引用 浏览数 的接口
                param.clear();
                param.put("objectType", "0");
                param.put("objectId", dyanimicId+"");
                Map<String, Object> liulan = viewNumberService.queryViewNumber(param);
                Object viewNumber = liulan.get("viewNumber");
                // 此处引用 回复消息数 的接口
                param.clear();
                param.put("object_type", "2");
                param.put("object_id", dyanimicId+"");
                int messageNumber = commentReplyService.commentsCount(param);

                //此处引用 当前用户是否关注 动态发布者
                param.clear();
                int attedUserId = (int) amap.get("publish_user_id");


                param.put("userId", user_id);
                param.put("attedUserId", attedUserId+"");
                param.put("type", "0");
                Map attention = attentionService.attentionRelation(param);
                Object attentionTF = attention.get("followed");

                // 此处引用 当前用户是否对该条动态点赞
                int thumbStatus = thumbService.thumbStatus(user_id, "0", attedUserId+"");

                map.put("dyanimic",amap);
                map.put("thumb",thumbNumber);
                map.put("message",messageNumber);
                map.put("view",viewNumber);
                map.put("attentionStatus",attentionTF); //1-关注 0-未关注
                map.put("thumbStatus",thumbStatus); //1：点赞 0： 未点赞 ;-999:参数不全
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }


    // 查询具体的一条动态  后台管理
    @SuppressWarnings("unchecked")
    @GetMapping("/select/detailOneBackstage")
    public Result<Object> selectDetailOneBackstageDyanimic(int id) {
        List returnList = new ArrayList();
        List<Map> list = dyanimicService.selectDetailOneDyanimic(id);  //查询出为一条数据，还是返回的list
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();

                Map amap = list.get(i);
                int dyanimicId = (int)amap.get("id");
                // 此处引用 点赞 的接口
                Map<String, String> param = new HashMap<>();
                param.put("type", "0");
                param.put("acceptThumbId", dyanimicId+"");
                Map<String, Object> dianzan = thumbService.thumbNumber(param);
                Object thumbNumber = dianzan.get("thumbNumber");
                // 此处引用 浏览数 的接口
                param.clear();
                param.put("objectType", "0");
                param.put("objectId", dyanimicId+"");
                Map<String, Object> liulan = viewNumberService.queryViewNumber(param);
                Object viewNumber = liulan.get("viewNumber");
                // 此处引用 回复消息数 的接口
                param.clear();
                param.put("object_type", "2");
                param.put("object_id", dyanimicId+"");
                int messageNumber = commentReplyService.commentsCount(param);

                map.put("dyanimic",amap);
                map.put("thumb",thumbNumber);
                map.put("message",messageNumber);
                map.put("view",viewNumber);
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }

    // 删除某一条动态
    @SuppressWarnings("unchecked")
    @DeleteMapping("/delete/oneDyanimic")
    public Result<Object> delOneDyanimic(int id){

        int del = dyanimicService.delOneDyanimic(id);
        return  ResultUtil.success(del);
    }


    // 查询动态分类
    @SuppressWarnings("unchecked")
    @GetMapping("/select/DyanimicCategory")
    public Result<Object> selectDyanimicCategory() {
        List<Map> list = dyanimicService.selectDyanimicCategory();
        return ResultUtil.success(list);
    }


    // 查询待审核的动态
    @SuppressWarnings("unchecked")
    @GetMapping("/select/uncheckDyanimic")
    public Result<Object> selectUncheckDyanimic(int dyn_cat_id,String publish_time) {
        List<Map> list = dyanimicService.selectUncheckDyanimic( dyn_cat_id, publish_time);
        return ResultUtil.success(list);
    }

    /*审核 动态，批准0或不批准1*/
    @SuppressWarnings("unchecked")
    @GetMapping("/update/dyanimic")
    public Result<Object> updateOneDyanimic(char effect_flag, String fail_opinion,int id) {
        int update = dyanimicService.updateOneDyanimic(effect_flag,fail_opinion,id);
        return ResultUtil.success(update);
    }



}