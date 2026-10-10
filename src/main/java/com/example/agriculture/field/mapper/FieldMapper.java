package com.example.agriculture.field.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.agriculture.field.dto.FieldPatchDto;
import com.example.agriculture.field.dto.FieldRequestDto;
import com.example.agriculture.field.dto.FieldResponseDto;
import com.example.agriculture.field.entity.Field;


@Mapper(componentModel = "spring")
public interface FieldMapper 
{
	
	@Mapping(target = "farm", ignore = true)
	Field toEntity(FieldRequestDto dto);

	@Mapping(target = "farmId", source = "farm.farmId")
	@Mapping(target = "farmName", source = "farm.farmName")
	FieldResponseDto toResponseDto(Field field);

	@Mapping(target = "fieldId", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "farm", ignore = true)
	void updateEntity(FieldRequestDto dto, @MappingTarget Field field);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "farm", ignore = true)
	void patchEntity(FieldPatchDto dto, @MappingTarget Field field);

}
