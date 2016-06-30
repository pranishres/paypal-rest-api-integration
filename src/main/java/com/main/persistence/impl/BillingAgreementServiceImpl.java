package com.main.persistence.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dto.BillingAgreementDTO;
import com.main.persistence.entity.BillingAgreement;
import com.main.persistence.entity.BillingPlan;
import com.main.persistence.repo.BillingAgreementRepo;
import com.main.persistence.repo.BillingPlanRepo;
import com.main.persistence.service.BillingAgreementService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.AgreementDetails;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class BillingAgreementServiceImpl implements BillingAgreementService {
	@Autowired
	BillingAgreementRepo billingAgreementRepo;

	@Autowired
	BillingPlanRepo billingPlanRepo;

	@Override
	public Agreement createAgreement(String planId, BillingAgreementDTO billingAgreementDTO) {

		Agreement agreement = new Agreement();

		agreement.setName(billingAgreementDTO.getAggrementName());
		agreement.setDescription(billingAgreementDTO.getAggrementDescription());

		agreement.setStartDate(billingAgreementDTO.getAggrementStartDate()); // effective
																				// start
																				// date
																				// for
																				// the
		// agreement

		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setEmail(billingAgreementDTO.getPayerEmail());
		payerInfo.setFirstName(billingAgreementDTO.getPayerFirstName());
		payerInfo.setLastName(billingAgreementDTO.getPayerLastName());

		ShippingAddress shippingAddress = new ShippingAddress();

		shippingAddress.setCity(billingAgreementDTO.getShippingCity());
		shippingAddress.setState(billingAgreementDTO.getShippingState());
		shippingAddress.setCountryCode(billingAgreementDTO.getShippingCountryCode());
		shippingAddress.setPostalCode(billingAgreementDTO.getShippingPostalCode());
		shippingAddress.setPhone(billingAgreementDTO.getShippingPhone());
		shippingAddress.setLine1(billingAgreementDTO.getShippingLine1());

		payerInfo.setShippingAddress(shippingAddress);

		Payer payer = new Payer();
		payer.setPayerInfo(payerInfo);
		payer.setPaymentMethod(billingAgreementDTO.getPayerPaymentMethod());

		if (billingAgreementDTO.getPayerPaymentMethod().equals("credit_card")
				|| billingAgreementDTO.getPayerPaymentMethod() == "credit_card") {
			FundingInstrument fundingInstrument = new FundingInstrument();
			fundingInstrument.setCreditCard(getCreditCard());

			List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
			fundingInstrumentList.add(fundingInstrument);
			/* For card payment */
			payer.setFundingInstruments(fundingInstrumentList);
		}

		agreement.setPayer(payer);

		Plan plan = new Plan();
		plan.setId(planId);
		agreement.setPlan(plan);
		agreement.setShippingAddress(shippingAddress);

		Agreement createdAgreement = null;
		try {
			createdAgreement = agreement.create(SessionContext.getAPIContext());
			System.out.println("Agreement ID : " + createdAgreement.getId());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		savePlanAndToken(createdAgreement);
		return createdAgreement;
	}

	private void savePlanAndToken(Agreement createdAgreement) {
		String token = createdAgreement.getToken();
		String planId = createdAgreement.getPlan().getId();
		Plan plan;

		BillingAgreement billingAgreement = new BillingAgreement();

		if (billingPlanRepo.findOneByPlanId(planId) == null) {
			try {
				plan = Plan.get(SessionContext.getAPIContext(), planId);

				BillingPlan billingPlan = new BillingPlan();
				billingPlan.setPlanId(planId);
				billingPlan.setState(plan.getState());
				
				billingPlanRepo.save(billingPlan);
				
			} catch (PayPalRESTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		billingAgreement.setpId(billingPlanRepo.findOneByPlanId(planId).getId());
		billingAgreement.setCustomerId(SessionContext.getCustomerId());
		billingAgreement.setToken(token);

		billingAgreementRepo.save(billingAgreement);

	}

	private CreditCard getCreditCard() {
		CreditCard creditCard = new CreditCard();
		creditCard.setExpireMonth(12);
		creditCard.setExpireYear(2021);
		creditCard.setFirstName("Pranish");
		creditCard.setLastName("Shrestha Credit Card");
		creditCard.setNumber("4032035145786042");
		creditCard.setType("visa");

		return creditCard;
	}

	@Override
	public Agreement executeAgreement(String token) {
		Agreement executedAgreement = null;
		try {
			executedAgreement = Agreement.execute(SessionContext.getAPIContext(), token);
			System.out.println("executedAgreement.getToken() Before calling saveBilllingAgreementID : " + executedAgreement.getToken());

		} catch (Exception e) {
			System.out.println("Unable to execute the agreement : " + e.getMessage());
		}

		saveBillingAgreementId(executedAgreement, token);

		return executedAgreement;
	}

	private void saveBillingAgreementId(Agreement executedAgreement, String token) {
		System.out.println("(executedAgreement.getToken()) :  " + (executedAgreement.getToken()));
		BillingAgreement billingAgreement = billingAgreementRepo.findOneByToken(token);
		
		billingAgreement.setBillingAgreementId(executedAgreement.getId());

		billingAgreementRepo.save(billingAgreement);

	}

}
