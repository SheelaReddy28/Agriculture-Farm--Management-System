package com.example.agriculture.crop.dto;

import java.time.LocalDate;

import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropRequestDto {
	
	private Integer cropId;

    private Integer version;

    @NotBlank(message = "Crop name is required")
    private String cropName;

    @NotBlank(message = "Crop variety is required")
    private String variety;

    @NotNull(message = "Crop season is required")
    private CropSeason season;

    @NotNull(message = "Crop status is required")
    private CropStatus status;

    @NotNull(message = "Sowing date is required")
    @FutureOrPresent(message = "Sowing date cannot be in the past")
    private LocalDate sowingDate;

    @NotNull(message = "Expected harvest date is required")
    private LocalDate expectedHarvestDate;

    @NotNull(message = "Field ID is required")
    private Integer fieldId;

}
