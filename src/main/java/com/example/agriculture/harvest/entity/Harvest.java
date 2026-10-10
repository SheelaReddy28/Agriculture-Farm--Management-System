package com.example.agriculture.harvest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.agriculture.crop.entity.Crop;
import com.example.agriculture.harvest.enumtype.HarvestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "harvest")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Harvest 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer harvestId;

	@Version
	private Integer version;

	private LocalDate harvestDate;

	private Double quantity;

	private String unit;

	@Enumerated(EnumType.STRING)
	private HarvestStatus status;

	private Double sellingPrice;

	private String buyerName;

	private String description;

	@ManyToOne
	@JoinColumn(name = "crop_id", nullable = false)
	private Crop crop;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
