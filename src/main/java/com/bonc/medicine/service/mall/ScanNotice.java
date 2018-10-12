package com.bonc.medicine.service.mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScanNotice {
	
	@Autowired
	NoticeService noticeService;
	@Scheduled(cron = "0 0/5 * * * ?")
    public void updateTrainStatus() {
		noticeService.scanNotice();
    }
}
