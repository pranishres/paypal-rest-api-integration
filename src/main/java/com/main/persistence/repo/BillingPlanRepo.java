package com.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.persistence.entity.BillingPlan;

public interface BillingPlanRepo extends JpaRepository<BillingPlan,String>{
	public BillingPlan findOneByPlanId(String planId);
	
}
