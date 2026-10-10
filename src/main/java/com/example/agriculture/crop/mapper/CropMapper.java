package com.example.agriculture.crop.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.agriculture.crop.dto.CropPatchDto;
import com.example.agriculture.crop.dto.CropRequestDto;
import com.example.agriculture.crop.dto.CropResponseDto;
import com.example.agriculture.crop.entity.Crop;

@Mapper(componentModel = "spring")
public interface CropMapper {
	
	@Mapping(target = "field", ignore = true)
	Crop toEntity(CropRequestDto dto);

	@Mapping(target = "fieldId", source = "field.fieldId")
	@Mapping(target = "fieldName", source = "field.fieldName")
	CropResponseDto toResponseDto(Crop crop);

	@Mapping(target = "cropId", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "field", ignore = true)
	void updateEntity(CropRequestDto dto, @MappingTarget Crop crop);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "field", ignore = true)
	void patchEntity(CropPatchDto dto, @MappingTarget Crop crop);

}
