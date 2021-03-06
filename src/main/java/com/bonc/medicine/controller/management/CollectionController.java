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
    public Result<Object> searchInfoByCollect(@RequestParam String user_id){
        return ResultUtil.success(collectionService.searchInfoByCollect(user_id));
    }


    /*
    * 收藏夹—资讯搜索详情
    * */
    @GetMapping("/infoBasicDetail/{id}")
    public Result<Object> infoBasicDetail(@PathVariable String id){
        return ResultUtil.success(collectionService.infoBasicDetail(id));
    }

    /*
    * 收藏夹—商品列表搜索,供应列表
    * */
    @GetMapping("/searchSupplyByCollect")
    public Result<Object> searchSupplyByCollect(@RequestParam String user_id){
        return ResultUtil.success(collectionService.searchSupplyByCollect(user_id));
    }

    /*
    * 收藏夹—商品列表搜索,求购列表
    * */
    @GetMapping("/searchPurchaseByCollect")
    public Result<Object> searchPurchaseByCollect(@RequestParam String user_id){
        return ResultUtil.success(collectionService.searchPurchaseByCollect(user_id));
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
    public Result<Object> searchVideoByCollect(@RequestParam String user_id){
        return ResultUtil.success(collectionService.searchVideoByCollect(user_id));
    }

    /*
    * 收藏夹—视频搜索详情
    * (知识库：视频分类详情一致)
    * */
    @GetMapping("/videoCourseDetail/{id}")
    public Result<Object> videoCourseDetail(@PathVariable String id){
        return ResultUtil.success(collectionService.videoCourseDetail(id));
    }
    
    /*
     * 收藏夹—线下培训搜索详情
     * (知识库：线下培训详情一致)
     * */
     @GetMapping("/trainOfflineDetail/{id}")
     public Result<Object> trainOfflineDetail(@PathVariable String id){
         return ResultUtil.success(collectionService.trainOfflineDetail(id));
     }
     
     /*
      * 收藏夹—直播课堂搜索详情
      * (知识库：直播课堂详情一致)
      * */
      @GetMapping("/trainLiveDetail/{id}")
      public Result<Object> trainLiveDetail(@PathVariable String id){
          return ResultUtil.success(collectionService.trainLiveDetail(id));
      }


    /*
    *
    * 案例详情
    * */
    @GetMapping("/specCaseDetail/{id}")
    public Result<Object> specCaseDetail(@PathVariable String id){
        return ResultUtil.success(collectionService.specCaseDetail(id));
    }

    /**
     * 是否收藏
     * @param collect_type 收藏类型
     * @param collect_object_id 收藏类型下目标的id
     * @return
     */
    @GetMapping("/isCollect/{collect_type}/{collect_object_id}/{user_id}")
    public Result<Object> isCollect(@PathVariable String collect_type,@PathVariable String collect_object_id,@PathVariable String user_id){
        return ResultUtil.success(collectionService.isCollect(collect_type,collect_object_id,user_id));
    }

    /**
     * 取消收藏
     * @param collect_type
     * @param collect_object_id
     * @param user_id
     * @return
     */
    @GetMapping("/undoCollect/{collect_type}/{collect_object_id}/{user_id}")
    public Result<Object> undoCollect(@PathVariable String collect_type,@PathVariable String collect_object_id,@PathVariable String user_id){
        return ResultUtil.success(collectionService.undoCollect(collect_type,collect_object_id,user_id));
    }

    /**
     * 收藏数
     * @param collect_type
     * @param collect_object_id
     * @return
     */
    @GetMapping("/collectCount/{collect_type}/{collect_object_id}")
    public Result<Object> collectCount(@PathVariable String collect_type,@PathVariable String collect_object_id){
        return ResultUtil.success(collectionService.collectCount(collect_type,collect_object_id));
    }

}
