package com.example.agriculture.farm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmProfitabilityDto 
{
	private String farmName;

    private Double totalExpense;

    private Double totalRevenue;

    private Double profit;

}
