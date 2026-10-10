package com.example.agriculture.farm.dto;

import java.time.LocalDateTime;

import com.example.agriculture.farm.enumtype.FarmStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmResponseDto {
	
	 private Integer farmId;

	 private Integer version;

	 private String farmName;

	 private String location;

	 private Double totalArea;

	 private FarmStatus status;

	 private Integer farmerId;

	 private String farmerName;

	 private LocalDateTime createdDate;

	 private LocalDateTime lastModifiedDate;
	
	
	

}
