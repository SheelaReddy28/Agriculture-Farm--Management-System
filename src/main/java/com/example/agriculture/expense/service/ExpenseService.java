package com.example.agriculture.expense.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.agriculture.expense.dto.ExpensePatchDto;
import com.example.agriculture.expense.dto.ExpenseRequestDto;
import com.example.agriculture.expense.dto.ExpenseResponseDto;
import com.example.agriculture.expense.enumtype.ExpenseType;

public interface ExpenseService 
{
	ExpenseResponseDto createOrUpdate(ExpenseRequestDto dto);

	List<ExpenseResponseDto> getAll();

	ExpenseResponseDto getById(Integer id);

	ExpenseResponseDto update(Integer id, ExpenseRequestDto dto);

	ExpenseResponseDto partialUpdate(Integer id, ExpensePatchDto dto);

	void deleteById(Integer id);

	Page<ExpenseResponseDto> getExpenses(int pageNo, int pageSize);

	Page<ExpenseResponseDto> getExpensesSorted(int pageNo, int pageSize, String field);

	List<ExpenseResponseDto> searchByName(String expenseName);

	List<ExpenseResponseDto> getExpensesByType(ExpenseType expenseType);

	List<ExpenseResponseDto> getExpensesByActivity(Integer activityId);

	List<ExpenseResponseDto> getExpensesByDate(LocalDate startDate, LocalDate endDate);

	long getExpenseCount();

	Double getTotalExpense();

}
