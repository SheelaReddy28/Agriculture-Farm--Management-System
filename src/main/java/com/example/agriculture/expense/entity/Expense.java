package com.example.agriculture.expense.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.agriculture.activity.entity.Activity;
import com.example.agriculture.expense.enumtype.ExpenseType;

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
@Table(name = "expense")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer expenseId;

	@Version
	private Integer version;

	private String expenseName;

	@Enumerated(EnumType.STRING)
	private ExpenseType expenseType;

	private LocalDate expenseDate;

	private Double amount;

	private String description;

	@ManyToOne
	@JoinColumn(name = "activity_id", nullable = false)
	private Activity activity;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
