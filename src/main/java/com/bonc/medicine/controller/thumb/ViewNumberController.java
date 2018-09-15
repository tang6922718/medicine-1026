package com.bonc.medicine.controller.thumb;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.thumb.ViewNumberService;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: 浏览数向外提供接口的控制类
 * @author: hejiajun
 * @create: 2018-09-09 19:22
 **/
@RestController
public class ViewNumberController {

    @Autowired
    private ViewNumberService viewNumberService;

    /**
    * @Description: 查询浏览数
    * @Param: [objectType, objectId]
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/15 
    */ 
    @GetMapping("/view/number/v1.0")
    public Result queryViewNumber(@RequestParam String objectType,
                                  @RequestParam String objectId) {

        //objectType; objectId
        if (StringUtils.isEmpty(objectType) || StringUtils.isEmpty(objectId)) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        Map<String, String> map = new HashMap<>();
        map.put("objectType", objectType);
        map.put("objectId", objectId);

        return ResultUtil.success(viewNumberService.queryViewNumber(map));
    }

    /**
    * @Description: 增加浏览数
    * @Param: [paramMap]
    * @return: com.bonc.medicine.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/15 
    */ 
    @PostMapping("/view/number/v1.0")
    public Result addOrUpdateViewNumberCord(@RequestBody Map<String, String> paramMap) {
        // viewNumber;objectType;objectId
        if (StringUtils.isEmpty(paramMap.get("objectType")) || StringUtils.isEmpty(paramMap.get("objectId"))) {
            return ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        if (StringUtils.isEmpty(paramMap.get("viewNumber"))) {
            paramMap.put("viewNumber", "1");
        }

        return ResultUtil.success(viewNumberService.addOrUpdateViewNumberCord(paramMap));
    }

}
