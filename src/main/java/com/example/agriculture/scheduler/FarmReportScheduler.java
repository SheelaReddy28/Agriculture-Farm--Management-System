package com.example.agriculture.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.agriculture.farm.service.FarmSchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FarmReportScheduler {
	private final FarmSchedulerService farmSchedulerService;

	@Scheduled(fixedRateString = "${scheduler.farm-report.rate}")
	public void generateFarmReport() 
	{

		log.info("===== Farm Report Scheduler Started =====");

		try {
			farmSchedulerService.generateFarmReport();

			log.info("===== Farm Report Scheduler Completed Successfully =====");

		} catch (Exception e) {
			log.error("Farm Report Scheduler failed", e);
		}
	}

}
