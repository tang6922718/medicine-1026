package com.bonc.medicine.mapper.thumb;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: medicine-hn
 * @description: ${description}
 * @author: hejiajun
 * @create: 2018-09-08 19:05
 **/
@Component
public interface LogsMapper {

    public int addLoginLogs(Map<String, String> paramMap);

    public int addLogoutLogs(Map<String, String> paramMap);

    public int updateOperLogsNormal(@Param("logId") String logId);

    public int updateOperLogsUnnormal(@Param("logId") String logId);

    public List<Map<String, Object>> queryLoginOutLogs(Map<String, String> paramMap);

    public List<Map<String, Object>> queryOperLogs(Map<String, String> paramMap);

    public int addOperLogs(Map<String, String> paramMap);

    public List<Map<String, Object>> queryUserLoginTimes(Map<String, String> paramMap);


}
