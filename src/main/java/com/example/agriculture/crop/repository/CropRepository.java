package com.example.agriculture.crop.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.agriculture.crop.entity.Crop;
import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;

public interface CropRepository extends JpaRepository<Crop, Integer> 
{
	List<Crop> findByCropNameContainingIgnoreCase(String cropName);

    List<Crop> findBySeason(CropSeason season);

    List<Crop> findByStatus(CropStatus status);

    List<Crop> findByFieldFieldId(Integer fieldId);

    List<Crop> findByExpectedHarvestDateBetween(LocalDate startDate,LocalDate endDate);

}
