package com.example.agriculture.harvest.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.agriculture.harvest.dto.HarvestResponseDto;
import com.example.agriculture.harvest.entity.Harvest;
import com.example.agriculture.harvest.enumtype.HarvestStatus;
import com.example.agriculture.harvest.mapper.HarvestMapper;
import com.example.agriculture.harvest.repository.HarvestRepository;
import com.example.agriculture.crop.entity.Crop;
import com.example.agriculture.crop.repository.CropRepository;
import com.example.agriculture.harvest.dto.HarvestPatchDto;
import com.example.agriculture.harvest.dto.HarvestRequestDto;
import com.example.agriculture.harvest.service.HarvestService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HarvestServiceImpl implements HarvestService 
{
	private final HarvestRepository harvestRepository;
	private final CropRepository cropRepository;
	private final HarvestMapper harvestMapper;

	@Override
	public HarvestResponseDto createOrUpdate(HarvestRequestDto dto) 
	{
		log.info("Processing harvest create/update request");

		Crop crop = cropRepository.findById(dto.getCropId())
				.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + dto.getCropId()));

		Harvest harvest;

		if (dto.getHarvestId() == null) 
		{
			log.info("Creating new harvest for crop ID: {}", dto.getCropId());

			harvest = harvestMapper.toEntity(dto);
			harvest.setCrop(crop);

		} 
		else 
		{
			log.info("Updating harvest with ID: {}", dto.getHarvestId());

			harvest = harvestRepository.findById(dto.getHarvestId()).orElseThrow(
					() -> new IllegalArgumentException("Harvest not found with ID: " + dto.getHarvestId()));

			if (dto.getVersion() == null || !dto.getVersion().equals(harvest.getVersion()))
			{
				log.warn("Optimistic locking failed for harvest ID: {}", dto.getHarvestId());

				throw new ObjectOptimisticLockingFailureException(Harvest.class, dto.getHarvestId());
			}

			harvestMapper.updateEntity(dto, harvest);
			harvest.setCrop(crop);
		}

		Harvest savedHarvest = harvestRepository.save(harvest);

		log.info("Harvest create/update completed successfully. Harvest ID: {}", savedHarvest.getHarvestId());

		return harvestMapper.toResponseDto(savedHarvest);
	}

	@Override
	public List<HarvestResponseDto> getAll() 
	{
		log.info("Fetching all harvests");

		return harvestRepository.findAll().stream().map(harvestMapper::toResponseDto).toList();
	}

	@Override
	public HarvestResponseDto getById(Integer id) 
	{
		log.info("Fetching harvest with ID: {}", id);

		Harvest harvest = harvestRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + id));

		return harvestMapper.toResponseDto(harvest);
	}

	@Override
	public HarvestResponseDto update(Integer id, HarvestRequestDto dto)
	{
		log.info("Updating harvest with ID: {}", id);

		Harvest harvest = harvestRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + id));

		if (dto.getVersion() == null || !dto.getVersion().equals(harvest.getVersion())) 
		{
			log.warn("Optimistic locking failed for harvest ID: {}", id);

			throw new ObjectOptimisticLockingFailureException(Harvest.class, id);
		}

		Crop crop = cropRepository.findById(dto.getCropId())
				.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + dto.getCropId()));

		harvestMapper.updateEntity(dto, harvest);
		harvest.setCrop(crop);

		Harvest updatedHarvest = harvestRepository.saveAndFlush(harvest);

		log.info("Harvest updated successfully with ID: {}", id);

		return harvestMapper.toResponseDto(updatedHarvest);
	}

	@Override
	public HarvestResponseDto partialUpdate(Integer id, HarvestPatchDto dto) 
	{
		log.info("Partially updating harvest with ID: {}", id);

		Harvest harvest = harvestRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Harvest not found with ID: " + id));

		harvestMapper.patchEntity(dto, harvest);

		if (dto.getCropId() != null) 
		{
			Crop crop = cropRepository.findById(dto.getCropId())
					.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + dto.getCropId()));

			harvest.setCrop(crop);
		}

		Harvest updatedHarvest = harvestRepository.saveAndFlush(harvest);

		log.info("Harvest partially updated successfully with ID: {}", id);

		return harvestMapper.toResponseDto(updatedHarvest);
	}

	@Override
	public void deleteById(Integer id) 
	{
		log.info("Deleting harvest with ID: {}", id);

		if (!harvestRepository.existsById(id)) {

			throw new IllegalArgumentException("Harvest not found with ID: " + id);
		}

		harvestRepository.deleteById(id);

		log.info("Harvest deleted successfully with ID: {}", id);
	}

	@Override
	public Page<HarvestResponseDto> getHarvests(int pageNo, int pageSize) 
	{
		log.info("Fetching harvests with pagination. Page: {}, Size: {}", pageNo, pageSize);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

		return harvestRepository.findAll(pageRequest).map(harvestMapper::toResponseDto);
	}

	@Override
	public Page<HarvestResponseDto> getHarvestsSorted(int pageNo, int pageSize, String field)
	{
		log.info("Fetching harvests with pagination and sorting. Page: {}, Size: {}, Field: {}", pageNo, pageSize,
				field);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(field).ascending());

		return harvestRepository.findAll(pageRequest).map(harvestMapper::toResponseDto);
	}

	@Override
	public List<HarvestResponseDto> getHarvestsByStatus(HarvestStatus status) 
	{
		log.info("Fetching harvests by status: {}", status);

		return harvestRepository.findByStatus(status).stream().map(harvestMapper::toResponseDto).toList();
	}

	@Override
	public List<HarvestResponseDto> getHarvestsByCrop(Integer cropId) 
	{
		log.info("Fetching harvests for crop ID: {}", cropId);

		return harvestRepository.findByCropCropId(cropId).stream().map(harvestMapper::toResponseDto).toList();
	}

	@Override
	public List<HarvestResponseDto> getHarvestsByDate(LocalDate startDate, LocalDate endDate) 
	{
		log.info("Fetching harvests between {} and {}", startDate, endDate);

		return harvestRepository.findByHarvestDateBetween(startDate, endDate).stream().map(harvestMapper::toResponseDto)
				.toList();
	}

	@Override
	public List<HarvestResponseDto> searchByBuyer(String buyerName) 
	{
		log.info("Searching harvests by buyer: {}", buyerName);

		return harvestRepository.findByBuyerNameContainingIgnoreCase(buyerName).stream()
				.map(harvestMapper::toResponseDto).toList();
	}

	@Override
	public long getHarvestCount() 
	{
		log.info("Fetching total harvest count");

		return harvestRepository.count();
	}

	@Override
	public Double getTotalRevenue() 
	{
		log.info("Calculating total harvest revenue using JPQL aggregate query");
	    
		return harvestRepository.getTotalRevenue();
	}

}
