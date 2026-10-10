package com.example.agriculture.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.agriculture.expense.service.ExpenseSchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpenseReportScheduler
{
	private final ExpenseSchedulerService expenseSchedulerService;

	@Scheduled(fixedRateString = "${scheduler.expense-report.rate}")
	public void generateExpenseReport() 
	{

		log.info("===== Expense Report Scheduler Started =====");

		try 
		{
			expenseSchedulerService.generateExpenseReport();

			log.info("===== Expense Report Scheduler Completed Successfully =====");

		} 
		catch (Exception e) {

			log.error("Expense Report Scheduler failed", e);
		}
	}

}
