package com.example.agriculture.activity.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.agriculture.activity.dto.ActivityResponseDto;
import com.example.agriculture.activity.service.ActivitySchedulerService;
import com.example.agriculture.activity.service.ActivityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivitySchedulerServiceImpl implements ActivitySchedulerService
{
	private final ActivityService activityService;
	
	@Override
	public void generateActivityReminderReport() 
	{
		long activityCount = activityService.getActivityCount();

        List<ActivityResponseDto> activities = activityService.getAll();

        log.info("===== ACTIVITY REMINDER REPORT =====");
        log.info("Total Activities : {}", activityCount);

        log.info("----- ACTIVITY DETAILS -----");

        for (ActivityResponseDto activity : activities) 
        {

            log.info("Activity ID      : {}", activity.getActivityId());
            log.info("Activity Name    : {}", activity.getActivityName());
            log.info("Activity Type    : {}", activity.getActivityType());
            log.info("Activity Date    : {}", activity.getActivityDate());
            log.info("Description      : {}", activity.getDescription());
            log.info("Cost             : {}", activity.getCost());
            log.info("Crop ID          : {}", activity.getCropId());
            log.info("Crop Name        : {}", activity.getCropName());
            log.info("Created Date     : {}", activity.getCreatedDate());
            log.info("Modified Date    : {}", activity.getLastModifiedDate());
            log.info("Version          : {}", activity.getVersion());

            log.info("-----------------------------------");
        }

        log.info("===================================");
    }
		
		
	}


