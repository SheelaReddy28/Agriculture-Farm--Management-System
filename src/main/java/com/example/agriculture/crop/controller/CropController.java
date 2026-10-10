package com.example.agriculture.crop.controller;

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

import com.example.agriculture.crop.dto.CropPatchDto;
import com.example.agriculture.crop.dto.CropRequestDto;
import com.example.agriculture.crop.dto.CropResponseDto;
import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;
import com.example.agriculture.crop.service.CropService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

	private final CropService cropService;

	@Operation(summary = "Create or update crop", description = "Creates a new crop when cropId is null, otherwise updates an existing crop.")
	@ApiResponses({ 		
	@ApiResponse(responseCode = "201", description = "Crop created successfully"),
	@ApiResponse(responseCode = "200", description = "Crop updated successfully"),
	@ApiResponse(responseCode = "400", description = "Invalid crop data"),
	@ApiResponse(responseCode = "404", description = "Field or crop not found"),
	@ApiResponse(responseCode = "409", description = "Optimistic locking conflict") })
	
	@PostMapping
	public ResponseEntity<CropResponseDto> createOrUpdate(@Valid @RequestBody CropRequestDto dto) 
	{
		CropResponseDto response = cropService.createOrUpdate(dto);

		if (dto.getCropId() == null) 
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<CropResponseDto>> getAll()
	{
		List<CropResponseDto> response = cropService.getAll();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CropResponseDto> getById(@PathVariable Integer id) 
	{
		CropResponseDto response = cropService.getById(id);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CropResponseDto> update(@PathVariable Integer id, @Valid @RequestBody CropRequestDto dto) 
	{
		CropResponseDto response = cropService.update(id, dto);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CropResponseDto> partialUpdate(@PathVariable Integer id,
			@Valid @RequestBody CropPatchDto dto) 
	{
		CropResponseDto response = cropService.partialUpdate(id, dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) 
	{
		cropService.deleteById(id);

		return ResponseEntity.ok("Crop record deleted");
	}

	@GetMapping("/page")
	public ResponseEntity<Page<CropResponseDto>> getCrops(@RequestParam int pageNo, @RequestParam int pageSize) 
	{
		Page<CropResponseDto> response = cropService.getCrops(pageNo, pageSize);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/sorted")
	public ResponseEntity<Page<CropResponseDto>> getCropsSorted(@RequestParam int pageNo, @RequestParam int pageSize,@RequestParam String field) 
	{
		Page<CropResponseDto> response = cropService.getCropsSorted(pageNo, pageSize, field);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search/name")
	public ResponseEntity<List<CropResponseDto>> searchByName(@RequestParam String cropName) 
	{
		List<CropResponseDto> response = cropService.searchByName(cropName);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/season")
	public ResponseEntity<List<CropResponseDto>> getCropsBySeason(@RequestParam CropSeason season) 
	{
		List<CropResponseDto> response = cropService.getCropsBySeason(season);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/status")
	public ResponseEntity<List<CropResponseDto>> getCropsByStatus(@RequestParam CropStatus status) 
	{
		List<CropResponseDto> response = cropService.getCropsByStatus(status);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/field/{fieldId}")
	public ResponseEntity<List<CropResponseDto>> getCropsByField(@PathVariable Integer fieldId) 
	{
		List<CropResponseDto> response = cropService.getCropsByField(fieldId);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/harvest-date")
	public ResponseEntity<List<CropResponseDto>> getCropsByHarvestDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate) 
	{
		List<CropResponseDto> response = cropService.getCropsByHarvestDate(startDate, endDate);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getCropCount() 
	{
		long count = cropService.getCropCount();

		return ResponseEntity.ok(count);
	}

}
