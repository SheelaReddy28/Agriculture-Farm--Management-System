package com.example.agriculture.crop.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.agriculture.crop.dto.CropResponseDto;
import com.example.agriculture.crop.service.CropSchedulerService;
import com.example.agriculture.crop.service.CropService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CropSchedulerServiceImpl implements CropSchedulerService 
{
	private final CropService cropService;

	@Override
	public void generateCropMonitoringReport() {

		long cropCount = cropService.getCropCount();
		List<CropResponseDto> crops = cropService.getAll();

		log.info("===== CROP MONITORING REPORT =====");
		log.info("Total Crops : {}", cropCount);

		log.info("----- CROP DETAILS -----");

		for (CropResponseDto crop : crops) {

			log.info("Crop ID              : {}", crop.getCropId());
			log.info("Crop Name            : {}", crop.getCropName());
			log.info("Variety              : {}", crop.getVariety());
			log.info("Season               : {}", crop.getSeason());
			log.info("Status               : {}", crop.getStatus());
			log.info("Sowing Date          : {}", crop.getSowingDate());
			log.info("Expected Harvest Date: {}", crop.getExpectedHarvestDate());
			log.info("Field ID             : {}", crop.getFieldId());
			log.info("Field Name           : {}", crop.getFieldName());
			log.info("Created Date         : {}", crop.getCreatedDate());
			log.info("Modified Date        : {}", crop.getLastModifiedDate());
			log.info("Version              : {}", crop.getVersion());

			log.info("-----------------------------------");
		}

		log.info("===================================");

	}

}
