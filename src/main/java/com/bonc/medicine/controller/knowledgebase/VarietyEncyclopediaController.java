package com.bonc.medicine.controller.knowledgebase;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.hbase.HbaseUploadFile;
import com.bonc.medicine.service.knowledgebase.AuditService;
import com.bonc.medicine.service.knowledgebase.PharmacopoeiaInfoService;
import com.bonc.medicine.service.knowledgebase.VarietyEncyclopediaService;
import com.bonc.medicine.utils.JacksonMapper;
import com.bonc.medicine.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrator on 2018/8/29.
 */
@RestController
@RequestMapping("/management")
public class VarietyEncyclopediaController {

    @Autowired
    private VarietyEncyclopediaService varietyEncyclopediaService;

    @Autowired
    private PharmacopoeiaInfoService pharmacopoeiaInfoService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private HbaseUploadFile file;


    @SuppressWarnings("unchecked")
    @PostMapping("/addBreedInfoTTT" )
    @Transactional
    public Result<Object> addVarietyEncyclopedia(@RequestParam String variety_id,@RequestParam(required = false) String variety_alias,@RequestParam(required = false) String property_flavor,@RequestParam(required = false) String product_time
            ,@RequestParam(required = false) String distribution_area,@RequestParam(required = false) String effect,@RequestParam(required = false) String atlas,@RequestParam(required = false) String variety_desc,@RequestParam(required = false) String feature,@RequestParam(required = false) String difference
            ,@RequestParam(required = false) String other_aspect,@RequestParam(required = false) String title){
        Map breedMap = new HashMap();
        Map phamaMap = new HashMap();
        Map auditMap = new HashMap();
        breedMap.put("variety_id",variety_id);
        breedMap.put("variety_alias",variety_alias);
        breedMap.put("property_flavor",property_flavor);
        breedMap.put("product_time",product_time);
        breedMap.put("distribution_area",distribution_area);
        breedMap.put("effect",effect);

        phamaMap.put("atlas",atlas);
        phamaMap.put("cat_code",variety_id);
        phamaMap.put("variety_desc",variety_desc);
        phamaMap.put("feature",feature);
        phamaMap.put("difference",difference);
        phamaMap.put("other_aspect",other_aspect);

        auditMap.put("title",title);
        auditMap.put("km_type",1);
        auditMap.put("object_id",variety_id);
        auditMap.put("publish_time",new Date());
        auditMap.put("status",2);
        int count = varietyEncyclopediaService.addBreed(breedMap);
        count += pharmacopoeiaInfoService.addPharma(phamaMap);
        count += auditService.addAudit(auditMap);
        return ResultUtil.success(count);
    }

    //    @Produces( "application/json;charset=UTF-8" )
//    @Consumes( MediaType.APPLICATION_JSON )
    /*
     * 增加品种百科
     * */
    @PostMapping("/addBreedInfo" )
    @Transactional
    public Result<Object> addVarietyEncyclopedia(@RequestBody String addJson ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map map = JacksonMapper.INSTANCE.readJsonToMap(addJson);
        int count = varietyEncyclopediaService.addBreed(map);
        count += pharmacopoeiaInfoService.addPharma(map);
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }

