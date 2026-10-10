package com.example.agriculture.expense.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.agriculture.expense.dto.ExpensePatchDto;
import com.example.agriculture.expense.dto.ExpenseRequestDto;
import com.example.agriculture.expense.dto.ExpenseResponseDto;
import com.example.agriculture.expense.entity.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper 
{

	@Mapping(target = "activity", ignore = true)
	Expense toEntity(ExpenseRequestDto dto);

	@Mapping(target = "activityId", source = "activity.activityId")
	@Mapping(target = "activityName", source = "activity.activityName")
	ExpenseResponseDto toResponseDto(Expense expense);

	@Mapping(target = "expenseId", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "activity", ignore = true)
	void updateEntity(ExpenseRequestDto dto, @MappingTarget Expense expense);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "activity", ignore = true)
	void patchEntity(ExpensePatchDto dto, @MappingTarget Expense expense);

}
