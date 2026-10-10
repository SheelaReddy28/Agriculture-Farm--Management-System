package com.example.agriculture.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.agriculture.crop.service.CropSchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CropMonitoringScheduler 
{
	private final CropSchedulerService cropSchedulerService;

	@Scheduled(fixedRateString = "${scheduler.crop-monitoring.rate}")
	public void monitorCrops() 
	{

		log.info("===== Crop Monitoring Scheduler Started =====");

		try 
		{
			cropSchedulerService.generateCropMonitoringReport();

			log.info("===== Crop Monitoring Scheduler Completed Successfully =====");

		} 
		catch (Exception e)
		{
			log.error("Crop Monitoring Scheduler failed", e);
		}
	}

}
