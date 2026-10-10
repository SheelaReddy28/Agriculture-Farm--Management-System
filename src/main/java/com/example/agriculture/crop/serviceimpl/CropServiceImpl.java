package com.example.agriculture.crop.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.agriculture.crop.dto.CropPatchDto;
import com.example.agriculture.crop.dto.CropRequestDto;
import com.example.agriculture.crop.dto.CropResponseDto;
import com.example.agriculture.crop.entity.Crop;
import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;
import com.example.agriculture.crop.mapper.CropMapper;
import com.example.agriculture.crop.repository.CropRepository;
import com.example.agriculture.crop.service.CropService;
import com.example.agriculture.field.entity.Field;
import com.example.agriculture.field.repository.FieldRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CropServiceImpl implements CropService {
	
	private final CropRepository cropRepository;
	private final FieldRepository fieldRepository;
	private final CropMapper cropMapper;

	@Override
	public CropResponseDto createOrUpdate(CropRequestDto dto) 
	{
		log.info("Processing crop create/update request");

		Field field = fieldRepository.findById(dto.getFieldId())
				.orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + dto.getFieldId()));
		Crop crop;

		if (dto.getCropId() == null) 
		{
			log.info("Creating new crop for field ID: {}", dto.getFieldId());

			crop = cropMapper.toEntity(dto);
			crop.setField(field);
		} 
		else 
		{
			log.info("Updating crop with ID: {}", dto.getCropId());

			crop = cropRepository.findById(dto.getCropId())
					.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + dto.getCropId()));

			if (dto.getVersion() == null || !dto.getVersion().equals(crop.getVersion())) 
			{

				log.warn("Optimistic locking failed for crop ID: {}", dto.getCropId());

				throw new ObjectOptimisticLockingFailureException(Crop.class, dto.getCropId());
			}

			cropMapper.updateEntity(dto, crop);
			crop.setField(field);
		}

		Crop savedCrop = cropRepository.save(crop);
		log.info("Crop create/update completed successfully. Crop ID: {}", savedCrop.getCropId());

		return cropMapper.toResponseDto(savedCrop);
	}

	@Override
	public List<CropResponseDto> getAll() 
	{
		log.info("Fetching all crops");

		return cropRepository.findAll().stream().map(cropMapper::toResponseDto).toList();
	}

	@Override
	public CropResponseDto getById(Integer id) 
	{
		log.info("Fetching crop with ID: {}", id);

		Crop crop = cropRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + id));

		return cropMapper.toResponseDto(crop);
	}

	@Override
	public CropResponseDto update(Integer id, CropRequestDto dto) 
	{		
		log.info("Updating crop with ID: {}", id);

		Crop crop = cropRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + id));

		if (dto.getVersion() == null || !dto.getVersion().equals(crop.getVersion()))
		{
			log.warn("Optimistic locking failed for crop ID: {}", id);

			throw new ObjectOptimisticLockingFailureException(Crop.class, id);
		}

		Field field = fieldRepository.findById(dto.getFieldId())
				.orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + dto.getFieldId()));

		cropMapper.updateEntity(dto, crop);
		crop.setField(field);

		Crop updatedCrop = cropRepository.saveAndFlush(crop);

		log.info("Crop updated successfully with ID: {}", id);

		return cropMapper.toResponseDto(updatedCrop);
	}

	@Override
	public CropResponseDto partialUpdate(Integer id, CropPatchDto dto) 
	{
		log.info("Partially updating crop with ID: {}", id);

		Crop crop = cropRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + id));

		cropMapper.patchEntity(dto, crop);

		if (dto.getFieldId() != null) 
		{
			Field field = fieldRepository.findById(dto.getFieldId())
					.orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + dto.getFieldId()));

			crop.setField(field);
		}

		Crop updatedCrop = cropRepository.saveAndFlush(crop);

		log.info("Crop partially updated successfully with ID: {}", id);

		return cropMapper.toResponseDto(updatedCrop);
	}

	@Override
	public void deleteById(Integer id) 
	{
		log.info("Deleting crop with ID: {}", id);

		if (!cropRepository.existsById(id)) 
		{
			throw new IllegalArgumentException("Crop not found with ID: " + id);
		}

		cropRepository.deleteById(id);

		log.info("Crop deleted successfully with ID: {}", id);
	}

	@Override
	public Page<CropResponseDto> getCrops(int pageNo, int pageSize) 
	{
		log.info("Fetching crops with pagination. Page: {}, Size: {}", pageNo, pageSize);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

		return cropRepository.findAll(pageRequest).map(cropMapper::toResponseDto);
	}

	@Override
	public Page<CropResponseDto> getCropsSorted(int pageNo, int pageSize, String field)
	{
		log.info("Fetching crops with pagination and sorting. Page: {}, Size: {}, Field: {}", pageNo, pageSize, field);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(field).ascending());

		return cropRepository.findAll(pageRequest).map(cropMapper::toResponseDto);
	}

	@Override
	public List<CropResponseDto> searchByName(String cropName) 
	{
		log.info("Searching crops by name: {}", cropName);

		return cropRepository.findByCropNameContainingIgnoreCase(cropName).stream().map(cropMapper::toResponseDto)
				.toList();
	}

	@Override
	public List<CropResponseDto> getCropsBySeason(CropSeason season) 
	{
		log.info("Fetching crops by season: {}", season);

		return cropRepository.findBySeason(season).stream().map(cropMapper::toResponseDto).toList();
	}

	@Override
	public List<CropResponseDto> getCropsByStatus(CropStatus status) 
	{
		log.info("Fetching crops by status: {}", status);

		return cropRepository.findByStatus(status).stream().map(cropMapper::toResponseDto).toList();
	}

	@Override
	public List<CropResponseDto> getCropsByField(Integer fieldId) 
	{
		log.info("Fetching crops for field ID: {}", fieldId);

		return cropRepository.findByFieldFieldId(fieldId).stream().map(cropMapper::toResponseDto).toList();
	}

	@Override
	public List<CropResponseDto> getCropsByHarvestDate(LocalDate startDate, LocalDate endDate) 
	{
		log.info("Fetching crops with harvest dates between {} and {}", startDate, endDate);

		return cropRepository.findByExpectedHarvestDateBetween(startDate, endDate).stream().map(cropMapper::toResponseDto).toList();
	}

	@Override
	public long getCropCount() 
	{
		log.info("Fetching total crop count");

		return cropRepository.count();
	}

}
