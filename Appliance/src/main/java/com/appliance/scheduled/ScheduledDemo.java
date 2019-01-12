package com.appliance.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledDemo {

	@Scheduled(cron = "0 0 0/1 * * ? ") // 每小时执行一次
	public void statusCheck() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Date date = new Date();
		String nowTime = sdf.format(date);
		System.out.println("这是每小时执行一次的定时任务！！！当前时间：" + nowTime);
	}

}
