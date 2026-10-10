package com.example.agriculture.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.agriculture.farmer.service.FarmerSchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FarmerReportScheduler
{
	private final FarmerSchedulerService farmerSchedulerService;

	@Scheduled(fixedRateString = "${scheduler.farmer-report.rate}")
	public void generateFarmerReport() 
	{
		log.info("===== Farmer Report Scheduler Started =====");

		try 
		{
			farmerSchedulerService.generateFarmerReport();

			log.info("===== Farmer Report Scheduler Completed Successfully =====");

		}
		catch (Exception e) 
		{
			log.error("Farmer Report Scheduler failed", e);
		}
	}

}
