package com.example.agriculture.farm.dto;

import com.example.agriculture.farm.enumtype.FarmStatus;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmPatchDto {

	private String farmName;

	private String location;

	@DecimalMin(value = "0.1", message = "Total area must be greater than 0")
	private Double totalArea;

	private FarmStatus status;

	private Integer farmerId;

}
