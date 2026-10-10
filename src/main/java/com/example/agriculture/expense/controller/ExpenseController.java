package com.example.agriculture.expense.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.agriculture.expense.dto.ExpensePatchDto;
import com.example.agriculture.expense.dto.ExpenseRequestDto;
import com.example.agriculture.expense.dto.ExpenseResponseDto;
import com.example.agriculture.expense.enumtype.ExpenseType;
import com.example.agriculture.expense.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
	private final ExpenseService expenseService;

	@Operation(summary = "Create or update expense", description = "Creates a new expense when expenseId is null, otherwise updates an existing expense.")
	@ApiResponses({ 
	@ApiResponse(responseCode = "201", description = "Expense created successfully"),
	@ApiResponse(responseCode = "200", description = "Expense updated successfully"),
	@ApiResponse(responseCode = "400", description = "Invalid expense data"),
	@ApiResponse(responseCode = "404", description = "Activity or expense not found"),
	@ApiResponse(responseCode = "409", description = "Optimistic locking conflict") })
	
	@PostMapping
	public ResponseEntity<ExpenseResponseDto> createOrUpdate(@Valid @RequestBody ExpenseRequestDto dto) 
	{
		ExpenseResponseDto response = expenseService.createOrUpdate(dto);

		if (dto.getExpenseId() == null) 
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<ExpenseResponseDto>> getAll() 
	{
		List<ExpenseResponseDto> response = expenseService.getAll();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExpenseResponseDto> getById(@PathVariable Integer id) 
	{
		ExpenseResponseDto response = expenseService.getById(id);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExpenseResponseDto> update(@PathVariable Integer id,@Valid @RequestBody ExpenseRequestDto dto)
	{
		ExpenseResponseDto response = expenseService.update(id, dto);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ExpenseResponseDto> partialUpdate(@PathVariable Integer id,@Valid @RequestBody ExpensePatchDto dto) 
	{
		ExpenseResponseDto response = expenseService.partialUpdate(id, dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) 
	{
		expenseService.deleteById(id);

		return ResponseEntity.ok("Expense record deleted");
	}

	@GetMapping("/page")
	public ResponseEntity<Page<ExpenseResponseDto>> getExpenses(@RequestParam int pageNo, @RequestParam int pageSize) 
	{
		Page<ExpenseResponseDto> response = expenseService.getExpenses(pageNo, pageSize);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/sorted")
	public ResponseEntity<Page<ExpenseResponseDto>> getExpensesSorted(@RequestParam int pageNo,@RequestParam int pageSize, @RequestParam String field) 
	{
		Page<ExpenseResponseDto> response = expenseService.getExpensesSorted(pageNo, pageSize, field);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search/name")
	public ResponseEntity<List<ExpenseResponseDto>> searchByName(@RequestParam String expenseName) 
	{
		List<ExpenseResponseDto> response = expenseService.searchByName(expenseName);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/type")
	public ResponseEntity<List<ExpenseResponseDto>> getExpensesByType(@RequestParam ExpenseType expenseType) 
	{
		List<ExpenseResponseDto> response = expenseService.getExpensesByType(expenseType);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/activity/{activityId}")
	public ResponseEntity<List<ExpenseResponseDto>> getExpensesByActivity(@PathVariable Integer activityId) 
	{
		List<ExpenseResponseDto> response = expenseService.getExpensesByActivity(activityId);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/date")
	public ResponseEntity<List<ExpenseResponseDto>> getExpensesByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate)
	{
		List<ExpenseResponseDto> response = expenseService.getExpensesByDate(startDate, endDate);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getExpenseCount() 
	{
		long count = expenseService.getExpenseCount();

		return ResponseEntity.ok(count);
	}

	@GetMapping("/total")
	public ResponseEntity<Double> getTotalExpense() 
	{
		Double total = expenseService.getTotalExpense();

		return ResponseEntity.ok(total);
	}

}
