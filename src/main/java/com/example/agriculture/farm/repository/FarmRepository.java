package com.example.agriculture.farm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.agriculture.farm.dto.FarmProfitabilityDto;
import com.example.agriculture.farm.entity.Farm;
import com.example.agriculture.farm.enumtype.FarmStatus;

public interface FarmRepository extends JpaRepository<Farm, Integer> {
	
	List<Farm> findByFarmNameContainingIgnoreCase(String farmName);

    List<Farm> findByLocationContainingIgnoreCase(String location);

    List<Farm> findByStatus(FarmStatus status);

    List<Farm> findByFarmerFarmerId(Integer farmerId);
    
    @Query(value = """    		
    		SELECT
    	        f.farm_name AS farmName,
    	        COALESCE((
    	            SELECT SUM(e.amount)
    	            FROM expense e
    	            JOIN activity a ON e.activity_id = a.activity_id
    	            JOIN crop c ON a.crop_id = c.crop_id
    	            JOIN field fi ON c.field_id = fi.field_id
    	            WHERE fi.farm_id = f.farm_id
    	        ), 0) AS totalExpense,

    	        COALESCE((
    	            SELECT SUM(h.selling_price * h.quantity)
    	            FROM harvest h
    	            JOIN crop c2 ON h.crop_id = c2.crop_id
    	            JOIN field fi2 ON c2.field_id = fi2.field_id
    	            WHERE fi2.farm_id = f.farm_id
    	        ), 0) AS totalRevenue,

    	        (
    	            COALESCE((
    	                SELECT SUM(h.selling_price * h.quantity)
    	                FROM harvest h
    	                JOIN crop c3 ON h.crop_id = c3.crop_id
    	                JOIN field fi3 ON c3.field_id = fi3.field_id
    	                WHERE fi3.farm_id = f.farm_id
    	            ), 0)
    	            -
    	            COALESCE((
    	                SELECT SUM(e2.amount)
    	                FROM expense e2
    	                JOIN activity a2 ON e2.activity_id = a2.activity_id
    	                JOIN crop c4 ON a2.crop_id = c4.crop_id
    	                JOIN field fi4 ON c4.field_id = fi4.field_id
    	                WHERE fi4.farm_id = f.farm_id
    	            ), 0)
    	        ) AS profit

    	    FROM farm f
    	    """, nativeQuery = true)
    	List<FarmProfitabilityDto> getFarmProfitabilityReport();

}
