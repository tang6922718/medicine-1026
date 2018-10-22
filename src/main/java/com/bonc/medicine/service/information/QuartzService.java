package com.bonc.medicine.service.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: tlz
 * @Date: 2018/10/9 15:03
 * @Description:
 */
@Component
public class QuartzService {


    @Autowired
    TrainService trainService;
    /**
     * 每小时更改 培训表的状态值 即
     * 小于当前培训日期     报名状态  0
     * 等于当前培训日期     进行中状态 1
     * 大于培训日期         结束状态 2
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void updateTrainStatus() {
        trainService.updateTrainStatus();
    }
}
