package com.example.agriculture.activity.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.agriculture.activity.dto.ActivityPatchDto;
import com.example.agriculture.activity.dto.ActivityRequestDto;
import com.example.agriculture.activity.dto.ActivityResponseDto;
import com.example.agriculture.activity.enumtype.ActivityType;

public interface ActivityService 
{

	ActivityResponseDto createOrUpdate(ActivityRequestDto dto);

	List<ActivityResponseDto> getAll();

	ActivityResponseDto getById(Integer id);

	ActivityResponseDto update(Integer id, ActivityRequestDto dto);

	ActivityResponseDto partialUpdate(Integer id, ActivityPatchDto dto);

	void deleteById(Integer id);

	Page<ActivityResponseDto> getActivities(int pageNo, int pageSize);

	Page<ActivityResponseDto> getActivitiesSorted(int pageNo, int pageSize, String field);

	List<ActivityResponseDto> searchByName(String activityName);

	List<ActivityResponseDto> getActivitiesByType(ActivityType activityType);

	List<ActivityResponseDto> getActivitiesByCrop(Integer cropId);

	List<ActivityResponseDto> getActivitiesByDate(LocalDate startDate, LocalDate endDate);

	long getActivityCount();
}
