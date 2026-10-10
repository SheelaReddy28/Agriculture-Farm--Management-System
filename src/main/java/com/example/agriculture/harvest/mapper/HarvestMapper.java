package com.example.agriculture.harvest.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.agriculture.harvest.dto.HarvestPatchDto;
import com.example.agriculture.harvest.dto.HarvestRequestDto;
import com.example.agriculture.harvest.dto.HarvestResponseDto;
import com.example.agriculture.harvest.entity.Harvest;

@Mapper(componentModel = "spring")
public interface HarvestMapper 
{
	@Mapping(target = "crop", ignore = true)
	Harvest toEntity(HarvestRequestDto dto);

	@Mapping(target = "cropId", source = "crop.cropId")
	@Mapping(target = "cropName", source = "crop.cropName")
	HarvestResponseDto toResponseDto(Harvest harvest);

	@Mapping(target = "harvestId", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "crop", ignore = true)
	void updateEntity(HarvestRequestDto dto, @MappingTarget Harvest harvest);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "crop", ignore = true)
	void patchEntity(HarvestPatchDto dto, @MappingTarget Harvest harvest);

}
