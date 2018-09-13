package com.bonc.medicine.service.information;


import java.util.List;
import java.util.Map;

public interface InfoService {

    List<Map> getAllInfo(int catCode);

    int addInfo(Map<String, Object> map);

    Map infoDetailById(String id);

    int infoEditById(Map<String, Object> map);

    int infoAudit(Map<String, Object> map);

    List<Map> infoClass(Map<String, Object> map);

    Map infoReadCount(Map<String, Object> map);

    int delInfo(Map<String, Object> map);
}
