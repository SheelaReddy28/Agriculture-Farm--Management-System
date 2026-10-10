package com.example.agriculture.field.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.agriculture.field.dto.FieldResponseDto;
import com.example.agriculture.field.service.FieldSchedulerService;
import com.example.agriculture.field.service.FieldService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FieldSchedulerServiceImpl implements FieldSchedulerService
{
	 private final FieldService fieldService;
	
	 @Override
	public void generateFieldMonitoringReport() 
	 {
		 long fieldCount = fieldService.getFieldCount();
	        List<FieldResponseDto> fields = fieldService.getAll();

	        log.info("===== FIELD MONITORING REPORT =====");
	        log.info("Total Fields : {}", fieldCount);

	        log.info("----- FIELD DETAILS -----");

	        for (FieldResponseDto field : fields) {

	            log.info("Field ID        : {}", field.getFieldId());
	            log.info("Field Name      : {}", field.getFieldName());
	            log.info("Area            : {}", field.getArea());
	            log.info("Soil Type       : {}", field.getSoilType());
	            log.info("Irrigation Type : {}", field.getIrrigationType());
	            log.info("Farm ID         : {}", field.getFarmId());
	            log.info("Farm Name       : {}", field.getFarmName());
	            log.info("Created Date    : {}", field.getCreatedDate());
	            log.info("Modified Date   : {}", field.getLastModifiedDate());
	            log.info("Version         : {}", field.getVersion());

	            log.info("-----------------------------------");
	        }

	        log.info("===================================");
	    }
		
	}


