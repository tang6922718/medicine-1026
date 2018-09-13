package com.bonc.medicine.service.information.impl;

import com.bonc.medicine.mapper.information.TrainMapper;
import com.bonc.medicine.service.information.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TrainServiceImpl implements TrainService {


    @Autowired
    TrainMapper trainMapper;
    @Override
    public int createTrain(Map<String, Object> map) {
        return trainMapper.createTrain(map);
    }

    @Override
    public int createVideo(Map<String, Object> map) {
        return trainMapper.createVideo(map);
    }

    @Override
    public int addComment(Map<String, Object> map) {
        return trainMapper.addComment(map);
    }

    @Override
    public List<Map> selectComment(Map<String, Object> map) {
        return trainMapper.selectComment(map);
    }

    @Override
    public List<Map> selectCourseList(Map<String, Object> map) {
        return trainMapper.selectCourseList(map);
    }

    @Override
    public int addTrainApply(Map<String, Object> map) {
        return trainMapper.addTrainApply(map);
    }

    @Override
    public List<Map> selectTrainList(Map<String, Object> map) {
        return trainMapper.selectTrainList(map);
    }

    @Override
    public int editOfflineTrainVideo(Map<String, Object> map) {
        return trainMapper.editOfflineTrainVideo(map);
    }

    @Override
    public int editOfflineTrain(Map<String, Object> map) {
        return trainMapper.editOfflineTrain(map);
    }

    @Override
    public int delOfflineTrain(Map<String, Object> map) {
        return trainMapper.delOfflineTrain(map);
    }

    @Override
    public int delOfflineTrainVideo(Map<String, Object> map) {
        return trainMapper.delOfflineTrainVideo(map);
    }
}
