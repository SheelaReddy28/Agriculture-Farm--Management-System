package com.example.agriculture.farm.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.agriculture.farm.dto.FarmPatchDto;
import com.example.agriculture.farm.dto.FarmRequestDto;
import com.example.agriculture.farm.dto.FarmResponseDto;
import com.example.agriculture.farm.entity.Farm;

@Mapper(componentModel = "spring")
public interface FarmMapper 
{
	@Mapping(target = "farmer", ignore = true)
	Farm toEntity(FarmRequestDto dto);

	@Mapping(source = "farmer.farmerId", target = "farmerId")
	@Mapping(source = "farmer.farmerName", target = "farmerName")
	FarmResponseDto toResponseDto(Farm farm);

	@Mapping(target = "farmId", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "farmer", ignore = true)
	void updateEntity(FarmRequestDto dto, @MappingTarget Farm farm);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "farmer", ignore = true)
	void patchEntity(FarmPatchDto dto, @MappingTarget Farm farm);

}
