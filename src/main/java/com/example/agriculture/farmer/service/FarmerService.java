package com.example.agriculture.farmer.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.agriculture.farmer.dto.FarmerPatchDto;
import com.example.agriculture.farmer.dto.FarmerRequestDto;
import com.example.agriculture.farmer.dto.FarmerResponseDto;

public interface FarmerService {
		
    FarmerResponseDto createOrUpdate(FarmerRequestDto dto);
   
    List<FarmerResponseDto> getAll();
   
    FarmerResponseDto getById(Integer id);
   
    FarmerResponseDto update(Integer id,FarmerRequestDto dto);
    
    FarmerResponseDto partialUpdate(Integer id,FarmerPatchDto dto);

    void deleteById(Integer id);
   
    Page<FarmerResponseDto> getFarmers(int pageNo,int pageSize);
    
    Page<FarmerResponseDto> getFarmersSorted(int pageNo,int pageSize,String field);

    List<FarmerResponseDto> searchByName(String farmerName);
    
    long getFarmerCount();
   
    List<FarmerResponseDto> getFarmerHistory(Integer farmerId);
    
    List<FarmerResponseDto> findByFarmNameJPQL(String farmName);
    
    FarmerResponseDto findByEmailNative(String email);
	 	    	    
}
