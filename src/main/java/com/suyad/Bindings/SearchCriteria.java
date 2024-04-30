package com.suyad.Bindings;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria 
{
	private String planName;
	
	private String planStatus;
	
	private String gender;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planStartDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate planEndDate;
}
