package com.bonc.medicine.controller.information;


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
    public Result infoList(@RequestParam int catCode) {
        return  ResultUtil.success(infoService.getAllInfo(catCode));
    }

    /**
     * @description 添加咨询
     * @param map
     * @return
     */
    @PostMapping("/addInfo")
    public Result addInfo(@RequestBody Map<String,Object>  map) {
        return  ResultUtil.success(infoService.addInfo(map));
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
     * @param map
     * @return
     */
    @RequestMapping("/infoDetail")
    public Result infoDetail(@RequestBody Map<String,Object>  map) {
            String id = (String) map.get("id");
            return  ResultUtil.success(infoService.infoDetailById(id));
    }

    /**
     * @description 咨讯编辑
     * @param map
     * @return
     */
    @RequestMapping("/infoEdit")
    public Result infoEdit (@RequestBody Map<String,Object>  map) {
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
     * @description 资讯浏览数
     * @param map
     * @return
     */
    @RequestMapping("/delInfo")
    public Result delInfo (@RequestBody Map<String,Object>  map) {
        return  ResultUtil.success(infoService.delInfo(map));
    }

}
