package com.bonc.medicine.controller.information;


import com.bonc.medicine.annotation.CurrentUser;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.information.InfoService;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class InfoController {

    @Autowired
    InfoService infoService;

    /**
     * @description 查询咨讯列表
     * @param catCode
     * @return
     */


    @RequestMapping("/infoList")
    public Result infoList(@RequestParam(required = false) String catCode,
                           @RequestParam(required = false,defaultValue = "1") String pageNum,
                           @RequestParam(required = false, defaultValue = "10") String pageSize) {

        return  ResultUtil.success(infoService.getAllInfo(catCode,pageNum,pageSize));
    }

    /**
     * @description 添加咨询
     * @param map
     * @return
     */
    @PostMapping("/addInfo")
    @com.bonc.medicine.annotation.Authorization
    public Result addInfo(@CurrentUser String user_id,@RequestBody Map<String,Object>  map) {
        map.put("user_id",user_id);
        return  ResultUtil.success(infoService.addInfo(map));
    }


    /**
     * @description 咨讯撤销
     * @param id
     * @return
     */
    @RequestMapping("/infoRepeal")
    public Result infoRepeal(@RequestParam String  id) {
        return  ResultUtil.success(infoService.infoRepeal(id));
    }




    /**
     * @description 咨讯审核
     * @param map
     * @return
     */
    @RequestMapping("/infoAudit")
    public Result infoAudit(@RequestBody Map<String,Object>  map) {
        return  ResultUtil.success(infoService.infoAudit(map));
    }

    /**
     * @description 咨讯详情
     * @param id
     * @return
     */
    @RequestMapping("/infoDetail")
    public Result infoDetail(@RequestParam String  id) {
            return  ResultUtil.success(infoService.infoDetailById(id));
    }




    /**
     * @description 咨讯编辑
     * @param map
     * @return
     */
    @RequestMapping("/infoEdit")
    @com.bonc.medicine.annotation.Authorization
    public Result infoEdit (@CurrentUser String user_id,@RequestBody Map<String,Object>  map) {
        map.putIfAbsent("user_id",user_id);
        return  ResultUtil.success(infoService.infoEditById(map));
    }

    /**
     * @description 相关咨讯
     * @param map
     * @return
     */
    @RequestMapping("/infoClass")
    public Result infoClass (@RequestBody Map<String,Object>  map) {
        return  ResultUtil.success(infoService.infoClass(map));
    }

    /**
     * @description 资讯浏览数
     * @param map
     * @return
     */
    @RequestMapping("/infoReadCount")
    public Result infoReadCount (@RequestBody Map<String,Object>  map) {
        return  ResultUtil.success(infoService.infoReadCount(map));
    }

    /**
     * @description 删除资讯
     * @param map
     * @return
     */
    @RequestMapping("/delInfo")
    public Result delInfo (@RequestBody Map<String,Object>  map) {
        return  ResultUtil.success(infoService.delInfo(map));
    }

}
