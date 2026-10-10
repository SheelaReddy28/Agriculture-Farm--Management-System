package com.example.agriculture.field.serviceimpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.agriculture.farm.entity.Farm;
import com.example.agriculture.farm.repository.FarmRepository;
import com.example.agriculture.field.dto.FieldPatchDto;
import com.example.agriculture.field.dto.FieldRequestDto;
import com.example.agriculture.field.dto.FieldResponseDto;
import com.example.agriculture.field.entity.Field;
import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;
import com.example.agriculture.field.mapper.FieldMapper;
import com.example.agriculture.field.repository.FieldRepository;
import com.example.agriculture.field.service.FieldService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FieldServiceImpl implements FieldService {
	private final FieldRepository fieldRepository;
	private final FarmRepository farmRepository;
	private final FieldMapper fieldMapper;

	@Override
	public FieldResponseDto createOrUpdate(FieldRequestDto dto) {
		log.info("Processing field create/update request");
		Farm farm = farmRepository.findById(dto.getFarmId())
				.orElseThrow(() -> new IllegalArgumentException("Farm not found with ID: " + dto.getFarmId()));
		Field field;
		if (dto.getFieldId() == null) {
			log.info("Creating new field for farm ID: {}", dto.getFarmId());
			field = fieldMapper.toEntity(dto);
			field.setFarm(farm);
		} else {
			log.info("Updating field with ID: {}", dto.getFieldId());
			field = fieldRepository.findById(dto.getFieldId())
					.orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + dto.getFieldId()));

			if (dto.getVersion() == null || !dto.getVersion().equals(field.getVersion())) {
				log.warn("Optimistic locking failed for field ID: {}", dto.getFieldId());
				throw new ObjectOptimisticLockingFailureException(Field.class, dto.getFieldId());
			}
			fieldMapper.updateEntity(dto, field);
			field.setFarm(farm);
		}
		Field savedField = fieldRepository.save(field);
		log.info("Field create/update completed successfully. Field ID: {}", savedField.getFieldId());

		return fieldMapper.toResponseDto(savedField);
	}

	@Override
	public List<FieldResponseDto> getAll() {
		log.info("Fetching all fields");

		return fieldRepository.findAll().stream().map(fieldMapper::toResponseDto).toList();
	}

	@Override
	public FieldResponseDto getById(Integer id) {
		log.info("Fetching field with ID: {}", id);

		Field field = fieldRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + id));

		return fieldMapper.toResponseDto(field);
	}

	@Override
	public FieldResponseDto update(Integer id, FieldRequestDto dto) {
		log.info("Updating field with ID: {}", id);
		Field field = fieldRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + id));

		if (dto.getVersion() == null || !dto.getVersion().equals(field.getVersion())) {
			log.warn("Optimistic locking failed for field ID: {}", id);
			throw new ObjectOptimisticLockingFailureException(Field.class, id);
		}

		Farm farm = farmRepository.findById(dto.getFarmId())
				.orElseThrow(() -> new IllegalArgumentException("Farm not found with ID: " + dto.getFarmId()));

		fieldMapper.updateEntity(dto, field);

		field.setFarm(farm);

		Field updatedField = fieldRepository.saveAndFlush(field);

		log.info("Field updated successfully with ID: {}", id);

		return fieldMapper.toResponseDto(updatedField);
	}

	@Override
	public FieldResponseDto partialUpdate(Integer id, FieldPatchDto dto) {
		log.info("Partially updating field with ID: {}", id);
		Field field = fieldRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + id));

		fieldMapper.patchEntity(dto, field);
		if (dto.getFarmId() != null) {
			Farm farm = farmRepository.findById(dto.getFarmId())
					.orElseThrow(() -> new IllegalArgumentException("Farm not found with ID: " + dto.getFarmId()));
			field.setFarm(farm);
		}
		Field updatedField = fieldRepository.saveAndFlush(field);

		log.info("Field partially updated successfully with ID: {}", id);

		return fieldMapper.toResponseDto(updatedField);
	}

	@Override
	public void deleteById(Integer id) {
		log.info("Deleting field with ID: {}", id);
		if (!fieldRepository.existsById(id)) {
			throw new IllegalArgumentException("Field not found with ID: " + id);
		}

		fieldRepository.deleteById(id);

		log.info("Field deleted successfully with ID: {}", id);
	}

	@Override
	public Page<FieldResponseDto> getFields(int pageNo, int pageSize) {
		log.info("Fetching fields with pagination. Page: {}, Size: {}", pageNo, pageSize);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

		return fieldRepository.findAll(pageRequest).map(fieldMapper::toResponseDto);
	}

	@Override
	public Page<FieldResponseDto> getFieldsSorted(int pageNo, int pageSize, String field) {
		log.info("Fetching fields with pagination and sorting. Page: {}, Size: {}, Field: {}", pageNo, pageSize, field);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(field).ascending());

		return fieldRepository.findAll(pageRequest).map(fieldMapper::toResponseDto);
	}

	@Override
	public List<FieldResponseDto> searchByName(String fieldName) {
		log.info("Searching fields by name: {}", fieldName);
		return fieldRepository.findByFieldNameContainingIgnoreCase(fieldName).stream().map(fieldMapper::toResponseDto)
				.toList();

	}
	
	@Override
	public List<FieldResponseDto> getFieldsBySoilType(SoilType soilType) 
	{
        log.info("Fetching fields by soil type: {}", soilType);
		
		return fieldRepository.findBySoilType(soilType).stream().map(fieldMapper::toResponseDto).toList();
		
	}
	
	@Override
	public List<FieldResponseDto> getFieldsByIrrigationType(IrrigationType irrigationType) 
	{
		
		log.info("Fetching fields by irrigation type: {}", irrigationType);

		return fieldRepository.findByIrrigationType(irrigationType).stream().map(fieldMapper::toResponseDto).toList();

	}

	@Override
	public List<FieldResponseDto> getFieldsByFarm(Integer farmId) {
		log.info("Fetching fields for farm ID: {}", farmId);

		return fieldRepository.findByFarmFarmId(farmId).stream().map(fieldMapper::toResponseDto).toList();

	}

	@Override
	public long getFieldCount() {
		log.info("Fetching total field count");
		return fieldRepository.count();
	}

	

	

}
