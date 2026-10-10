package com.example.agriculture.farm.serviceimpl;


import java.util.List;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.example.agriculture.exception.Constants;
import com.example.agriculture.exception.UserDefineException;
import com.example.agriculture.farm.dto.FarmPatchDto;
import com.example.agriculture.farm.dto.FarmProfitabilityDto;
import com.example.agriculture.farm.dto.FarmRequestDto;
import com.example.agriculture.farm.dto.FarmResponseDto;
import com.example.agriculture.farm.entity.Farm;
import com.example.agriculture.farm.enumtype.FarmStatus;
import com.example.agriculture.farm.mapper.FarmMapper;
import com.example.agriculture.farm.repository.FarmRepository;
import com.example.agriculture.farm.service.FarmService;
import com.example.agriculture.farmer.entity.Farmer;
import com.example.agriculture.farmer.repository.FarmerRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FarmServiceImpl implements FarmService {

	private final FarmRepository farmRepository;
	private final FarmerRepository farmerRepository;
	private final FarmMapper farmMapper;

	@Override
	public FarmResponseDto createOrUpdate(FarmRequestDto dto) 
	{

		log.info("Processing farm create/update request");
		Farmer farmer = farmerRepository.findById(dto.getFarmerId()).orElseThrow(
				() -> new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + dto.getFarmerId()));
		Farm farm;

		if (dto.getFarmId() == null) 
		{

			log.info("Creating new farm for farmer ID: {}", dto.getFarmerId());
			farm = farmMapper.toEntity(dto);
			farm.setFarmer(farmer);

		} else
		  {
			log.info("Updating farm with ID: {}", dto.getFarmId());
			farm = farmRepository.findById(dto.getFarmId())
					.orElseThrow(() -> new UserDefineException("Farm not found with ID: " + dto.getFarmId()));
			
			if (dto.getVersion() == null || !dto.getVersion().equals(farm.getVersion())) 
			{

		        log.warn("Optimistic locking failed for farm ID: {}. Request version: {}, Current version: {}",dto.getFarmId(),dto.getVersion(),farm.getVersion());

		        throw new ObjectOptimisticLockingFailureException(Farm.class,dto.getFarmId());
		    }

			farmMapper.updateEntity(dto, farm);
			farm.setFarmer(farmer);
		}

		Farm savedFarm = farmRepository.save(farm);
		log.info("Farm create/update completed successfully. Farm ID: {}", savedFarm.getFarmId());

		return farmMapper.toResponseDto(savedFarm);
	}

	@Override
	@Transactional
	public List<FarmResponseDto> getAll() 
	{
		log.info("Fetching all farms");
		List<Farm> farms = farmRepository.findAll();

		return farms.stream().map(farmMapper::toResponseDto).toList();
	}

	@Override
	@Transactional
	public FarmResponseDto getById(Integer id) 
	{
		log.info("Fetching farm with ID: {}", id);
	    Farm farm = farmRepository.findById(id)
	            .orElseThrow(() -> new UserDefineException("Farm not found with ID: " + id));

	    return farmMapper.toResponseDto(farm);		
	}

	@Override
	public FarmResponseDto update(Integer id, FarmRequestDto dto) 
	{
		log.info("Updating farm with ID: {}", id);
		Farm farm = farmRepository.findById(id)
				.orElseThrow(() -> new UserDefineException("Farm not found with ID: " + id));

		if (dto.getVersion() == null || !dto.getVersion().equals(farm.getVersion())) 
		{

			log.warn("Optimistic locking failed for farm ID: {}. Request version: {}, Current version: {}", id,dto.getVersion(), farm.getVersion());

			throw new ObjectOptimisticLockingFailureException(Farm.class, id);
		}

		Farmer farmer = farmerRepository.findById(dto.getFarmerId()).orElseThrow(
				() -> new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + dto.getFarmerId()));

		farmMapper.updateEntity(dto, farm);

		farm.setFarmer(farmer);

		Farm updatedFarm = farmRepository.saveAndFlush(farm);

		log.info("Farm updated successfully with ID: {}", id);

		return farmMapper.toResponseDto(updatedFarm);

	}

	@Override
	public FarmResponseDto partialUpdate(Integer id, FarmPatchDto dto) 
	{
		log.info("Partially updating farm with ID: {}", id);

		Farm farm = farmRepository.findById(id)
				.orElseThrow(() -> new UserDefineException("Farm not found with ID: " + id));

		farmMapper.patchEntity(dto, farm);

		if (dto.getFarmerId() != null) {

			Farmer farmer = farmerRepository.findById(dto.getFarmerId()).orElseThrow(
					() -> new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + dto.getFarmerId()));

			farm.setFarmer(farmer);
		}

		Farm updatedFarm = farmRepository.saveAndFlush(farm);

		log.info("Farm partially updated successfully with ID: {}", id);

		return farmMapper.toResponseDto(updatedFarm);

	}

	@Override
	public void deleteById(Integer id) 
	{
		log.info("Deleting farm with ID: {}", id);
		
		 if (!farmRepository.existsById(id)) 
		 {
		        throw new UserDefineException("Farm not found with ID: " + id);
		    }

		    farmRepository.deleteById(id);
		    farmRepository.flush();

		    log.info("Farm deleted successfully with ID: {}", id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FarmResponseDto> getFarms(int pageNo, int pageSize) 
	{
		log.info("Fetching farms with pagination. Page: {}, Size: {}", pageNo, pageSize);

		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Page<Farm> farms = farmRepository.findAll(pageable);

		return farms.map(farmMapper::toResponseDto);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<FarmResponseDto> getFarmsSorted(int pageNo, int pageSize, String field) 
	{
		log.info("Fetching farms with pagination and sorting. Page: {}, Size: {}, Sort field: {}", pageNo, pageSize,field);

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, field));
		
		Page<Farm> farms = farmRepository.findAll(pageable);

		return farms.map(farmMapper::toResponseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FarmResponseDto> searchByName(String farmName) 
	{
		log.info("Searching farms by name: {}", farmName);

		List<Farm> farms = farmRepository.findByFarmNameContainingIgnoreCase(farmName);

		return farms.stream().map(farmMapper::toResponseDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<FarmResponseDto> searchByLocation(String location) 
	{
		log.info("Searching farms by location: {}", location);

		List<Farm> farms = farmRepository.findByLocationContainingIgnoreCase(location);

		return farms.stream().map(farmMapper::toResponseDto).toList();		
	}

	@Override
	@Transactional(readOnly = true)
	public List<FarmResponseDto> getFarmsByStatus(String status) 
	{
		log.info("Fetching farms by status: {}", status);
		FarmStatus farmStatus;
		try 
		{
			farmStatus = FarmStatus.valueOf(status.toUpperCase());
		} 
		catch (IllegalArgumentException e) 
		{
			throw new IllegalArgumentException("Invalid farm status: " + status + ". Allowed values: ACTIVE, INACTIVE");
		}

		List<Farm> farms = farmRepository.findByStatus(farmStatus);

		return farms.stream().map(farmMapper::toResponseDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<FarmResponseDto> getFarmsByFarmer(Integer farmerId) 
	{
		log.info("Fetching farms for farmer ID: {}", farmerId);

		if (!farmerRepository.existsById(farmerId)) 
		{
			throw new UserDefineException(Constants.FARMER_NOT_FOUND + " with ID: " + farmerId);
		}

		List<Farm> farms = farmRepository.findByFarmerFarmerId(farmerId);

		return farms.stream().map(farmMapper::toResponseDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public long getFarmCount() 
	{
		log.info("Fetching total farm count");

		return farmRepository.count();
		
	}

	@Override
	public List<FarmProfitabilityDto> getFarmProfitabilityReport() 
	{
		 log.info("Fetching farm profitability report");
		   
		 return farmRepository.getFarmProfitabilityReport();
		
	}

}
