package com.example.agriculture.activity.dto;

import java.time.LocalDate;

import com.example.agriculture.activity.enumtype.ActivityType;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityPatchDto 
{
	private String activityName;
    private ActivityType activityType;
    private LocalDate activityDate;
    private String description;

    @DecimalMin(value = "0.0", message = "Activity cost cannot be negative")
    private Double cost;

    private Integer cropId;

}
