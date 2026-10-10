package com.example.agriculture.farmer.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "farmer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Farmer 
{
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer farmerId;

	    @NotBlank
	    private String farmerName;

	    @NotBlank
	    private String phoneNumber;

	    @Email
	    @NotBlank
	    @Column(unique = true, nullable = false)
	    private String email;

	    @NotBlank
	    private String address;

	    @NotBlank
	    private String farmName;
	    
	    @CreatedDate
	    private LocalDateTime createdDate;
	    
	    @LastModifiedDate
	    private LocalDateTime lastModifiedDate;
	    
	    @Version
	    private Integer version;
	}


