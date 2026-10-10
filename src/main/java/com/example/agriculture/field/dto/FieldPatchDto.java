package com.example.agriculture.field.dto;

import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldPatchDto {
	
	private String fieldName;

	@DecimalMin(value = "0.1", message = "Field area must be greater than 0")
	private Double area;

	private SoilType soilType;

	private IrrigationType irrigationType;

	private Integer farmId;

}
