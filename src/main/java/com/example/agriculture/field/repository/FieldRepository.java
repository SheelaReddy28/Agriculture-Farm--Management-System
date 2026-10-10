package com.example.agriculture.field.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.agriculture.field.entity.Field;
import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;

public interface FieldRepository extends JpaRepository<Field, Integer> 
{
	List<Field> findByFieldNameContainingIgnoreCase(String fieldName);

    List<Field> findBySoilType(SoilType soilType);

    List<Field> findByIrrigationType(IrrigationType irrigationType);

    List<Field> findByFarmFarmId(Integer farmId);

}
