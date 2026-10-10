package com.example.agriculture.farmer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerPatchDto {
	
	private String farmerName;

    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;

    @Email(message = "Enter a valid email address")
    private String email;

    private String address;

    private String farmName;

}
