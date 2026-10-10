package com.example.agriculture.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.agriculture.field.service.FieldSchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FieldMonitoringScheduler 
{
	private final FieldSchedulerService fieldSchedulerService;
	
	@Scheduled(fixedRateString = "${scheduler.field-monitoring.rate}")
    public void monitorFields() {

        log.info("===== Field Monitoring Scheduler Started =====");

        try {
            fieldSchedulerService.generateFieldMonitoringReport();

            log.info("===== Field Monitoring Scheduler Completed Successfully =====");

        } catch (Exception e) {
            log.error("Field Monitoring Scheduler failed", e);
        }
    }

}
