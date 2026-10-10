package com.example.agriculture.expense.dto;

import java.time.LocalDate;

import com.example.agriculture.expense.enumtype.ExpenseType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto 
{
	private Integer expenseId;
	private Integer version;

	@NotBlank(message = "Expense name is required")
	private String expenseName;

	@NotNull(message = "Expense type is required")
	private ExpenseType expenseType;

	@NotNull(message = "Expense date is required")
	private LocalDate expenseDate;

	@NotNull(message = "Expense amount is required")
	@DecimalMin(value = "0.0", message = "Expense amount cannot be negative")
	private Double amount;

	private String description;

	@NotNull(message = "Activity ID is required")
	private Integer activityId;

}
