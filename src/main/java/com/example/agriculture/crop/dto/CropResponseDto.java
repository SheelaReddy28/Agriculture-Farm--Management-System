package com.example.agriculture.crop.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropResponseDto {
	
	private Integer cropId;

    private Integer version;

    private String cropName;

    private String variety;

    private CropSeason season;

    private CropStatus status;

    private LocalDate sowingDate;

    private LocalDate expectedHarvestDate;

    private Integer fieldId;

    private String fieldName;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
