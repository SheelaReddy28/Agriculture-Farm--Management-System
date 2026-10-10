package com.example.agriculture.harvest.controller;

import java.time.LocalDate;
import java.util.List;

import com.example.agriculture.harvest.service.HarvestService;

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

import com.example.agriculture.harvest.dto.HarvestPatchDto;
import com.example.agriculture.harvest.dto.HarvestRequestDto;
import com.example.agriculture.harvest.dto.HarvestResponseDto;
import com.example.agriculture.harvest.enumtype.HarvestStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/harvests")
@RequiredArgsConstructor
public class HarvestController 
{
	private final HarvestService harvestService;
	
	@Operation(summary = "Create or update harvest", description = "Creates a new harvest when harvestId is null, otherwise updates an existing harvest.")
	@ApiResponses({ 		
	@ApiResponse(responseCode = "201", description = "Harvest created successfully"),
	@ApiResponse(responseCode = "200", description = "Harvest updated successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid harvest data"),
	@ApiResponse(responseCode = "404", description = "Crop or harvest not found"),
	@ApiResponse(responseCode = "409", description = "Optimistic locking conflict") })

	@PostMapping
	public ResponseEntity<HarvestResponseDto> createOrUpdate(@Valid @RequestBody HarvestRequestDto dto)
	{
		HarvestResponseDto response = harvestService.createOrUpdate(dto);

		if (dto.getHarvestId() == null) 
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<HarvestResponseDto>> getAll() 
	{
		List<HarvestResponseDto> response = harvestService.getAll();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<HarvestResponseDto> getById(@PathVariable Integer id)
	{
		HarvestResponseDto response = harvestService.getById(id);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HarvestResponseDto> update(@PathVariable Integer id,@Valid @RequestBody HarvestRequestDto dto) {

		HarvestResponseDto response = harvestService.update(id, dto);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<HarvestResponseDto> partialUpdate(@PathVariable Integer id,@Valid @RequestBody HarvestPatchDto dto) 
	{
		HarvestResponseDto response = harvestService.partialUpdate(id, dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) 
	{
		harvestService.deleteById(id);

		return ResponseEntity.ok("Harvest record deleted");
	}

	@GetMapping("/page")
	public ResponseEntity<Page<HarvestResponseDto>> getHarvests(@RequestParam int pageNo, @RequestParam int pageSize) 
	{
		Page<HarvestResponseDto> response = harvestService.getHarvests(pageNo, pageSize);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/sorted")
	public ResponseEntity<Page<HarvestResponseDto>> getHarvestsSorted(@RequestParam int pageNo,@RequestParam int pageSize, @RequestParam String field) 
	{
		Page<HarvestResponseDto> response = harvestService.getHarvestsSorted(pageNo, pageSize, field);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/status")
	public ResponseEntity<List<HarvestResponseDto>> getHarvestsByStatus(@RequestParam HarvestStatus status) 
	{
		List<HarvestResponseDto> response = harvestService.getHarvestsByStatus(status);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/crop/{cropId}")
	public ResponseEntity<List<HarvestResponseDto>> getHarvestsByCrop(@PathVariable Integer cropId)
	{
		List<HarvestResponseDto> response = harvestService.getHarvestsByCrop(cropId);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/date")
	public ResponseEntity<List<HarvestResponseDto>> getHarvestsByDate(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate) 
	{
		List<HarvestResponseDto> response = harvestService.getHarvestsByDate(startDate, endDate);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search/buyer")
	public ResponseEntity<List<HarvestResponseDto>> searchByBuyer(@RequestParam String buyerName) 
	{
		List<HarvestResponseDto> response = harvestService.searchByBuyer(buyerName);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getHarvestCount() 
	{
		long count = harvestService.getHarvestCount();

		return ResponseEntity.ok(count);
	}

	@GetMapping("/revenue")
	public ResponseEntity<Double> getTotalRevenue() 
	{
		Double revenue = harvestService.getTotalRevenue();

		return ResponseEntity.ok(revenue);
	}

}
