package com.suyad.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.suyad.entity.CitizenPlan;

import jakarta.transaction.Transactional;

public interface CitizenPlanRepo extends JpaRepository<CitizenPlan, Integer> 
{
	@Modifying
	@Transactional
	@Query(value = "select distinct(PLAN_NAME) from CITIZEN_PLAN ",nativeQuery = true)
	public List<String> getPlanNames();
	
	@Modifying
	@Transactional
	@Query(value ="select distinct(PLAN_STATUS) from CITIZEN_PLAN",nativeQuery = true)
	public List<String> getStatus();
}
