package com.example.agriculture.farmer.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerResponseDto {
	
    private Integer farmerId;

    private String farmerName;

    private String phoneNumber;

    private String email;

    private String address;

    private String farmName;
    
    private Integer version;
    
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;


}
