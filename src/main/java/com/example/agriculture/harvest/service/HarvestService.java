package com.example.agriculture.harvest.service;

import java.time.LocalDate;


import java.util.List;

import org.springframework.data.domain.Page;
import com.example.agriculture.harvest.dto.HarvestPatchDto;
import com.example.agriculture.harvest.dto.HarvestRequestDto;
import com.example.agriculture.harvest.dto.HarvestResponseDto;
import com.example.agriculture.harvest.enumtype.HarvestStatus;

public interface HarvestService 
{
	HarvestResponseDto createOrUpdate(HarvestRequestDto dto);

	List<HarvestResponseDto> getAll();

	HarvestResponseDto getById(Integer id);

	HarvestResponseDto update(Integer id, HarvestRequestDto dto);

	HarvestResponseDto partialUpdate(Integer id, HarvestPatchDto dto);

	void deleteById(Integer id);

	Page<HarvestResponseDto> getHarvests(int pageNo, int pageSize);

	Page<HarvestResponseDto> getHarvestsSorted(int pageNo, int pageSize, String field);

	List<HarvestResponseDto> getHarvestsByStatus(HarvestStatus status);

	List<HarvestResponseDto> getHarvestsByCrop(Integer cropId);

	List<HarvestResponseDto> getHarvestsByDate(LocalDate startDate, LocalDate endDate);

	List<HarvestResponseDto> searchByBuyer(String buyerName);

	long getHarvestCount();

	Double getTotalRevenue();

}
