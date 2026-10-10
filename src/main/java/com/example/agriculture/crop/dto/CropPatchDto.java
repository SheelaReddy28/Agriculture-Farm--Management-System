package com.example.agriculture.crop.dto;

import java.time.LocalDate;

import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropPatchDto {
	
	private String cropName;

    private String variety;

    private CropSeason season;

    private CropStatus status;

    @FutureOrPresent(message = "Sowing date cannot be in the past")
    private LocalDate sowingDate;

    private LocalDate expectedHarvestDate;

    private Integer fieldId;

}
