package com.example.agriculture.harvest.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.agriculture.harvest.enumtype.HarvestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarvestResponseDto 
{
	
	private Integer harvestId;
    private Integer version;

    private LocalDate harvestDate;
    private Double quantity;
    private String unit;
    private HarvestStatus status;
    private Double sellingPrice;
    private String buyerName;
    private String description;

    private Integer cropId;
    private String cropName;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
