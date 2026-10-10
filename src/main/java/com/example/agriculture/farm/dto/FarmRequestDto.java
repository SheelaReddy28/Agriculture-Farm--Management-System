package com.example.agriculture.farm.dto;

import com.example.agriculture.farm.enumtype.FarmStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmRequestDto {
	
	private Integer farmId;

    private Integer version;

    @NotBlank(message = "Farm name is required")
    private String farmName;

    @NotBlank(message = "Farm location is required")
    private String location;

    @NotNull(message = "Total area is required")
    @DecimalMin(value = "0.1", message = "Total area must be greater than 0")
    private Double totalArea;

    @NotNull(message = "Farm status is required")
    private FarmStatus status;

    @NotNull(message = "Farmer ID is required")
    private Integer farmerId;
	
	

}
