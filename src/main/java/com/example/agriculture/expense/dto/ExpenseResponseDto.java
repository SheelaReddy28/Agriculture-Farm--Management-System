package com.example.agriculture.expense.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.agriculture.expense.enumtype.ExpenseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDto 
{
	
	private Integer expenseId;
	private Integer version;

	private String expenseName;
	private ExpenseType expenseType;
	private LocalDate expenseDate;
	private Double amount;
	private String description;

	private Integer activityId;
	private String activityName;

	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

}