    /*
     * 修改品种百科
     * */
    @PostMapping("/editBreedInfo")
    @Transactional
    public Result<Object> editVarietyEncyclopedia(@RequestBody String addJson) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map map = JacksonMapper.INSTANCE.readJsonToMap(addJson);
        int count = varietyEncyclopediaService.updateBreedInfo(map);
//        count += pharmacopoeiaInfoService.updatePhara(map);
        auditService.czAudit(map);//撤销原来存入审核表的数据，修改后重新插入。不存在则直接插入
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }
    /*
     * 药典详情*/
    @GetMapping("/pharaDetail/{id}")
    public Result<Object> pharaDetail(@PathVariable String id){
        return ResultUtil.success(pharmacopoeiaInfoService.pharaDetail(id));
    }

    /*
     * 药典修改*/
    @PostMapping("/updetePharaDetail")
    public Result<Object> updatePharaDetail(@RequestBody String editJson){
        Map map = JacksonMapper.INSTANCE.readJsonToMap(editJson);
        map.put("km_status","2");
        map.put("object_id",map.get("id"));
        int count = pharmacopoeiaInfoService.updatePharaDetail(map);
        count += varietyEncyclopediaService.changePhaStatus(map);
        auditService.czAudit(map);
        count += auditService.addAudit(map);
        return ResultUtil.success(count);
    }


    /*
     * 撤销药典*/
    @GetMapping("/undoBreedStatus/{id}")
    public Result<Object> undoBreedStatus(@PathVariable String id){
        return ResultUtil.success(varietyEncyclopediaService.undoBreedStatus(id));
    }

    /*
     * 品种管理查询列表
     * */
    @GetMapping("/breed")
    public Result<Object> selectBreed(@RequestParam(required = false) String search_name,@RequestParam(required = false) String type_code,@RequestParam(required = false) String record_status){
        return ResultUtil.success(varietyEncyclopediaService.selectBreed(search_name,type_code,record_status));
    }

    /*
     * 品种管理-品种百科详情
     * */
    @GetMapping("/breedInfo/{id}")
    public Result<Object> selectBreedDetail(@PathVariable String id) throws Exception{
        Map map = varietyEncyclopediaService.selectBreedDetail(id);
        return ResultUtil.success(map);
    }

    /**
     * 通过品种查询百科详情
     * @param variety_code
     * @param variety_name
     * @return
     * @throws Exception
     */
    @GetMapping("/breedInfoByVariety")
    public Result<Object> breedInfoByVariety(@RequestParam(required = false) String variety_code,@RequestParam(required = false) String variety_name) throws Exception{
        return ResultUtil.success(varietyEncyclopediaService.breedInfoByVariety(variety_code,variety_name));
    }


    /**
     * 知识库信息审核列表
     * @param searchJson{"keyword": "abc", "type_code": 1}
     * @return
     */
    @GetMapping("/kmAuditList")
    public Result<Object> kmAuditList(@RequestParam(required = false) String searchJson){
        if(null == searchJson || "" == searchJson){
            searchJson = "{\"keyword\": \"\", \"type_code\": \"\"}";
        }
        Map map = JacksonMapper.INSTANCE.readJsonToMap(searchJson);
        return ResultUtil.success(varietyEncyclopediaService.kmAuditList(map));
    }

    /**
     *知识库信息审核
     * @param auditJson {"km_type": 2, "object_id": 1, "fail_opinion": "fnvk ds", "status": 1}
     * @return
     */
    @PostMapping("/kmAudit")
    @Transactional
    public Result<Object> kmAudit(@RequestBody String auditJson){
        if(null == auditJson || "" == auditJson){
            auditJson = "{\"fail_opinion\": \"\", \"status\": \"\", \"km_type\": \"\", \"object_id\": \"\"}";
        }
        Map map = JacksonMapper.INSTANCE.readJsonToMap(auditJson);
        if(null != map.get("km_type") && "" !=map.get("km_type")){
            String km_type = (String) map.get("km_type");
            String status = (String) map.get("status");
            String km_status = "1".equals(status)?"3":"4";
            String ve_status = "1".equals(status)?"3":"2";
            map.put("km_status",km_status);
            map.put("iv_status",km_status);
            if("1".equals(km_type)){
                varietyEncyclopediaService.changeStatus(map);
            }else if("6".equals(km_type)){
                varietyEncyclopediaService.changePhaStatus(map);
            }else if("4".equals(km_type)){
                varietyEncyclopediaService.changeSopStatus(map);
            }else if("2".equals(km_type)){
                varietyEncyclopediaService.changeInfoStatus(map);
            }else if("5".equals(km_type)){
                varietyEncyclopediaService.changeVedioStatus(map);
            }
        };

        return ResultUtil.success(varietyEncyclopediaService.kmAudit(map));
    }


    /*
     * APP接口
     * */

    /*
     * 热门品种查询
     * */
    @GetMapping("/hotBreed")
    public Result<Object> showHotBreed(){
        return ResultUtil.success(varietyEncyclopediaService.showHotBreed());
    }

    /*
    * 门户资源分布
    * */
    @GetMapping("/sourceDistribution")
    public Result<Object> sourceDistribution(){
        return ResultUtil.success(varietyEncyclopediaService.sourceDistribution());
    }

    /*
    * 历史搜索-插入
    * */
    @GetMapping("/addHistoryText")
    public Result<Object> addHistoryText(@RequestParam String search_text){
        return ResultUtil.success(varietyEncyclopediaService.addHistoryText(search_text));
    }

    /*
    * 历史搜索-返回历史搜索文本
    * */
    @GetMapping("/searchHistoryText")
    public Result<Object> searchHistoryText(){
        return ResultUtil.success(varietyEncyclopediaService.searchHistoryText());
    }


}
