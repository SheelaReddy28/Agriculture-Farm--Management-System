package com.example.agriculture.harvest.dto;

import java.time.LocalDate;

import com.example.agriculture.harvest.enumtype.HarvestStatus;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarvestPatchDto 
{
	private LocalDate harvestDate;

	@DecimalMin(value = "0.1", message = "Harvest quantity must be greater than 0")
	private Double quantity;

	private String unit;

	private HarvestStatus status;

	@DecimalMin(value = "0.0", message = "Selling price cannot be negative")
	private Double sellingPrice;

	private String buyerName;

	private String description;

	private Integer cropId;

}
