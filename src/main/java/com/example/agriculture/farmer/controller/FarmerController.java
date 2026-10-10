package com.example.agriculture.farmer.controller;


import java.util.List;

import com.example.agriculture.exception.Constants;

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

import com.example.agriculture.farmer.dto.FarmerPatchDto;
import com.example.agriculture.farmer.dto.FarmerRequestDto;
import com.example.agriculture.farmer.dto.FarmerResponseDto;
import com.example.agriculture.farmer.service.FarmerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {
	
	 private final FarmerService farmerService;
	 
	 @Operation(summary = "Create or update farmer",description = "Creates a new farmer when farmerId is null, otherwise updates an existing farmer.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "201",description = "Farmer created successfully"),
	 @ApiResponse(responseCode = "200",description = "Farmer updated successfully"),
	 @ApiResponse(responseCode = "400",description = "Invalid farmer data"),
     @ApiResponse(responseCode = "404",description = "Farmer not found"),
	 @ApiResponse(responseCode = "409",description = "Email already exists")
	})
	 @PostMapping
	 public ResponseEntity<FarmerResponseDto> createOrUpdate(@Valid @RequestBody FarmerRequestDto dto) 
	 {
		 FarmerResponseDto response = farmerService.createOrUpdate(dto);
		 if (dto.getFarmerId() == null) 
		 {
		        return ResponseEntity.status(HttpStatus.CREATED).body(response);
		 }
		 return ResponseEntity.ok(response);
	 }

	 
	 
	 @Operation(summary = "Get all farmers",description = "Retrieves all registered farmers.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmers retrieved successfully"),
	 @ApiResponse(responseCode = "500",description = "Internal server error")
	 })
	 
	 @GetMapping
	 public ResponseEntity<List<FarmerResponseDto>> getAll()
	 {
		 List<FarmerResponseDto> farmers = farmerService.getAll();
		 return ResponseEntity.ok(farmers);		 
	 }
	 
	 @Operation(summary = "Get farmer by ID",description = "Retrieves a farmer using the farmer ID.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmer retrieved successfully"),
	 @ApiResponse(responseCode = "404",description = "Farmer not found")
		})
	 @GetMapping("/{id}")
	 public ResponseEntity<FarmerResponseDto> getById(@PathVariable Integer id) 
	 {
		 FarmerResponseDto response = farmerService.getById(id);
		 
		 return ResponseEntity.ok(response);		 
	 }
	 
	 @Operation(summary = "Update farmer",description = "Updates all details of an existing farmer using the farmer ID.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmer updated successfully"),
	 @ApiResponse(responseCode = "400",description = "Invalid farmer data"),
	 @ApiResponse(responseCode = "404",description = "Farmer not found"),
	 @ApiResponse(responseCode = "409",description = "Email already exists")
		})
	 @PutMapping("/{id}")
	 public ResponseEntity<FarmerResponseDto> update(@PathVariable Integer id,@Valid @RequestBody FarmerRequestDto dto) 
	 {
		 FarmerResponseDto response = farmerService.update(id, dto);
		 
		 return ResponseEntity.ok(response);
		 
	 }
	 
	 @Operation(summary = "Partially update farmer",description = "Updates only the provided fields of an existing farmer.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmer partially updated successfully"),
	 @ApiResponse(responseCode = "400",description = "Invalid farmer data"),
	 @ApiResponse(responseCode = "404",description = "Farmer not found"),
	 @ApiResponse(responseCode = "409",description = "Email already exists")
		})
	 @PatchMapping("/{id}")
	 public ResponseEntity<FarmerResponseDto> partialUpdate(@PathVariable Integer id,@Valid @RequestBody FarmerPatchDto dto) 
	 {
		 FarmerResponseDto response = farmerService.partialUpdate(id, dto);
		 
		 return ResponseEntity.ok(response);
		 
	 }
	 
	 @Operation(summary = "Delete farmer",description = "Deletes an existing farmer using the farmer ID.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "204",description = "Farmer deleted successfully"),
	 @ApiResponse(responseCode = "404",description = "Farmer not found")
		})
	 @DeleteMapping("/{id}")
	 public ResponseEntity<String> deleteById(@PathVariable Integer id) 
	 {
		 farmerService.deleteById(id);
		 
		 return ResponseEntity.ok(Constants.RECORD_DELETED);		 
	 }
	 
	 @Operation(summary = "Get farmers with pagination",description = "Retrieves farmers page by page using page number and page size.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmers retrieved successfully"),
	 @ApiResponse(responseCode = "400",description = "Invalid pagination parameters")
		})
	 @GetMapping("/page")
	 public ResponseEntity<Page<FarmerResponseDto>> getFarmers(@RequestParam int pageNo,@RequestParam int pageSize) 
	 {
		 Page<FarmerResponseDto> response = farmerService.getFarmers(pageNo, pageSize);
		 
		 return ResponseEntity.ok(response);		 
	 }
	 
	 @Operation(summary = "Get farmers with sorting",description = "Retrieves farmers with pagination and sorting by the specified field.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmers retrieved successfully"),
	 @ApiResponse(responseCode = "400",description = "Invalid pagination or sorting parameters")
		})
	 @GetMapping("/sort")
	 public ResponseEntity<Page<FarmerResponseDto>> getFarmersSorted(@RequestParam int pageNo,@RequestParam int pageSize,@RequestParam String field) 
	 {
		 Page<FarmerResponseDto> response = farmerService.getFarmersSorted(pageNo, pageSize, field);
		 
		 return ResponseEntity.ok(response);		 
	 }
	 
	 @Operation(summary = "Search farmers by name",description = "Searches farmers by name without considering letter case.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Search completed successfully")
		})
	 @GetMapping("/search")
	 public ResponseEntity<List<FarmerResponseDto>> searchByName(@RequestParam String farmerName) 
	 {
		 List<FarmerResponseDto> response = farmerService.searchByName(farmerName);
		 
		 return ResponseEntity.ok(response);		 
	 }
	 
	 @Operation(summary = "Get total farmer count",description = "Returns the total number of registered farmers.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmer count retrieved successfully")
		})
	 @GetMapping("/count")
	 public ResponseEntity<Long> getFarmerCount() 
	 {
		 long count = farmerService.getFarmerCount();
		 
		 return ResponseEntity.ok(count);		 
	 }
	 
	 @Operation(summary = "Find farmers by farm name using JPQL",description = "Retrieves farmers by farm name using a JPQL query.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmers retrieved successfully")
		})
	 @GetMapping("/jpql")
	 public ResponseEntity<List<FarmerResponseDto>> findByFarmNameJPQL(@RequestParam String farmName) 
	 {
		 List<FarmerResponseDto> response = farmerService.findByFarmNameJPQL(farmName);
		 
		 return ResponseEntity.ok(response);		 
	 }
	 
	 @Operation(summary = "Find farmer by email using native query",description = "Retrieves a farmer by email using a native SQL query.")
	 @ApiResponses({
	 @ApiResponse(responseCode = "200",description = "Farmer retrieved successfully"),
	 @ApiResponse(responseCode = "404",description = "Farmer not found")
		})
	 @GetMapping("/native")
	 public ResponseEntity<FarmerResponseDto> findByEmailNative(@RequestParam String email) 
	 {
		 FarmerResponseDto response = farmerService.findByEmailNative(email);
		 
		 return ResponseEntity.ok(response);
	 }


	 

	 
	 
	 
	 

}
