package com.bonc.medicine.controller.thumb;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.entity.Result;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.service.thumb.ThumbService;
import com.bonc.medicine.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: medicine-hn
 *
 * @description: 点赞的控制类向外提供接口
 *
 * @author: hejiajun
 *
 * @create: 2018-09-05 19:04
 **/
@RestController
public class ThumbController {

    @Autowired
    ThumbService thumbService;

    /**
    * @Description: 点赞
    * @Param: [param]
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/5 
    */ 
    @PostMapping("/thumb/v1.0")
    public Result giveThumb(@RequestBody Map<String, String> param){
        validatePram(param);
        return ResultUtil.success(thumbService.giveThumb(param));

    }

    /**
    * @Description:取消点赞
    * @Param: [giveThumbId, type, acceptThumbId]
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/5
    */
    @DeleteMapping("/thumb/v1.0/{giveThumbId}/{type}/{acceptThumbId}")
    public Result removeThumb(@PathVariable String giveThumbId,
                              @PathVariable String type,
                              @PathVariable String acceptThumbId){
        if(StringUtils.isEmpty(giveThumbId) || StringUtils.isEmpty(type) || StringUtils.isEmpty(acceptThumbId)){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        Map<String, String> param = new HashMap<>();
        param.put("giveThumbId", giveThumbId);
        param.put("type", type);
        param.put("acceptThumbId", acceptThumbId);

        return ResultUtil.success(thumbService.removeThumb(param));

    }

    /**
    * @Description:获取点赞数
    * @Param: [acceptThumbId, type]
    * @return: com.bonc.thumb.entity.Result
    * @Author: hejiajun
    * @Date: 2018/9/5
    */
    @GetMapping("/thumb/v1.0/{type}/{acceptThumbId}")
    public Result thumbNumber(@PathVariable String type,
                              @PathVariable String acceptThumbId) {
        if(StringUtils.isEmpty(type) || StringUtils.isEmpty(acceptThumbId)){
            ResultUtil.error(ResultEnum.MISSING_PARA);
        }
        Map<String, String> param = new HashMap<>();
        param.put("type", type);
        param.put("acceptThumbId", acceptThumbId);
        return ResultUtil.success(thumbService.thumbNumber(param));

    }

    /**
    * @Description:获取当前用户对 某个对象是否点赞 三个参数都是必须的
    * @Param: [userid : 当前的userid, type：对象的类型, acceptThumbId：对象的id]
    * @return: int 1：点赞 0： 未点赞 ;-999:参数不全
    * @Author: hejiajun
    * @Date: 2018/9/20
    */
    @GetMapping("/thumb/status/v1.0")
    public int thumbStatus(@RequestParam(required = true) String userid,
                              @RequestParam(required = true)  String type,
                              @RequestParam(required = true)  String acceptThumbId){

        return  thumbService.thumbStatus(userid, type, acceptThumbId);
    }




    public void validatePram( Map<String, String> paramMap){
        if (null == paramMap){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }
        String giveThumbId = paramMap.get("giveThumbId");
        String acceptThumbId = paramMap.get("acceptThumbId");
        String type = paramMap.get("type");
        if(null == type ){
            paramMap.put("type", "0");
        }

        if(StringUtils.isEmpty(giveThumbId) || StringUtils.isEmpty(acceptThumbId)){
            throw new MedicineRuntimeException(ResultEnum.MISSING_PARA);
        }
    }

}
