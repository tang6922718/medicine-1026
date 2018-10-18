package com.bonc.medicine.service.information;

import java.util.List;
import java.util.Map;

public interface  LiveService {


    int createLive(Map<String, Object> map);


    int updateLiveStatus(String roomId, String status);

    List<Map>  selectAllLive(Map map,int pageNum,int pageSize);

    int updateWatchPeople(Map map);

    int editLive(Map<String, Object> map);

    int repealLive(Map<String, Object> map);

    int delLive(Map<String,Object> map);

    Integer updateWatchNum(String room_id);
}
