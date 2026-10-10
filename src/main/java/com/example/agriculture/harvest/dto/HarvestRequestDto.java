package com.example.agriculture.harvest.dto;

import java.time.LocalDate;

import com.example.agriculture.harvest.enumtype.HarvestStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarvestRequestDto 
{
	private Integer harvestId;
	private Integer version;

	@NotNull(message = "Harvest date is required")
	private LocalDate harvestDate;

	@NotNull(message = "Harvest quantity is required")
	@DecimalMin(value = "0.1", message = "Harvest quantity must be greater than 0")
	private Double quantity;

	@NotBlank(message = "Unit is required")
	private String unit;

	@NotNull(message = "Harvest status is required")
	private HarvestStatus status;

	@NotNull(message = "Selling price is required")
	@DecimalMin(value = "0.0", message = "Selling price cannot be negative")
	private Double sellingPrice;

	@NotBlank(message = "Buyer name is required")
	private String buyerName;

	private String description;

	@NotNull(message = "Crop ID is required")
	private Integer cropId;

}
