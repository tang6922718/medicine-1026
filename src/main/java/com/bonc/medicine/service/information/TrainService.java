package com.bonc.medicine.service.information;

import java.util.List;
import java.util.Map;

public interface TrainService {

    Map queryAppointmentNumber(Map<String, String> map);

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

    List<Map> selectCourseClass();

    List<Map> selectTrainApply(Map<String,Object> map);

    Map queryCommentNumber(Map<String,String> map);

    int delCourseTrainVideo(Map<String,Object> map);

    int repealOfflineTrain(Map<String,Object> map);

    int repealVideoCourse(Map<String,Object> map);

    int editVideoCourse(Map<String,Object> map);

    List<Map> selectSpecialist();
}
