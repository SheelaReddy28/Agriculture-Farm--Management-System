package com.example.agriculture.farm.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.agriculture.farm.dto.FarmResponseDto;
import com.example.agriculture.farm.service.FarmSchedulerService;
import com.example.agriculture.farm.service.FarmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmSchedulerServiceImpl implements FarmSchedulerService 
{
	private final FarmService farmService;

	@Override
	public void generateFarmReport() 
	{
		long farmCount = farmService.getFarmCount();

		List<FarmResponseDto> farms = farmService.getAll();

		log.info("===== FARM REPORT =====");
		log.info("Total Farms : {}", farmCount);

		log.info("----- FARM DETAILS -----");

		for (FarmResponseDto farm : farms) 
		{
			log.info("Farm ID         : {}", farm.getFarmId());
			log.info("Farm Name       : {}", farm.getFarmName());
			log.info("Location        : {}", farm.getLocation());
			log.info("Total Area      : {}", farm.getTotalArea());
			log.info("Status          : {}", farm.getStatus());
			log.info("Farmer ID       : {}", farm.getFarmerId());
			log.info("Farmer Name     : {}", farm.getFarmerName());
			log.info("Created Date    : {}", farm.getCreatedDate());
			log.info("Modified Date   : {}", farm.getLastModifiedDate());
			log.info("Version         : {}", farm.getVersion());

			log.info("-----------------------------------");
		}

		log.info("===================================");
	}
}
