package com.bonc.medicine.controller.mall;


import com.bonc.medicine.entity.Result;
import com.bonc.medicine.entity.mall.Dyanimic;
import com.bonc.medicine.service.mall.DyanimicService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/dyanimic")
public class DyanimicController {

    @Autowired
    DyanimicService dyanimicService;

    @SuppressWarnings("unchecked")
    @PostMapping("/insert")
    public Result<Object> insertDyanimic(Dyanimic dyanimic){return ResultUtil.success(dyanimicService.insertDyanimic(dyanimic));}



    @SuppressWarnings("unchecked")
    @GetMapping("/select/dyanimic")
    public Result<Object> selectDyanimic(int num){  // 查询所有用户的动态
        //前端穿过来的int的对应关系： 全部（1） ，视频（2），供求（3），提问（4），其他（5）

        List returnList = new ArrayList();
        if(num == 1){
            List<Map> list = dyanimicService.selectAllDyanimic();
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }

        }else if(num == 2){
            List<Map> list = dyanimicService.selectOneDyanimic(2);
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        }else if(num == 3){
            List<Map> list = dyanimicService.selectTwoDyanimic();
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        }else if(num == 4){
            List<Map> list = dyanimicService.selectOneDyanimic(5);
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        }else{
            List<Map> list = dyanimicService.selectOneDyanimic(6);
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        }
        return ResultUtil.success(returnList);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/select/user")
    public Result<Object> selectUserDyanimic(int publish_user_id,int num) { // 查询某一用户的动态
        //前端穿过来的int的对应关系： 视频（1），供求（3），提问（2），其他（4）
        List returnList = new ArrayList();
        if (num == 1) {
            List<Map> list = dyanimicService.selectUserOneDyanimic(publish_user_id, 2);
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        } else if (num == 2) {
            List<Map> list = dyanimicService.selectUserOneDyanimic(publish_user_id, 5);
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        } else if (num == 3) {
            List<Map> list = dyanimicService.selectUserTwoDyanimic(publish_user_id);
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        } else {
            List<Map> list = dyanimicService.selectUserOneDyanimic(publish_user_id,6);
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = new HashMap();
                    Map amap = list.get(i);
                    // 此处引用 点赞 的接口
                    // 此处引用 回复消息 的接口
                    map.put("dyanimic",amap);
                    map.put("dianzan",1);
                    map.put("message",1);
                    returnList.add(map);
                }
            }
        }

        return ResultUtil.success(returnList);
    }



    @SuppressWarnings("unchecked")
    @GetMapping("/select/detailOne")
    public Result<Object> selectDetailOneDyanimic(int id) { // 查询某一条具体的动态
        List returnList = new ArrayList();
        List<Map> list = dyanimicService.selectDetailOneDyanimic(id);   //查询出为一条数据，还是返回的list
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                Map amap = list.get(i);
                // 此处引用 点赞 的接口
                // 此处引用 回复消息 的接口
                map.put("dyanimic",amap);
                map.put("dianzan",1);
                map.put("message",1);
                returnList.add(map);
            }
        }
        return ResultUtil.success(returnList);
    }

}