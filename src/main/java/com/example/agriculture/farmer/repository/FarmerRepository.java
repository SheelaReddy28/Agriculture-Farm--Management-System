package com.example.agriculture.farmer.repository;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.agriculture.farmer.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Integer>
{
	
	boolean existsByEmail(String email);
	
	List<Farmer> findByFarmerNameContainingIgnoreCase(String farmerName);
	
		 
	 @Query("SELECT f FROM Farmer f WHERE LOWER(f.farmName) = LOWER(:farmName)")
	 List<Farmer> findByFarmNameJPQL(@Param("farmName") String farmName);
	 
	 @Query(value = "SELECT * FROM farmer WHERE email = :email",nativeQuery = true)
	 Optional<Farmer> findByEmailNative(@Param("email") String email);
	 	 	 	
}
