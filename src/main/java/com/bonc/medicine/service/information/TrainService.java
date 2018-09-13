package com.bonc.medicine.service.information;

import java.util.List;
import java.util.Map;

public interface TrainService {

    int createTrain(Map<String, Object> map);

    int createVideo(Map<String, Object> map);

    int addComment(Map<String, Object> map);

    List<Map> selectComment(Map<String, Object> map);

    List<Map> selectCourseList(Map<String, Object> map);

    int addTrainApply(Map<String, Object> map);

    List<Map> selectTrainList(Map<String, Object> map);

    int editOfflineTrainVideo(Map<String, Object> map);

    int editOfflineTrain(Map<String, Object> map);

    int delOfflineTrain(Map<String, Object> map);

    int delOfflineTrainVideo(Map<String, Object> map);
}
