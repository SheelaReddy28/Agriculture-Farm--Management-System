package com.example.agriculture.field.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.agriculture.farm.entity.Farm;
import com.example.agriculture.field.enumtype.IrrigationType;
import com.example.agriculture.field.enumtype.SoilType;

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
@Table(name = "field")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fieldId;

	@Version
	private Integer version;

	private String fieldName;

	private Double area;

	@Enumerated(EnumType.STRING)
	private SoilType soilType;

	@Enumerated(EnumType.STRING)
	private IrrigationType irrigationType;

	@ManyToOne
	@JoinColumn(name = "farm_id", nullable = false)
	private Farm farm;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
