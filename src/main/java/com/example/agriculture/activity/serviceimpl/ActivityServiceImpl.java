package com.example.agriculture.activity.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.agriculture.activity.dto.ActivityPatchDto;
import com.example.agriculture.activity.dto.ActivityRequestDto;
import com.example.agriculture.activity.dto.ActivityResponseDto;
import com.example.agriculture.activity.entity.Activity;
import com.example.agriculture.activity.enumtype.ActivityType;
import com.example.agriculture.activity.mapper.ActivityMapper;
import com.example.agriculture.activity.repository.ActivityRepository;
import com.example.agriculture.activity.service.ActivityService;
import com.example.agriculture.crop.entity.Crop;
import com.example.agriculture.crop.repository.CropRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements ActivityService 
{
	private final ActivityRepository activityRepository;
	private final CropRepository cropRepository;
	private final ActivityMapper activityMapper;

	@Override
	public ActivityResponseDto createOrUpdate(ActivityRequestDto dto)
	{
		log.info("Processing activity create/update request");

		Crop crop = cropRepository.findById(dto.getCropId())
				.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + dto.getCropId()));

		Activity activity;

		if (dto.getActivityId() == null) 
		{
			log.info("Creating new activity for crop ID: {}", dto.getCropId());

			activity = activityMapper.toEntity(dto);
			activity.setCrop(crop);

		} 
		else
		{
			log.info("Updating activity with ID: {}", dto.getActivityId());

			activity = activityRepository.findById(dto.getActivityId()).orElseThrow(
					() -> new IllegalArgumentException("Activity not found with ID: " + dto.getActivityId()));

			if (dto.getVersion() == null || !dto.getVersion().equals(activity.getVersion())) 
			{
				log.warn("Optimistic locking failed for activity ID: {}", dto.getActivityId());

				throw new ObjectOptimisticLockingFailureException(Activity.class, dto.getActivityId());
			}

			activityMapper.updateEntity(dto, activity);
			activity.setCrop(crop);
		}

		Activity savedActivity = activityRepository.save(activity);

		log.info("Activity create/update completed successfully. Activity ID: {}", savedActivity.getActivityId());

		return activityMapper.toResponseDto(savedActivity);
	}

	@Override
	public List<ActivityResponseDto> getAll() 
	{
		log.info("Fetching all activities");

		return activityRepository.findAll().stream().map(activityMapper::toResponseDto).toList();
	}

	@Override
	public ActivityResponseDto getById(Integer id)
	{
		log.info("Fetching activity with ID: {}", id);

		Activity activity = activityRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + id));

		return activityMapper.toResponseDto(activity);
	}

	@Override
	public ActivityResponseDto update(Integer id, ActivityRequestDto dto) 
	{
		log.info("Updating activity with ID: {}", id);

		Activity activity = activityRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + id));

		if (dto.getVersion() == null || !dto.getVersion().equals(activity.getVersion())) 
		{
			log.warn("Optimistic locking failed for activity ID: {}", id);

			throw new ObjectOptimisticLockingFailureException(Activity.class, id);
		}

		Crop crop = cropRepository.findById(dto.getCropId())
				.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + dto.getCropId()));

		activityMapper.updateEntity(dto, activity);
		activity.setCrop(crop);

		Activity updatedActivity = activityRepository.saveAndFlush(activity);

		log.info("Activity updated successfully with ID: {}", id);

		return activityMapper.toResponseDto(updatedActivity);
	}

	@Override
	public ActivityResponseDto partialUpdate(Integer id, ActivityPatchDto dto) 
	{
		log.info("Partially updating activity with ID: {}", id);

		Activity activity = activityRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + id));

		activityMapper.patchEntity(dto, activity);

		if (dto.getCropId() != null) 
		{
			Crop crop = cropRepository.findById(dto.getCropId())
					.orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + dto.getCropId()));

			activity.setCrop(crop);
		}

		Activity updatedActivity = activityRepository.saveAndFlush(activity);

		log.info("Activity partially updated successfully with ID: {}", id);

		return activityMapper.toResponseDto(updatedActivity);
	}

	@Override
	public void deleteById(Integer id) 
	{
		log.info("Deleting activity with ID: {}", id);

		if (!activityRepository.existsById(id))
		{
			throw new IllegalArgumentException("Activity not found with ID: " + id);
		}

		activityRepository.deleteById(id);

		log.info("Activity deleted successfully with ID: {}", id);
	}

	@Override
	public Page<ActivityResponseDto> getActivities(int pageNo, int pageSize) 
	{
		log.info("Fetching activities with pagination. Page: {}, Size: {}", pageNo, pageSize);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

		return activityRepository.findAll(pageRequest).map(activityMapper::toResponseDto);
	}

	@Override
	public Page<ActivityResponseDto> getActivitiesSorted(int pageNo, int pageSize, String field) 
	{
		log.info("Fetching activities with pagination and sorting. Page: {}, Size: {}, Field: {}", pageNo, pageSize,
				field);

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(field).ascending());

		return activityRepository.findAll(pageRequest).map(activityMapper::toResponseDto);
	}

	@Override
	public List<ActivityResponseDto> searchByName(String activityName) 
	{
		log.info("Searching activities by name: {}", activityName);

		return activityRepository.findByActivityNameContainingIgnoreCase(activityName).stream().map(activityMapper::toResponseDto).toList();
	}

	@Override
	public List<ActivityResponseDto> getActivitiesByType(ActivityType activityType)
	{
		log.info("Fetching activities by type: {}", activityType);

		return activityRepository.findByActivityType(activityType).stream().map(activityMapper::toResponseDto).toList();
	}

	@Override
	public List<ActivityResponseDto> getActivitiesByCrop(Integer cropId)
	{
		log.info("Fetching activities for crop ID: {}", cropId);

		return activityRepository.findByCropCropId(cropId).stream().map(activityMapper::toResponseDto).toList();
	}

	@Override
	public List<ActivityResponseDto> getActivitiesByDate(LocalDate startDate, LocalDate endDate) 
	{
		log.info("Fetching activities between {} and {}", startDate, endDate);

		return activityRepository.findByActivityDateBetween(startDate, endDate).stream()
				.map(activityMapper::toResponseDto).toList();
	}

	@Override
	public long getActivityCount() 
	{
		log.info("Fetching total activity count");

		return activityRepository.count();
	}
}
