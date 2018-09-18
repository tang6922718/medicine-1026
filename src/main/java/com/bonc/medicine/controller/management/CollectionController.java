package com.bonc.medicine.controller.management;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.management.CollectionService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2018/9/6.
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /*
    * 用户收藏
    * */
    @PostMapping("/collect")
    public Result<Object> collect(@RequestBody String collectJson){
        Map map = JacksonMapper.INSTANCE.readJsonToMap(collectJson);
        return ResultUtil.success(collectionService.collect(map));
    }

    /*
    * 收藏夹—资讯列表搜索
    * */
    @GetMapping("/searchInfoByCollect")
    public Result<Object> searchInfoByCollect(){
        return ResultUtil.success(collectionService.searchInfoByCollect());
    }


    /*
    * 收藏夹—资讯搜索详情
    * */
    @GetMapping("/infoBasicDetail/{id}")
    public Result<Object> infoBasicDetail(@PathVariable String id){
        return ResultUtil.success(collectionService.infoBasicDetail(id));
    }

    /*
    * 收藏夹—商品列表搜索,供应和求购列表
    * */
    @GetMapping("/searchSupplyByCollect")
    public Result<Object> searchSupplyByCollect(){
        return ResultUtil.success(collectionService.searchSupplyByCollect());
    }

    /*
    * 收藏夹—供应详情
    * */
    @GetMapping("/mallSupplyDetail/{id}")
    public Result<Object> mallSupplyDetail(@PathVariable String id){
        return ResultUtil.success(collectionService.mallSupplyDetail(id));
    }


    /*
    * 收藏夹—求购详情
    * */
    @GetMapping("/mallPurchaseDetail/{id}")
    public Result<Object> mallPurchaseDetail(@PathVariable String id){
        return ResultUtil.success(collectionService.mallPurchaseDetail(id));
    }


    /*
    * 收藏夹—视频列表搜索
    * */
    @GetMapping("/searchVideoByCollect")
    public Result<Object> searchVideoByCollect(){
        return ResultUtil.success(collectionService.searchVideoByCollect());
    }

    /*
    * 收藏夹—视频搜索详情
    * (知识库：视频分类详情一致)
    * */
    @GetMapping("/videoCourseDetail/{id}")
    public Result<Object> videoCourseDetail(@PathVariable String id){
        return ResultUtil.success(collectionService.videoCourseDetail(id));
    }








}
