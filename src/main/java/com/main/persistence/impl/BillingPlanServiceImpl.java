package com.main.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dto.BillingPlanDTO;
import com.main.persistence.entity.BillingPlan;
import com.main.persistence.repo.BillingPlanRepo;
import com.main.persistence.service.BillingPlanService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.PaymentDefinition;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.PlanList;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class BillingPlanServiceImpl implements BillingPlanService{
	@Autowired
	BillingPlanRepo billingPlanRepo;
	
	@Override
	public Plan createPlan(BillingPlanDTO billingPlanDTO) {

		Plan plan = new Plan();
		plan.setName(billingPlanDTO.getPlanName());
		plan.setDescription(billingPlanDTO.getPlanDescription());
		plan.setType(billingPlanDTO.getPlanType());

		PaymentDefinition payDefn = new PaymentDefinition();
		payDefn.setName(billingPlanDTO.getPayDefName());

		Currency cur = new Currency();
		cur.setCurrency(billingPlanDTO.getCurrency());
		cur.setValue(billingPlanDTO.getCurrenctValue());
		payDefn.setAmount(cur);

		payDefn.setFrequency(billingPlanDTO.getPayDefFrequency());
		payDefn.setFrequencyInterval(billingPlanDTO.getPayDefFrequencyInterval());
		payDefn.setType(billingPlanDTO.getPayDefType());
		payDefn.setCycles(billingPlanDTO.getPayDefCycles());

		List<PaymentDefinition> payDefnList = new ArrayList<PaymentDefinition>();
		payDefnList.add(payDefn);

		// Optional fields

		// ChargeModels chargeModels = new ChargeModels();
		// chargeModels.setType("SHIPPING");
		// chargeModels.setAmount(cur);

		// List<ChargeModels> chargeModelList = new ArrayList<ChargeModels>();
		// chargeModelList.add(chargeModels);
		// payDefn.setChargeModels(chargeModelList);

		plan.setPaymentDefinitions(payDefnList);

		MerchantPreferences merchantPref = new MerchantPreferences();
		merchantPref.setAutoBillAmount(billingPlanDTO.getMerchantPrefAutoBillAmount());
		merchantPref.setCancelUrl(billingPlanDTO.getMerchantPrefCancelURL());
		merchantPref.setReturnUrl(billingPlanDTO.getMerchantPrefReturnURL());

		plan.setMerchantPreferences(merchantPref);

		Plan createdPlan = null;
		try{
		createdPlan = plan.create(SessionContext.getAPIContext());
		saveBillingPlan(createdPlan);
		
		}catch(Exception e){
			System.out.println("Unable to create plan : "  + e.getMessage());
		}
		return createdPlan;
	}


	private void saveBillingPlan(Plan createdPlan) {
		BillingPlan billingPlan = new BillingPlan();
		billingPlan.setPlanId(createdPlan.getId());
		billingPlan.setState(createdPlan.getState());
		billingPlanRepo.save(billingPlan);
	}


	@Override
	public Plan activatePlan(String planId, BillingPlanDTO planDTO) {
		APIContext apiContext = SessionContext.getAPIContext();
		
		Plan plan = null;
		Plan updatedPlan = null;
		
		try {
			plan = Plan.get(apiContext, planId);
		} catch (PayPalRESTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		plan.setId(planId);

		// plan.setState(state);
		List<Patch> updateList = new ArrayList<Patch>();
		Patch patch = new Patch();
		patch.setPath("/");
		patch.setOp("replace");

		HashMap<String, String> value = new HashMap<String, String>();
		value.put("state", "ACTIVE");
		patch.setValue(value);

		updateList.add(patch);

		try {
			plan.update(apiContext, updateList);
			
			updatedPlan = Plan.get(apiContext, planId);
			
			
			BillingPlan billingPlan = billingPlanRepo.findOneByPlanId(planId);
			billingPlan.setState(updatedPlan.getState());
			billingPlanRepo.save(billingPlan);
			
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Plan updatedPlan = this.planDetail(planId);
		
		return updatedPlan;
	}

	@Override
	public PlanList getAllPlans(HashMap<String, String> containerMap) {
		PlanList planList = new PlanList();
		
		try {
			planList= Plan.list(SessionContext.getAPIContext(), containerMap);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return planList;
	}

	
	
}
