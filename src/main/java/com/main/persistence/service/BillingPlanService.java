package com.main.persistence.service;

import java.util.HashMap;

import com.main.dto.BillingPlanDTO;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.PlanList;

public interface BillingPlanService {
	public Plan createPlan(BillingPlanDTO planDTO);
	
	public Plan activatePlan(String planId, BillingPlanDTO billingPlanDTO);
	
	public PlanList getAllPlans(HashMap<String, String> containerMap);
}
