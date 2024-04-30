package com.suyad.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CitizenPlan 
{
	@Id
	@GeneratedValue
	private Integer citizenId;
	
	private String name;
	
	private String email;
	
	private Long phno;
	
	private Long ssn;
	
	private String gender;
	
	private String planName;
	
	private String planStatus;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planStartDate;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planEndDate;

	public CitizenPlan(String name, String email, Long phno, Long ssn, String gender, String planName,
			String planStatus, LocalDate planStartDate, LocalDate planEndDate) 
	{
		this.name = name;
		this.email = email;
		this.phno = phno;
		this.ssn = ssn;
		this.gender = gender;
		this.planName = planName;
		this.planStatus = planStatus;
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
	}

	
	
}
