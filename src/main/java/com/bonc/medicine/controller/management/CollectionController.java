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
    * 收藏夹—商品列表搜索
    * */
    @GetMapping("/searchSupplyByCollect")
    public Result<Object> searchSupplyByCollect(){
        return ResultUtil.success(collectionService.searchSupplyByCollect());
    }


    /*
    * 收藏夹—视频列表搜索
    * */
    @GetMapping("/searchVideoByCollect")
    public Result<Object> searchVideoByCollect(){
        return ResultUtil.success(collectionService.searchVideoByCollect());
    }





}
