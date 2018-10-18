package com.bonc.medicine.controller.management;

import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.hbase.HbaseUploadFile;
import com.bonc.medicine.service.management.BroadcastService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/29.
 */
@RestController
@RequestMapping("/manage")
public class BroadcastController {

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private HbaseUploadFile uploadFile;

    /*
    * 运营管理-轮播图管理，查询搜索、编辑访问接口
    * */
    
    @PostMapping("/broadcast")
    public Result<Object> selectBroadcast(@RequestBody(required = false) String searchJson){
        if(null == searchJson || "" == searchJson){
            searchJson = "{ \"id\":\"\",\"search_name \":\"\", \"site\":\"\", \"start_time\":\"\", \"end_time\":\"\" }";
        }
        Map map = JacksonMapper.INSTANCE.readJsonToMap(searchJson);
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        long total = 0L;
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        list = broadcastService.searchBroadcast(map);
        if (pageNum != null && pageSize != null) {
            total =  list == null ? 0L : ((Page<Map<String,Object>>)list).getTotal();
        }
        return ResultUtil.successTotal(list, total);
    }


    /*
    * 新增轮播图
    * */
    @MethodLog(remark = "新增,新增轮播图,运营管理")
    @PostMapping("/addBroadcast")
    public Result<Object> addBroadcast(/*@RequestBody MultipartFile myfile,*/ @RequestBody String addJson) throws Exception{
//        String key = uploadFile.uploadFileToHbase(myfile);
        Map map = JacksonMapper.INSTANCE.readJsonToMap(addJson);
//        map.put("img_url",key);
        return ResultUtil.success(broadcastService.addBroadcast(map));
    }

    /*
    * 修改轮播图
    * */
    @MethodLog(remark = "修改,修改轮播图,运营管理")
    @PostMapping("/editBroadcast")
    public Result<Object> editBroadcast(/*@RequestBody MultipartFile myfile, */@RequestBody String editJson) throws Exception{
//        String key = uploadFile.uploadFileToHbase(myfile);
        Map map = JacksonMapper.INSTANCE.readJsonToMap(editJson);
//        map.put("img_url",key);
        return ResultUtil.success(broadcastService.editBroadcast(map));
    }

    /*
    * 停用轮播图
    * */
    @GetMapping("/stopBroadcast/{id}")
    public Result<Object> stopBroadcast(@PathVariable String id){
        return ResultUtil.success(broadcastService.stopBroadcast(id));
    }

    /*
    * 启用轮播图
    * */
    @GetMapping("/enableBroadcast/{id}")
    public Result<Object> enableBroadcast(@PathVariable String id){
        return ResultUtil.success(broadcastService.enableBroadcast(id));
    }

    /*
    * 删除轮播图
    * */
    @MethodLog(remark = "删除,删除轮播图,运营管理")
    @GetMapping("/deleteBroadcast/{id}")
    public Result<Object> deleteBroadcast(@PathVariable String id){
        return ResultUtil.success(broadcastService.deleteBroadcast(id));
    }

    /*
    * 轮播图详情
    * */
    @GetMapping("/showBroadcastById/{id}")
    public Result<Object> showBroadcastById(@PathVariable String id){
        return ResultUtil.success(broadcastService.showBroadcastById(id));
    }



}
