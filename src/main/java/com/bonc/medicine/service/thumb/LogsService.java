package com.bonc.medicine.service.thumb;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: 用户日志接口
 * @author: hejiajun
 * @create: 2018-09-08 16:10
 **/
public interface LogsService {

    public Map<String, Object> addLoginLogs(Map<String, String> paramMap);

    public Map<String, Object> addLogoutLogs(Map<String, String> paramMap);

    public Map<String, Object> updateOperLogsNormal(String logId);

    public Map<String, Object> updateOperLogsUnnormal(String logId);

    public List<Map<String, Object>> queryLoginOutLogs(Map<String, String> paramMap);

    public List<Map<String, Object>> queryOperLogs(Map<String, String> paramMap);

    public Map<String, Object> addOperLogs(Map<String, String> paramMap);

    public List<Map<String, Object>> queryUserLoginTimes(Map<String, String> paramMap);


}
