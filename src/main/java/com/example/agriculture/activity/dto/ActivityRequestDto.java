package com.example.agriculture.activity.dto;

import java.time.LocalDate;

import com.example.agriculture.activity.enumtype.ActivityType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRequestDto 
{
	
	private Integer activityId;
	private Integer version;

	@NotBlank(message = "Activity name is required")
	private String activityName;

	@NotNull(message = "Activity type is required")
	private ActivityType activityType;

	@NotNull(message = "Activity date is required")
	private LocalDate activityDate;

	private String description;

	@NotNull(message = "Activity cost is required")
	@DecimalMin(value = "0.0", message = "Activity cost cannot be negative")
	private Double cost;

	@NotNull(message = "Crop ID is required")
	private Integer cropId;

}
