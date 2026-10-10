package com.example.agriculture.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.agriculture.harvest.service.HarvestSchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HarvestReminderScheduler 
{	
	private final HarvestSchedulerService harvestSchedulerService;

	@Scheduled(fixedRateString = "${scheduler.harvest-reminder.rate}")
	public void remindHarvests()
	{
		log.info("===== Harvest Reminder Scheduler Started =====");

		try 
		{
			harvestSchedulerService.generateHarvestReminderReport();

			log.info("===== Harvest Reminder Scheduler Completed Successfully =====");

		} 
		catch (Exception e)
		{
			log.error("Harvest Reminder Scheduler failed", e);
		}
	}

}
