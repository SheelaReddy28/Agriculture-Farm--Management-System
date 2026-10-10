package com.example.agriculture.activity.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.agriculture.activity.dto.ActivityPatchDto;
import com.example.agriculture.activity.dto.ActivityRequestDto;
import com.example.agriculture.activity.dto.ActivityResponseDto;
import com.example.agriculture.activity.entity.Activity;

@Mapper(componentModel = "spring")
public interface ActivityMapper 
{
	@Mapping(target = "crop", ignore = true)
	Activity toEntity(ActivityRequestDto dto);

	@Mapping(target = "cropId", source = "crop.cropId")
	@Mapping(target = "cropName", source = "crop.cropName")
	ActivityResponseDto toResponseDto(Activity activity);

	@Mapping(target = "activityId", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "crop", ignore = true)
	void updateEntity(ActivityRequestDto dto, @MappingTarget Activity activity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "crop", ignore = true)
	void patchEntity(ActivityPatchDto dto, @MappingTarget Activity activity);

}