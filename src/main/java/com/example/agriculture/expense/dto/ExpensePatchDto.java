package com.example.agriculture.expense.dto;

import java.time.LocalDate;

import com.example.agriculture.expense.enumtype.ExpenseType;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensePatchDto 
{
	private String expenseName;

	private ExpenseType expenseType;

	private LocalDate expenseDate;

	@DecimalMin(value = "0.0", message = "Expense amount cannot be negative")
	private Double amount;

	private String description;

	private Integer activityId;

}
