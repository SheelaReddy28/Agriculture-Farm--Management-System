package com.example.agriculture.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.agriculture.activity.service.ActivitySchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActivityReminderScheduler 
{
	private final ActivitySchedulerService activitySchedulerService;

	@Scheduled(fixedRateString = "${scheduler.activity-reminder.rate}")
	public void remindActivities() 
	{

		log.info("===== Activity Reminder Scheduler Started =====");

		try
		{

			activitySchedulerService.generateActivityReminderReport();

			log.info("===== Activity Reminder Scheduler Completed Successfully =====");

		} 
		catch (Exception e) 
		{

			log.error("Activity Reminder Scheduler failed", e);
		}
	}

}
