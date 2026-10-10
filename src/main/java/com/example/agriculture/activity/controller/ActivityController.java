package com.example.agriculture.activity.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.agriculture.activity.dto.ActivityPatchDto;
import com.example.agriculture.activity.dto.ActivityRequestDto;
import com.example.agriculture.activity.dto.ActivityResponseDto;
import com.example.agriculture.activity.enumtype.ActivityType;
import com.example.agriculture.activity.service.ActivityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController 
{

	private final ActivityService activityService;

	@Operation(summary = "Create or update activity", description = "Creates a new activity when activityId is null, otherwise updates an existing activity.")
	@ApiResponses({ 		
	@ApiResponse(responseCode = "201", description = "Activity created successfully"),
	@ApiResponse(responseCode = "200", description = "Activity updated successfully"),
	@ApiResponse(responseCode = "400", description = "Invalid activity data"),
	@ApiResponse(responseCode = "404", description = "Crop or activity not found"),
	@ApiResponse(responseCode = "409", description = "Optimistic locking conflict") })
	
	@PostMapping
	public ResponseEntity<ActivityResponseDto> createOrUpdate(@Valid @RequestBody ActivityRequestDto dto)
	{		
		ActivityResponseDto response = activityService.createOrUpdate(dto);

		if (dto.getActivityId() == null) 
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<ActivityResponseDto>> getAll() 
	{
		List<ActivityResponseDto> response = activityService.getAll();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ActivityResponseDto> getById(@PathVariable Integer id) 
	{
		ActivityResponseDto response = activityService.getById(id);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ActivityResponseDto> update(@PathVariable Integer id,@Valid @RequestBody ActivityRequestDto dto) 
	{
		ActivityResponseDto response = activityService.update(id, dto);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ActivityResponseDto> partialUpdate(@PathVariable Integer id,@Valid @RequestBody ActivityPatchDto dto) 
	{
		ActivityResponseDto response = activityService.partialUpdate(id, dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) 
	{
		activityService.deleteById(id);

		return ResponseEntity.ok("Activity record deleted");
	}

	@GetMapping("/page")
	public ResponseEntity<Page<ActivityResponseDto>> getActivities(@RequestParam int pageNo,@RequestParam int pageSize)
	{
		Page<ActivityResponseDto> response = activityService.getActivities(pageNo, pageSize);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/sorted")
	public ResponseEntity<Page<ActivityResponseDto>> getActivitiesSorted(@RequestParam int pageNo,@RequestParam int pageSize, @RequestParam String field) 
	{

		Page<ActivityResponseDto> response = activityService.getActivitiesSorted(pageNo, pageSize, field);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search/name")
	public ResponseEntity<List<ActivityResponseDto>> searchByName(@RequestParam String activityName) 
	{
		List<ActivityResponseDto> response = activityService.searchByName(activityName);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/type")
	public ResponseEntity<List<ActivityResponseDto>> getActivitiesByType(@RequestParam ActivityType activityType)
	{
		List<ActivityResponseDto> response = activityService.getActivitiesByType(activityType);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/crop/{cropId}")
	public ResponseEntity<List<ActivityResponseDto>> getActivitiesByCrop(@PathVariable Integer cropId) 
	{
		List<ActivityResponseDto> response = activityService.getActivitiesByCrop(cropId);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/date")
	public ResponseEntity<List<ActivityResponseDto>> getActivitiesByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate) 
	{
		List<ActivityResponseDto> response = activityService.getActivitiesByDate(startDate, endDate);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getActivityCount()
	{
		long count = activityService.getActivityCount();

		return ResponseEntity.ok(count);
	}

}
