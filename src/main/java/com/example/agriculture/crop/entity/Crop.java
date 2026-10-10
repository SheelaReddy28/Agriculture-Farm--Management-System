package com.example.agriculture.crop.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.agriculture.crop.enumtype.CropSeason;
import com.example.agriculture.crop.enumtype.CropStatus;
import com.example.agriculture.field.entity.Field;

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
@Table(name = "crop")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crop {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cropId;

    @Version
    private Integer version;

    private String cropName;

    private String variety;

    @Enumerated(EnumType.STRING)
    private CropSeason season;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    private LocalDate sowingDate;

    private LocalDate expectedHarvestDate;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
