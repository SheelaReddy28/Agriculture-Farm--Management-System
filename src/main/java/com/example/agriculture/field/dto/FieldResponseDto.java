package com.example.agriculture.field.dto;

import java.time.LocalDateTime;

import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldResponseDto {
	
	private Integer fieldId;

    private Integer version;

    private String fieldName;

    private Double area;

    private SoilType soilType;

    private IrrigationType irrigationType;

    private Integer farmId;

    private String farmName;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
