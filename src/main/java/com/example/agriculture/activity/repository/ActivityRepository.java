package com.example.agriculture.activity.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.agriculture.activity.entity.Activity;
import com.example.agriculture.activity.enumtype.ActivityType;

public interface ActivityRepository extends JpaRepository<Activity, Integer> 
{

	List<Activity> findByActivityNameContainingIgnoreCase(String activityName);

	List<Activity> findByActivityType(ActivityType activityType);

	List<Activity> findByCropCropId(Integer cropId);

	List<Activity> findByActivityDateBetween(LocalDate startDate, LocalDate endDate);
}