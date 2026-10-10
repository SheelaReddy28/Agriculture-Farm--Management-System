package com.example.agriculture.farm.controller;

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

import com.example.agriculture.farm.dto.FarmProfitabilityDto;
import com.example.agriculture.farm.dto.FarmPatchDto;
import com.example.agriculture.farm.dto.FarmRequestDto;
import com.example.agriculture.farm.dto.FarmResponseDto;
import com.example.agriculture.farm.service.FarmService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {
	
	private final FarmService farmService;
	
	@Operation(summary = "Create or update farm", description = "Creates a new farm when farmId is null, otherwise updates an existing farm.")
	@ApiResponses({ 
	@ApiResponse(responseCode = "201", description = "Farm created successfully"),
	@ApiResponse(responseCode = "200", description = "Farm updated successfully"),
	@ApiResponse(responseCode = "400", description = "Invalid farm data"),
	@ApiResponse(responseCode = "404", description = "Farmer or farm not found"),
	@ApiResponse(responseCode = "409", description = "Optimistic locking conflict") })
	@PostMapping
	public ResponseEntity<FarmResponseDto> createOrUpdate(@Valid @RequestBody FarmRequestDto dto) 
	{
	    FarmResponseDto response = farmService.createOrUpdate(dto);

	    if (dto.getFarmId() == null) 
	    {
	        return ResponseEntity .status(HttpStatus.CREATED) .body(response);
	    }

	    return ResponseEntity.ok(response);	    
	}
	
	@GetMapping
	public ResponseEntity<List<FarmResponseDto>> getAll() 
	{

	    List<FarmResponseDto> response = farmService.getAll();

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FarmResponseDto> getById( @PathVariable Integer id) 
	{

	    FarmResponseDto response = farmService.getById(id);

	    return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<FarmResponseDto> update(@PathVariable Integer id, @Valid @RequestBody FarmRequestDto dto) {

		FarmResponseDto response = farmService.update(id, dto);

		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<FarmResponseDto> partialUpdate(@PathVariable Integer id,@Valid @RequestBody FarmPatchDto dto)
	{

		FarmResponseDto response = farmService.partialUpdate(id, dto);

		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) 
	{
		farmService.deleteById(id);
		
		return ResponseEntity.ok("Farm record deleted");
		
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<FarmResponseDto>> getFarms(@RequestParam int pageNo,@RequestParam int pageSize) 
	{

	    Page<FarmResponseDto> response = farmService.getFarms(pageNo, pageSize);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/sorted")
	public ResponseEntity<Page<FarmResponseDto>> getFarmsSorted(@RequestParam int pageNo,@RequestParam int pageSize,@RequestParam String field) 
	{

	    Page<FarmResponseDto> response = farmService.getFarmsSorted(pageNo, pageSize, field);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search/name")
	public ResponseEntity<List<FarmResponseDto>> searchByName(@RequestParam String farmName) 
	{

	    List<FarmResponseDto> response = farmService.searchByName(farmName);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search/location")
	public ResponseEntity<List<FarmResponseDto>> searchByLocation(@RequestParam String location) 
	{

	    List<FarmResponseDto> response = farmService.searchByLocation(location);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<FarmResponseDto>> getFarmsByStatus(@RequestParam String  status) 
	{

	    List<FarmResponseDto> response = farmService.getFarmsByStatus(status);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/farmer/{farmerId}")
	public ResponseEntity<List<FarmResponseDto>> getFarmsByFarmer(@PathVariable Integer farmerId) 
	{

	    List<FarmResponseDto> response = farmService.getFarmsByFarmer(farmerId);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/count")
	public ResponseEntity<Long> getFarmCount() 
	{

	    long count = farmService.getFarmCount();

	    return ResponseEntity.ok(count);
	}
	
	@GetMapping("/report")
	public ResponseEntity<List<FarmProfitabilityDto>> getFarmProfitabilityReport() 
	{
	    List<FarmProfitabilityDto> report = farmService.getFarmProfitabilityReport();

	    return ResponseEntity.ok(report);
	}










	
	
	
	

}
