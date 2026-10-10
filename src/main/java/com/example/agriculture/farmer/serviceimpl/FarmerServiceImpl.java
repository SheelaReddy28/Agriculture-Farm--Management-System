package com.example.agriculture.farmer.serviceimpl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.agriculture.exception.Constants;
import com.example.agriculture.exception.UserDefineException;

import com.example.agriculture.farmer.dto.FarmerPatchDto;
import com.example.agriculture.farmer.dto.FarmerRequestDto;
import com.example.agriculture.farmer.dto.FarmerResponseDto;
import com.example.agriculture.farmer.entity.Farmer;
import com.example.agriculture.farmer.mapper.FarmerMapper;
import com.example.agriculture.farmer.repository.FarmerRepository;
import com.example.agriculture.farmer.service.FarmerService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FarmerServiceImpl implements FarmerService 
{
	 private final FarmerRepository farmerRepository;

	 private final FarmerMapper farmerMapper;

	 @Override
	 public FarmerResponseDto createOrUpdate(FarmerRequestDto dto) 
	 {
		Farmer farmer;
		
		if(dto.getFarmerId()==null)
		{
			log.info("Creating new farmer with email: {}", dto.getEmail());			
			if(farmerRepository.existsByEmail(dto.getEmail()))
			{
				log.warn("Farmer creation failed. Email already exists: {}", dto.getEmail());
				throw new  IllegalArgumentException(Constants.EMAIL_ALREADY_EXISTS + ": "+dto.getEmail());  
			}
			
			farmer = farmerMapper.toEntity(dto);
		}
		else
		{
			log.info("Updating farmer with ID: {}", dto.getFarmerId());
			farmer = farmerRepository.findById(dto.getFarmerId())
					.orElseThrow(() ->
					{
						log.warn("Farmer not found with ID: {}", dto.getFarmerId());
					
					return new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + dto.getFarmerId());});
					 
			
			if (!farmer.getEmail().equals(dto.getEmail())&& farmerRepository.existsByEmail(dto.getEmail()))
			{
				throw new IllegalArgumentException(Constants.EMAIL_ALREADY_EXISTS + ": " + dto.getEmail());
				 				
			}
			farmerMapper.updateEntity(dto, farmer);				    	
			}
		Farmer savedFarmer = farmerRepository.save(farmer);
		
		return farmerMapper.toResponseDto(savedFarmer);		
			 }
	
	 @Override
	 @Transactional(readOnly = true)
	 public List<FarmerResponseDto>getAll()
	 {
		 return farmerRepository.findAll().stream().map(farmerMapper::toResponseDto).toList();
	 }
	 
	 @Override
	 @Cacheable(value = "farmers", key = "#id")
	 @Transactional(readOnly = true)	 
	 public FarmerResponseDto getById(Integer id) 
	 {
		 log.info("Fetching farmer with ID: {}", id);
		 Farmer farmer = farmerRepository.findById(id)
				 .orElseThrow(() ->
				 {
				log.warn("Farmer not found with ID: {}", id);				 
				return new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + id);});
				 	
		return farmerMapper.toResponseDto(farmer);
	 }

	 @Override
	 @CachePut(value = "farmers", key = "#id")
	 public FarmerResponseDto update(Integer id, FarmerRequestDto dto) 
	 {
		 log.info("Updating farmer with ID: {}", id);				 
		 Farmer farmer = farmerRepository.findById(id)
				 .orElseThrow(() ->
				 {
					 log.warn("Farmer not found with ID: {}", id);
				 return new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + id);});
		 
		 if (dto.getVersion() == null ||
		            !dto.getVersion().equals(farmer.getVersion())) 
		 {
			 log.warn("Optimistic locking failed for farmer ID: {}. Request version: {}, Current version: {}",id,dto.getVersion(),farmer.getVersion());
			 throw new ObjectOptimisticLockingFailureException(Farmer.class,id);
		 }
		 		 		 
		 if (!farmer.getEmail().equals(dto.getEmail()) && farmerRepository.existsByEmail(dto.getEmail()))
		 {
			 log.warn("Update failed. Email already exists: {}", dto.getEmail());
			 throw new IllegalArgumentException(Constants.EMAIL_ALREADY_EXISTS + ": "+ dto.getEmail());
			 
		 }
		 farmerMapper.updateEntity(dto, farmer);
		 
		 Farmer updatedFarmer = farmerRepository.saveAndFlush(farmer);
		 
		 log.info("Farmer updated successfully with ID: {}", id);
		 return farmerMapper.toResponseDto(updatedFarmer);
	 }

	 @Override
	 public FarmerResponseDto partialUpdate(Integer id, FarmerPatchDto dto) 
	 {
		 log.info("Partially updating farmer with ID: {}", id);
		 Farmer farmer = farmerRepository.findById(id)
				 .orElseThrow(() ->	
				 {
				log.warn("Farmer not found with ID: {}", id);	 			 
				return new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + id);});
		 
		 if (dto.getEmail() != null && !dto.getEmail().equals(farmer.getEmail())
	                && farmerRepository.existsByEmail(dto.getEmail()))
		 {
			 log.warn("Partial update failed. Email already exists: {}", dto.getEmail());
			 throw new IllegalArgumentException( Constants.EMAIL_ALREADY_EXISTS + ": "+ dto.getEmail());			 
		 }
		 farmerMapper.patchEntity(dto, farmer);
		 
		 Farmer updatedFarmer = farmerRepository.save(farmer);
		 
		 log.info("Farmer partially updated successfully with ID: {}", id);
		 return farmerMapper.toResponseDto(updatedFarmer);		
	 }

	 @Override
	 @CacheEvict(value = "farmers", key = "#id")
	 public void deleteById(Integer id) 
	 {
		 log.info("Deleting farmer with ID: {}", id);
		 if (!farmerRepository.existsById(id))
		 {
			 log.warn("Delete failed. Farmer not found with ID: {}", id);
			 throw new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + id);		 
		 }
		 farmerRepository.deleteById(id);
		 log.info("Farmer deleted successfully with ID: {}", id);
	 }

	 @Override
	 @Transactional(readOnly = true)
	 public org.springframework.data.domain.Page<FarmerResponseDto> getFarmers(int pageNo, int pageSize) 
	 {
		 org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(pageNo,pageSize);
		
		return farmerRepository.findAll(pageable).map(farmerMapper::toResponseDto);
	 }

	 @Override
	 @Transactional(readOnly = true)
	 public org.springframework.data.domain.Page<FarmerResponseDto> getFarmersSorted(int pageNo, int pageSize, String field) 
	 {
		 org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(pageNo, pageSize, org.springframework.data.domain.Sort.by(field).ascending());
		
		return farmerRepository.findAll(pageable).map(farmerMapper::toResponseDto);
	 }

	 @Override
	 @Transactional(readOnly = true)
	 public  List<FarmerResponseDto> searchByName(String farmerName) 
	 {
		
		return farmerRepository.findByFarmerNameContainingIgnoreCase(farmerName).stream().map(farmerMapper::toResponseDto).toList();
	 }

	 @Override
	 @Transactional(readOnly = true)
	 public long getFarmerCount() 
	 {
		 return farmerRepository.count();
				
	 }

	 @Override
	 @Transactional(readOnly = true)
	 public List<FarmerResponseDto> getFarmerHistory(Integer farmerId) 
	 {
		 Farmer farmer = farmerRepository.findById(farmerId)
				 .orElseThrow(() ->
				 new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + farmerId)); 
		
		return List.of(farmerMapper.toResponseDto(farmer));
	 }
	 
	 @Override
	 @Transactional(readOnly = true)
	 public List<FarmerResponseDto> findByFarmNameJPQL(String farmName) 
	 {
		 List<Farmer> farmers = farmerRepository.findByFarmNameJPQL(farmName);
		 
		 return farmers.stream().map(farmerMapper::toResponseDto).toList();
		 
	 }
	 
	 @Override
	 @Transactional(readOnly = true)
	 public FarmerResponseDto findByEmailNative(String email) 
	 {
		 Farmer farmer = farmerRepository.findByEmailNative(email)
		            .orElseThrow(() -> new UserDefineException(Constants.FARMER_NOT_FOUND + " with email: " + email));
		 
		 return farmerMapper.toResponseDto(farmer);

	 }	 
	 	    
}
