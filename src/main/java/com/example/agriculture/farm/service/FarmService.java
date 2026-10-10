package com.example.agriculture.farm.service;

import java.util.List;


import org.springframework.data.domain.Page;

import com.example.agriculture.farm.dto.FarmProfitabilityDto;
import com.example.agriculture.farm.dto.FarmPatchDto;
import com.example.agriculture.farm.dto.FarmRequestDto;
import com.example.agriculture.farm.dto.FarmResponseDto;

public interface FarmService {

	FarmResponseDto createOrUpdate(FarmRequestDto dto);

	List<FarmResponseDto> getAll();

	FarmResponseDto getById(Integer id);

	FarmResponseDto update(Integer id, FarmRequestDto dto);

	FarmResponseDto partialUpdate(Integer id, FarmPatchDto dto);

	void deleteById(Integer id);

	Page<FarmResponseDto> getFarms(int pageNo, int pageSize);

	Page<FarmResponseDto> getFarmsSorted(int pageNo, int pageSize, String field);

	List<FarmResponseDto> searchByName(String farmName);

	List<FarmResponseDto> searchByLocation(String location);

	List<FarmResponseDto> getFarmsByStatus(String status);

	List<FarmResponseDto> getFarmsByFarmer(Integer farmerId);

	long getFarmCount();
	
	List<FarmProfitabilityDto> getFarmProfitabilityReport();

}
