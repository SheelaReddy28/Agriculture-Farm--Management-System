package com.example.agriculture.crop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.agriculture.crop.dto.CropPatchDto;
import com.example.agriculture.crop.dto.CropRequestDto;
import com.example.agriculture.crop.dto.CropResponseDto;
import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;

public interface CropService {
	
	CropResponseDto createOrUpdate(CropRequestDto dto);

	List<CropResponseDto> getAll();

	CropResponseDto getById(Integer id);

	CropResponseDto update(Integer id, CropRequestDto dto);

	CropResponseDto partialUpdate(Integer id, CropPatchDto dto);

	void deleteById(Integer id);

	Page<CropResponseDto> getCrops(int pageNo, int pageSize);

	Page<CropResponseDto> getCropsSorted(int pageNo, int pageSize, String field);

	List<CropResponseDto> searchByName(String cropName);

	List<CropResponseDto> getCropsBySeason(CropSeason season);

	List<CropResponseDto> getCropsByStatus(CropStatus status);

	List<CropResponseDto> getCropsByField(Integer fieldId);

	List<CropResponseDto> getCropsByHarvestDate(java.time.LocalDate startDate, java.time.LocalDate endDate);

	long getCropCount();

}
