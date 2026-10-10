package com.example.agriculture.field.dto;

import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldRequestDto {
	
	private Integer fieldId;

    private Integer version;

    @NotBlank(message = "Field name is required")
    private String fieldName;

    @NotNull(message = "Field area is required")
    @DecimalMin(value = "0.1", message = "Field area must be greater than 0")
    private Double area;

    @NotNull(message = "Soil type is required")
    private SoilType soilType;

    @NotNull(message = "Irrigation type is required")
    private IrrigationType irrigationType;

    @NotNull(message = "Farm ID is required")
    private Integer farmId;

}
