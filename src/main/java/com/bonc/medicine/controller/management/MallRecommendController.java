package com.bonc.medicine.controller.management;

import com.bonc.medicine.annotation.MethodLog;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.management.MallRecommendService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
            searchJson = "{ \"id \":\"\",\"search_name\":\"\", \"site\":\"\", \"start_time\":\"\", \"end_time\":\"\" }";
        }
        Map map = JacksonMapper.INSTANCE.readJsonToMap(searchJson);

        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        long total = 0L;
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        list = mallRecommendService.searchMallRecommend(map);
        if (pageNum != null && pageSize != null) {
            total =  list == null ? 0L : ((Page<Map<String,Object>>)list).getTotal();
        }
        return ResultUtil.successTotal(list, total);
    }

    /**
     * 商品推荐详情
     * @param id 推荐编号
     * @return
     */
    @GetMapping("/MallRecommendDetail/{id}")
    public Result<Object> MallRecommendDetail(@PathVariable String id){
        return ResultUtil.success(mallRecommendService.MallRecommendDetail(id));
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
    @MethodLog(remark = "删除,删除推荐的商品,商品推荐")
    @GetMapping("/deleteMallRecommend/{id}")
    public Result<Object> deleteMallRecommend(@PathVariable String id){
        return ResultUtil.success(mallRecommendService.deleteMallRecommend(id));
    }

    /**
     *新建商品推荐
     * @param addJson  {"site":"4","supply_id1":10,"img_url1":"/img/url111","supply_id2":11,"img_url2":"/img/url111","supply_id3":12,"img_url3":"/img/url111","state":"0"}
     * @return
     */
    @MethodLog(remark = "新增,新增商品推荐,商品推荐")
    @PostMapping("/mallRecommend")
    public Result<Object> mallRecommend(@RequestBody String addJson){
        Map map = JacksonMapper.INSTANCE.readJsonToMap(addJson);
//        List list = (List) map.get("addJson");
        return ResultUtil.success(mallRecommendService.mallRecommend(map));
    }


    /**
     * 修改商品推荐
     * @param editJson {"id":14,"site":"2","supply_id1":10,"img_url1":"/img/url111","supply_id2":11,"img_url2":"/img/url111","supply_id3":12,"img_url3":"/img/url111","state":"0"}
     * @return
     */
    @MethodLog(remark = "修改,修改商品推荐,商品推荐")
    @PostMapping("/editMallRecommend")
    public Result<Object> editMallRecommend(@RequestBody String editJson){
        Map map = JacksonMapper.INSTANCE.readJsonToMap(editJson);
        return ResultUtil.success(mallRecommendService.editMallRecommend(map));
    }

    /**
     * 商品推荐详情
     * @param id
     * @return {"code":200,"msg":"成功","data":{"supply_id":1,"upload_time":1537176130000,"site":"2","img_url":"/img/url111","id":1,"state":"0"}}
     */
    @GetMapping("/showMallRecommend/{id}")
    public Result<Object> showMallRecommend(@PathVariable String id){
        return ResultUtil.success(mallRecommendService.showMallRecommend(id));
    }


    /**
     * 根据供应编号显示 商品提供用户 商品名称 商品简介 图片
     * 只显示审核通过，上架，可用的商品
     * @param id
     * @return {"code":200,"msg":"成功","data":{"goods_name":"枸杞","img_url":"https://pro.modao.cc/uploads3/images/2060/20603852/raw_1527144746.png","id":1,"detail":"好药无价，谁吃谁知道！","linkman":"彭XX"}}
     */
    @GetMapping("/showGoodsById/{id}")
    public Result<Object> showGoodsById(@PathVariable String id,@RequestParam (required = false) String site){
        return ResultUtil.success(mallRecommendService.showGoodsById(id,site));
    }

    /**
     *精品推荐
     * @param sites
     * @return
     */
    @GetMapping("/qualityRecommend")
    public Result<Object> qualityRecommend(@RequestParam String... sites){
        return ResultUtil.success(mallRecommendService.qualityRecommend(sites));
    }


}
