package com.bonc.medicine.service.thumb.impl;

import com.bonc.medicine.Exception.MedicineRuntimeException;
import com.bonc.medicine.enums.ResultEnum;
import com.bonc.medicine.mapper.thumb.LogsMapper;
import com.bonc.medicine.service.thumb.LogsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: 积分的实现类
 * @author: hejiajun
 * @create: 2018-09-08 20:42
 **/
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsMapper logsMapper;

    @Override
    public Map<String, Object> addLoginLogs(Map<String, String> paramMap) {
        int succeedRow = logsMapper.addLoginLogs(paramMap);

        return mapReturnUtil("succeed", succeedRow, 0);
    }

    @Override
    public Map<String, Object> addLogoutLogs(Map<String, String> paramMap) {
        int succeedRow = logsMapper.addLogoutLogs(paramMap);

        return mapReturnUtil("succeed", succeedRow, 0);
    }

    @Override
    public Map<String, Object> updateOperLogsNormal(String logId) {
        String [] ids = logId.split(",");
        int succeedRow = logsMapper.updateOperLogsNormal(logId);
        Integer failedRow = ids.length == succeedRow ? 0 : ids.length - succeedRow;
        return mapReturnUtil("succeed", succeedRow, failedRow);
    }

    @Override
    public Map<String, Object> updateOperLogsUnnormal(String logId) {
        String [] ids = logId.split(",");
        int succeedRow = logsMapper.updateOperLogsUnnormal(logId);
        Integer failedRow = ids.length == succeedRow ? 0 : ids.length - succeedRow;
        return mapReturnUtil("succeed", succeedRow, failedRow);
    }

    @Override
    public List<Map<String, Object>> queryLoginOutLogs(Map<String, String> paramMap) {
        List<Map<String, Object>> reList = logsMapper.queryLoginOutLogs(paramMap);
        if (reList.size() == 0 || reList.get(0).size() == 0) {
            throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
        }
        return reList;
    }

    @Override
    public List<Map<String, Object>> queryOperLogs(Map<String, String> paramMap) {
        List<Map<String, Object>> reList = logsMapper.queryOperLogs(paramMap);
        if (reList.size() == 0 || reList.get(0).size() == 0) {
            throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
        }

        return reList;
    }

    @Override
    public Map<String, Object> addOperLogs(Map<String, String> paramMap) {
        int succeedRow = logsMapper.addOperLogs(paramMap);

        return mapReturnUtil("succeed", succeedRow, 0);
    }

    @Override
    public List<Map<String, Object>> queryUserLoginTimes(Map<String, String> paramMap) {
        List<Map<String, Object>> reList = logsMapper.queryUserLoginTimes(paramMap);
        if (reList.size() == 0 || reList.get(0).size() == 0) {
            throw new MedicineRuntimeException(ResultEnum.NO_CONTENT);
        }
        return reList;
    }


    /**
     * @Description:这只是一个辅助的方法
     * @Param: [key, value]
     * @return: java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author: hejiajun
     * @Date: 2018/9/9
     */
    private Map<String, Object> mapReturnUtil(String key, Object value,Integer failedRow) {
        Map<String, Object> reMap = new HashMap<>();
        reMap.put(key, value);
        if (failedRow > 0){
            reMap.put("failed", failedRow + "");
        }
        return reMap;
    }
}
