package com.main.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.main.dto.BillingPlanDTO;
import com.main.persistence.service.BillingPlanService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.PlanList;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/billings/plans")
public class BillingPlanController {
	@Autowired
	BillingPlanService billingPlanService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Plan createPlan(@RequestBody BillingPlanDTO billingPlanDTO) throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {

		Plan createdPlan = billingPlanService.createPlan(billingPlanDTO);

		System.out.println(createdPlan);
		return createdPlan;
	}
	
	/**Patching plan (activating)
	 * @param planId
	 * @return Updated plan
	 */
	@RequestMapping(method = RequestMethod.PATCH, value = "/{planId}")
	public Plan activateBillingPlan(@PathVariable("planId") String planId)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {

		return billingPlanService.activatePlan(planId, null);

	}

	/**Note : Only activated plans are listed 
	 * @return Activated Plan List
	 */
	@RequestMapping(method = RequestMethod.GET)
	public PlanList getList() throws PayPalRESTException {
		String state = "active"; // state of plans {ACTIVE, CREATED, INACTIVE}
		HashMap<String, String> containerMap = new HashMap<String, String>();
		containerMap.put("page_size", "5"); // NUMBER OF RECORDS IN EACH CALL
		containerMap.put("status", state);
		containerMap.put("page", ""); // DEFAULT 10
		// containerMap.put("total_required", "YES"); //YES OR NO
		
		/* PlanList planList = billingPlanService.getAllPlans(containerMap); */
		PlanList planList= Plan.list(SessionContext.getAPIContext(), containerMap);
		System.out.println(planList);
		
		return planList;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{planId}")
	public Plan getById(@PathVariable("planId") String planId) throws PayPalRESTException {
		
		try{
			return Plan.get(SessionContext.getAPIContext(), planId);
		}catch(Exception e){
			System.out.println("Unable to get Plan Details : " + e.getMessage());
			return null;
		}
	}

}
