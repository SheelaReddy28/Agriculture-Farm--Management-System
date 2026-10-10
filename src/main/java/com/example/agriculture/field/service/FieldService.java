package com.example.agriculture.field.service;

import java.util.List;




import org.springframework.data.domain.Page;
import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;
import com.example.agriculture.field.dto.FieldPatchDto;
import com.example.agriculture.field.dto.FieldRequestDto;
import com.example.agriculture.field.dto.FieldResponseDto;

public interface FieldService {
	
	 FieldResponseDto createOrUpdate(FieldRequestDto dto);

	 List<FieldResponseDto> getAll();

	 FieldResponseDto getById(Integer id);

	 FieldResponseDto update(Integer id, FieldRequestDto dto);

	 FieldResponseDto partialUpdate(Integer id, FieldPatchDto dto);

	 void deleteById(Integer id);

	 Page<FieldResponseDto> getFields(int pageNo, int pageSize);

	 Page<FieldResponseDto> getFieldsSorted(int pageNo,int pageSize,String field);

	 List<FieldResponseDto> searchByName(String fieldName);

	 List<FieldResponseDto> getFieldsBySoilType(SoilType soilType);

	 List<FieldResponseDto> getFieldsByIrrigationType(IrrigationType irrigationType);

	 List<FieldResponseDto> getFieldsByFarm(Integer farmId);

	 long getFieldCount();
	
}
