package com.example.agriculture.expense.serviceimpl;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.agriculture.activity.entity.Activity;
import com.example.agriculture.activity.repository.ActivityRepository;
import com.example.agriculture.expense.dto.ExpensePatchDto;
import com.example.agriculture.expense.dto.ExpenseRequestDto;
import com.example.agriculture.expense.dto.ExpenseResponseDto;
import com.example.agriculture.expense.entity.Expense;
import com.example.agriculture.expense.enumtype.ExpenseType;
import com.example.agriculture.expense.mapper.ExpenseMapper;
import com.example.agriculture.expense.repository.ExpenseRepository;
import com.example.agriculture.expense.service.ExpenseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService 
{
	private final ExpenseRepository expenseRepository;
	private final ActivityRepository activityRepository;
	private final ExpenseMapper expenseMapper;

	@Override
	public ExpenseResponseDto createOrUpdate(ExpenseRequestDto dto) 
	{
		log.info("Processing expense create/update request");

		Activity activity = activityRepository.findById(dto.getActivityId())
				.orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + dto.getActivityId()));

		Expense expense;

		if (dto.getExpenseId() == null) 
		{
			log.info("Creating new expense for activity ID: {}", dto.getActivityId());

			expense = expenseMapper.toEntity(dto);
			expense.setActivity(activity);

		} 
		else 
		{
			log.info("Updating expense with ID: {}", dto.getExpenseId());

			expense = expenseRepository.findById(dto.getExpenseId()).orElseThrow(
					() -> new IllegalArgumentException("Expense not found with ID: " + dto.getExpenseId()));

			if (dto.getVersion() == null || !dto.getVersion().equals(expense.getVersion())) 
			{
				log.warn("Optimistic locking failed for expense ID: {}", dto.getExpenseId());

				throw new ObjectOptimisticLockingFailureException(Expense.class, dto.getExpenseId());
			}

			expenseMapper.updateEntity(dto, expense);
			expense.setActivity(activity);
		}

		Expense savedExpense = expenseRepository.save(expense);

		log.info("Expense create/update completed successfully. Expense ID: {}", savedExpense.getExpenseId());

		return expenseMapper.toResponseDto(savedExpense);
	}

	@Override
	public List<ExpenseResponseDto> getAll() 
	{
		log.info("Fetching all expenses");

		return expenseRepository.findAll().stream().map(expenseMapper::toResponseDto).toList();
	}

	@Override
	public ExpenseResponseDto getById(Integer id) 
	{		
		log.info("Fetching expense with ID: {}", id);

		Expense expense = expenseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Expense not found with ID: " + id));

		return expenseMapper.toResponseDto(expense);
	}

	@Override
	public ExpenseResponseDto update(Integer id, ExpenseRequestDto dto) 
	{
		log.info("Updating expense with ID: {}", id);

		Expense expense = expenseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Expense not found with ID: " + id));

		if (dto.getVersion() == null || !dto.getVersion().equals(expense.getVersion())) 
		{
			log.warn("Optimistic locking failed for expense ID: {}", id);

			throw new ObjectOptimisticLockingFailureException(Expense.class, id);
		}

		Activity activity = activityRepository.findById(dto.getActivityId())
				.orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + dto.getActivityId()));

		expenseMapper.updateEntity(dto, expense);
		expense.setActivity(activity);

		Expense updatedExpense = expenseRepository.saveAndFlush(expense);

		log.info("Expense updated successfully with ID: {}", id);

		return expenseMapper.toResponseDto(updatedExpense);
	}

	@Override
	public ExpenseResponseDto partialUpdate(Integer id, ExpensePatchDto dto) 
	{
		log.info("Partially updating expense with ID: {}", id);

		Expense expense = expenseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Expense not found with ID: " + id));

		expenseMapper.patchEntity(dto, expense);

		if (dto.getActivityId() != null) 
		{
			Activity activity = activityRepository.findById(dto.getActivityId()).orElseThrow(
					() -> new IllegalArgumentException("Activity not found with ID: " + dto.getActivityId()));

			expense.setActivity(activity);
		}

		Expense updatedExpense = expenseRepository.saveAndFlush(expense);

		log.info("Expense partially updated successfully with ID: {}", id);

		return expenseMapper.toResponseDto(updatedExpense);
	}

	@Override
	public void deleteById(Integer id) 
	{
		log.info("Deleting expense with ID: {}", id);

		if (!expenseRepository.existsById(id)) 
		{
			throw new IllegalArgumentException("Expense not found with ID: " + id);
		}

		expenseRepository.deleteById(id);

		log.info("Expense deleted successfully with ID: {}", id);
	}

	@Override
	public Page<ExpenseResponseDto> getExpenses(int pageNo, int pageSize) 
	{
		log.info("Fetching expenses with pagination. Page: {}, Size: {}", pageNo, pageSize);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

		return expenseRepository.findAll(pageRequest).map(expenseMapper::toResponseDto);
	}

	@Override
	public Page<ExpenseResponseDto> getExpensesSorted(int pageNo, int pageSize, String field) 
	{
		log.info("Fetching expenses with pagination and sorting. Page: {}, Size: {}, Field: {}", pageNo, pageSize,
				field);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(field).ascending());

		return expenseRepository.findAll(pageRequest).map(expenseMapper::toResponseDto);
	}

	@Override
	public List<ExpenseResponseDto> searchByName(String expenseName) 
	{
		log.info("Searching expenses by name: {}", expenseName);

		return expenseRepository.findByExpenseNameContainingIgnoreCase(expenseName).stream()
				.map(expenseMapper::toResponseDto).toList();
	}

	@Override
	public List<ExpenseResponseDto> getExpensesByType(ExpenseType expenseType) 
	{
		log.info("Fetching expenses by type: {}", expenseType);

		return expenseRepository.findByExpenseType(expenseType).stream().map(expenseMapper::toResponseDto).toList();
	}

	@Override
	public List<ExpenseResponseDto> getExpensesByActivity(Integer activityId) 
	{
		log.info("Fetching expenses for activity ID: {}", activityId);

		return expenseRepository.findByActivityActivityId(activityId).stream().map(expenseMapper::toResponseDto)
				.toList();
	}

	@Override
	public List<ExpenseResponseDto> getExpensesByDate(LocalDate startDate, LocalDate endDate)
	{
		log.info("Fetching expenses between {} and {}", startDate, endDate);

		return expenseRepository.findByExpenseDateBetween(startDate, endDate).stream().map(expenseMapper::toResponseDto)
				.toList();
	}

	@Override
	public long getExpenseCount() 
	{
		log.info("Fetching total expense count");

		return expenseRepository.count();
	}

	@Override
	public Double getTotalExpense() 
	{
		log.info("Calculating total expense amount using JPQL aggregate query");
	    
		return expenseRepository.getTotalExpense();
		
	}

}
