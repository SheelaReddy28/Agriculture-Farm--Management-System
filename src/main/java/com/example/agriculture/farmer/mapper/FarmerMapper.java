package com.example.agriculture.farmer.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.agriculture.farmer.dto.FarmerPatchDto;
import com.example.agriculture.farmer.dto.FarmerRequestDto;
import com.example.agriculture.farmer.dto.FarmerResponseDto;
import com.example.agriculture.farmer.entity.Farmer;

@Mapper(componentModel = "spring")
public interface FarmerMapper {
	
	 Farmer toEntity(FarmerRequestDto dto);
	 
	 FarmerResponseDto toResponseDto(Farmer farmer);
	
	 @Mapping(target = "farmerId", ignore = true)
	 @Mapping(target = "version", ignore = true)
	 void updateEntity(FarmerRequestDto dto, @MappingTarget Farmer farmer);
	 
	 @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
		    
	 void patchEntity(FarmerPatchDto dto, @MappingTarget Farmer farmer);


}
