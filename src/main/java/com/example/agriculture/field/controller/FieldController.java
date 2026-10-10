package com.example.agriculture.field.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.agriculture.field.dto.FieldPatchDto;
import com.example.agriculture.field.dto.FieldRequestDto;
import com.example.agriculture.field.dto.FieldResponseDto;
import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;
import com.example.agriculture.field.service.FieldService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController 
{
	
	private final FieldService fieldService;
	
	@Operation(summary = "Create or update field", description = "Creates a new field when fieldId is null, otherwise updates an existing field.")
	@ApiResponses({ 
	@ApiResponse(responseCode = "201", description = "Field created successfully"),
	@ApiResponse(responseCode = "200", description = "Field updated successfully"),
	@ApiResponse(responseCode = "400", description = "Invalid field data"),
	@ApiResponse(responseCode = "404", description = "Farm or field not found"),
	@ApiResponse(responseCode = "409", description = "Optimistic locking conflict") })
	
	@PostMapping
	public ResponseEntity<FieldResponseDto> createOrUpdate(@Valid @RequestBody FieldRequestDto dto) 
	{
		FieldResponseDto response = fieldService.createOrUpdate(dto);

		if (dto.getFieldId() == null) 
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<FieldResponseDto>> getAll() 
	{
		List<FieldResponseDto> response = fieldService.getAll();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FieldResponseDto> getById(@PathVariable Integer id) 
	{
		FieldResponseDto response = fieldService.getById(id);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<FieldResponseDto> update(@PathVariable Integer id, @Valid @RequestBody FieldRequestDto dto) 
	{
		FieldResponseDto response = fieldService.update(id, dto);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<FieldResponseDto> partialUpdate(@PathVariable Integer id,@Valid @RequestBody FieldPatchDto dto) 
	{
		FieldResponseDto response = fieldService.partialUpdate(id, dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) 
	{
		fieldService.deleteById(id);

		return ResponseEntity.ok("Field record deleted");
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<FieldResponseDto>> getFields(@RequestParam int pageNo,@RequestParam int pageSize) 
	{
	    Page<FieldResponseDto> response = fieldService.getFields(pageNo, pageSize);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/sorted")
	public ResponseEntity<Page<FieldResponseDto>> getFieldsSorted(@RequestParam int pageNo,@RequestParam int pageSize,@RequestParam String field) 
	{
	    Page<FieldResponseDto> response = fieldService.getFieldsSorted(pageNo, pageSize, field);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search/name")
	public ResponseEntity<List<FieldResponseDto>> searchByName(@RequestParam String fieldName) 
	{
	    List<FieldResponseDto> response = fieldService.searchByName(fieldName);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/soil-type")
	public ResponseEntity<List<FieldResponseDto>> getFieldsBySoilType(@RequestParam SoilType soilType) 
	{
	    List<FieldResponseDto> response = fieldService.getFieldsBySoilType(soilType);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/irrigation-type")
	public ResponseEntity<List<FieldResponseDto>> getFieldsByIrrigationType(@RequestParam IrrigationType irrigationType) 
	{
	    List<FieldResponseDto> response = fieldService.getFieldsByIrrigationType(irrigationType);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/farm/{farmId}")
	public ResponseEntity<List<FieldResponseDto>> getFieldsByFarm(@PathVariable Integer farmId) 
	{
	    List<FieldResponseDto> response = fieldService.getFieldsByFarm(farmId);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/count")
	public ResponseEntity<Long> getFieldCount() 
	{
	    long count = fieldService.getFieldCount();

	    return ResponseEntity.ok(count);
	}

}
