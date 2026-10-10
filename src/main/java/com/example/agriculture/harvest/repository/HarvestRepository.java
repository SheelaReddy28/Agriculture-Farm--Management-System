package com.example.agriculture.harvest.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.agriculture.harvest.entity.Harvest;
import com.example.agriculture.harvest.enumtype.HarvestStatus;

public interface HarvestRepository extends JpaRepository<Harvest, Integer> 
{
	List<Harvest> findByStatus(HarvestStatus status);

	List<Harvest> findByCropCropId(Integer cropId);

	List<Harvest> findByHarvestDateBetween(LocalDate startDate, LocalDate endDate);

	List<Harvest> findByBuyerNameContainingIgnoreCase(String buyerName);
	
	@Query("""
		    SELECT COALESCE(SUM(h.sellingPrice * h.quantity), 0)
		    FROM Harvest h
		    """)
		Double getTotalRevenue();
	
	
}
