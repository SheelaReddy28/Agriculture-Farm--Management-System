package com.example.agriculture.activity.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.agriculture.activity.enumtype.ActivityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseDto 
{
	private Integer activityId;
	private Integer version;

	private String activityName;
	private ActivityType activityType;
	private LocalDate activityDate;
	private String description;
	private Double cost;

	private Integer cropId;
	private String cropName;

	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

}
