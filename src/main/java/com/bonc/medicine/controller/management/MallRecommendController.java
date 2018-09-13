package com.bonc.medicine.controller.management;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.management.MallRecommendService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@RestController
@RequestMapping("/recommend")
public class MallRecommendController {

    @Autowired
    private MallRecommendService mallRecommendService;


    /*
    * 运营管理-商品推荐，查询搜索、编辑访问接口
    * */
    @PostMapping("/searchRecommend")
    public Result<Object> searchMallRecommend(@RequestBody(required = false) String searchJson){
        if(null == searchJson || "" == searchJson){
            searchJson = "{ \"id \":\"\",\"search_name \":\"\", \"site\":\"\", \"start_time\":\"\", \"end_time\":\"\" }";
        }
        Map map = JacksonMapper.INSTANCE.readJsonToMap(searchJson);
        return ResultUtil.success(mallRecommendService.searchMallRecommend(map));
    }

    /*
    * 停用对应商品推荐
    * */
    @GetMapping("/stopMallRecommend/{id}")
    public Result<Object> stopMallRecommend(@PathVariable String id){
        return ResultUtil.success(mallRecommendService.stopMallRecommend(id));
    }

    /*
    * 启用对应商品推荐
    * */
    @GetMapping("/enableMallRecommend/{id}")
    public Result<Object> enableMallRecommend(@PathVariable String id){
        return ResultUtil.success(mallRecommendService.enableMallRecommend(id));
    }

    /*
    * 删除对应商品推荐
    * */
    @GetMapping("/deleteMallRecommend/{id}")
    public Result<Object> deleteMallRecommend(@PathVariable String id){
        return ResultUtil.success(mallRecommendService.deleteMallRecommend(id));
    }

}
