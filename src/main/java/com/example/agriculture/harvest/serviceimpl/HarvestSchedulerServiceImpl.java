package com.example.agriculture.harvest.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.agriculture.harvest.dto.HarvestResponseDto;
import com.example.agriculture.harvest.service.HarvestSchedulerService;
import com.example.agriculture.harvest.service.HarvestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HarvestSchedulerServiceImpl implements HarvestSchedulerService
{
	private final HarvestService harvestService;
	
	@Override
	public void generateHarvestReminderReport() 
	{
		long harvestCount = harvestService.getHarvestCount();

        List<HarvestResponseDto> harvests = harvestService.getAll();

        log.info("===== HARVEST REMINDER REPORT =====");
        log.info("Total Harvests : {}", harvestCount);

        log.info("----- HARVEST DETAILS -----");

        for (HarvestResponseDto harvest : harvests) {

            log.info("Harvest ID       : {}", harvest.getHarvestId());
            log.info("Harvest Date     : {}", harvest.getHarvestDate());
            log.info("Quantity         : {}", harvest.getQuantity());
            log.info("Unit             : {}", harvest.getUnit());
            log.info("Status           : {}", harvest.getStatus());
            log.info("Selling Price    : {}", harvest.getSellingPrice());
            log.info("Buyer Name       : {}", harvest.getBuyerName());
            log.info("Description      : {}", harvest.getDescription());
            log.info("Crop ID          : {}", harvest.getCropId());
            log.info("Crop Name        : {}", harvest.getCropName());
            log.info("Created Date     : {}", harvest.getCreatedDate());
            log.info("Modified Date    : {}", harvest.getLastModifiedDate());
            log.info("Version          : {}", harvest.getVersion());

            log.info("-----------------------------------");
        }

        log.info("===================================");
    }
		
		
	}


