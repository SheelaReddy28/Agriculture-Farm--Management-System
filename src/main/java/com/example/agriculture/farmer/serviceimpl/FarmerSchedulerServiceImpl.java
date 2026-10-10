package com.example.agriculture.farmer.serviceimpl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.agriculture.farmer.dto.FarmerResponseDto;
import com.example.agriculture.farmer.service.FarmerSchedulerService;
import com.example.agriculture.farmer.service.FarmerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmerSchedulerServiceImpl implements FarmerSchedulerService 
{
	private final FarmerService farmerService;

	@Override
	public void generateFarmerReport() 
	{
		long farmerCount = farmerService.getFarmerCount();

		List<FarmerResponseDto> farmers = farmerService.getAll();

		log.info("===== FARMER REPORT =====");
		log.info("Total Farmers : {}", farmerCount);

		log.info("----- FARMER DETAILS -----");

		for (FarmerResponseDto farmer : farmers)
		{
			log.info("Farmer ID       : {}", farmer.getFarmerId());
			log.info("Farmer Name     : {}", farmer.getFarmerName());
			log.info("Phone Number    : {}", farmer.getPhoneNumber());
			log.info("Email           : {}", farmer.getEmail());
			log.info("Address         : {}", farmer.getAddress());
			log.info("Farm Name       : {}", farmer.getFarmName());
			log.info("Created Date    : {}", farmer.getCreatedDate());
			log.info("Modified Date   : {}", farmer.getLastModifiedDate());
			log.info("Version         : {}", farmer.getVersion());

			log.info("-----------------------------------");
		}

		log.info("===================================");
	}

}
