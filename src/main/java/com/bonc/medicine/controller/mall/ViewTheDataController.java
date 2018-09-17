package com.bonc.medicine.controller.mall;

import com.bonc.medicine.entity.Result;
import com.bonc.medicine.service.mall.ViewTheDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ViewTheDataController {

    @Autowired
    private ViewTheDataService viewTheDataService;

    /*
     * 最近7日互动总数
     */
    @GetMapping("/viewTheData/interaction")
    public Result<Object> interaction( Integer user_id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = viewTheDataService.interaction(user_id);
        Result result = new Result();
        result.setData(list);
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

    /*
     * 问题统计
     */
    @GetMapping("/viewTheData/problemStatistics")
    public Result<Object> problemStatistics( Integer user_id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = viewTheDataService.problemStatistics(user_id);
        Result result = new Result();
        result.setData(list);
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

}
