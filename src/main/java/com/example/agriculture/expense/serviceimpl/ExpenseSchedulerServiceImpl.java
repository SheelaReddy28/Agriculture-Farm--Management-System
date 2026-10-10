package com.example.agriculture.expense.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.agriculture.expense.dto.ExpenseResponseDto;
import com.example.agriculture.expense.service.ExpenseSchedulerService;
import com.example.agriculture.expense.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseSchedulerServiceImpl implements ExpenseSchedulerService 
{
	private final ExpenseService expenseService;
	
	@Override
	public void generateExpenseReport() 
	{
		long expenseCount = expenseService.getExpenseCount();

        List<ExpenseResponseDto> expenses = expenseService.getAll();

        log.info("===== EXPENSE REPORT =====");
        log.info("Total Expenses : {}", expenseCount);

        log.info("----- EXPENSE DETAILS -----");

        for (ExpenseResponseDto expense : expenses) {

            log.info("Expense ID      : {}", expense.getExpenseId());
            log.info("Expense Name    : {}", expense.getExpenseName());
            log.info("Expense Type    : {}", expense.getExpenseType());
            log.info("Expense Date    : {}", expense.getExpenseDate());
            log.info("Amount          : {}", expense.getAmount());
            log.info("Description     : {}", expense.getDescription());
            log.info("Activity ID     : {}", expense.getActivityId());
            log.info("Activity Name   : {}", expense.getActivityName());
            log.info("Created Date    : {}", expense.getCreatedDate());
            log.info("Modified Date   : {}", expense.getLastModifiedDate());
            log.info("Version         : {}", expense.getVersion());

            log.info("-----------------------------------");
        }

        log.info("===================================");
    }
		
		
	}


