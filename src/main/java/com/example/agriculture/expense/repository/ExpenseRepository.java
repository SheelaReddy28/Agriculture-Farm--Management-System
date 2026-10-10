package com.example.agriculture.expense.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.agriculture.expense.entity.Expense;
import com.example.agriculture.expense.enumtype.ExpenseType;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> 
{
	List<Expense> findByExpenseNameContainingIgnoreCase(String expenseName);

	List<Expense> findByExpenseType(ExpenseType expenseType);

	List<Expense> findByActivityActivityId(Integer activityId);

	List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);
	
	@Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
	Double getTotalExpense();

}
