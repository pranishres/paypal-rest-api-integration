package com.main.persistence.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.main.dto.BillingAgreementDTO;
import com.main.persistence.entity.BillingAgreement;
import com.main.persistence.entity.BillingPlan;
import com.main.persistence.repo.BillingAgreementRepo;
import com.main.persistence.repo.BillingPlanRepo;
import com.main.persistence.repo.CustomerCreditCardRepo;
import com.main.persistence.service.BillingAgreementService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.AgreementDetails;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.CreditCardToken;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class BillingAgreementServiceImpl implements BillingAgreementService {
	@Autowired
	BillingAgreementRepo billingAgreementRepo;

	@Autowired
	BillingPlanRepo billingPlanRepo;

	@Autowired
	CustomerCreditCardRepo customerCreditCardRepo;
	
	@Override
	public Agreement createAgreement(String planId, BillingAgreementDTO billingAgreementDTO) {
		String payMethod = billingAgreementDTO.getPayerPaymentMethod();
		int customerId = SessionContext.getCustomerId();

		Agreement agreement = new Agreement();
		agreement.setName(billingAgreementDTO.getAgreementName());
		agreement.setDescription(billingAgreementDTO.getAgreementDescription());

		agreement.setStartDate(billingAgreementDTO.getAgreementStartDate()); 
		

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

//		payerInfo.setShippingAddress(shippingAddress);

		Payer payer = new Payer();
		payer.setPayerInfo(payerInfo);
		payer.setPaymentMethod(payMethod);

		/*
		 * If payment type for the agreement is done using credit card, funding
		 * instrument is added to payer
		 */
		if (payMethod.equals("credit_card") || payMethod == "credit_card") {
			System.out.println("Paying through credit card ");
			FundingInstrument fundingInstrument = new FundingInstrument();
//			 fundingInstrument.setCreditCard(getCreditCard());
			
			/*Checking if we can get card number from cardId. */
			try{
			fundingInstrument.setCreditCard(getCreditCard());
				String creditCardId = customerCreditCardRepo.findOneByCustomerIdAndDefaultCard(customerId, 1).getCardId();
				
				CreditCardToken creditCardToken = new CreditCardToken();
				creditCardToken.setCreditCardId(creditCardId);
				System.out.println("Credit card id : " + creditCardToken);
//				fundingInstrument.setCreditCardToken(creditCardToken);
			}catch(Exception e){
				System.out.println("Unale to set funding instrument : " + e.getMessage());
			}

			
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

			/**
			 * When using paypal for the agreement, first the agreement is created (status = CREATE) 
			 * and needs to be verified (status = ACTIVE)
			 * 
			 */
			
			if (payMethod.equals("paypal") || payMethod == "paypal") {
				savePlanAndToken(createdAgreement);
			} else if (payMethod.equals("credit_card") || payMethod == "credit_card") {
				saveCardBillingAgreement(createdAgreement, planId);
			}
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

		return createdAgreement;
	}

	private CreditCard getStoredCard() {
		String creditCardId = customerCreditCardRepo.findOneByCustomerIdAndDefaultCard(SessionContext.getCustomerId(), 1).getCardId();
		System.out.println("Credit Card ID : " + creditCardId);	
		
		 CreditCard creditCard = null;
		try {
			creditCard = CreditCard.get(SessionContext.getAPIContext(), creditCardId);
			System.out.println("Credit card :  " + creditCard);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return creditCard;
	}

	private void saveCardBillingAgreement(Agreement createdAgreement, String planId) {

		AgreementDetails agreementDetails = createdAgreement.getAgreementDetails();
		checkForPlanAvailability(planId);

		BillingAgreement billingAgreement = new BillingAgreement();
		billingAgreement.setBillingAgreementId(createdAgreement.getId());
		billingAgreement.setCustomerId(SessionContext.getCustomerId());
		billingAgreement.setpId(billingPlanRepo.findOneByPlanId(planId).getId());
		billingAgreement.setStatus(createdAgreement.getState());
		billingAgreement.setlastPayentDate(agreementDetails.getLastPaymentDate());
		billingAgreement.setNextBillingDate(agreementDetails.getNextBillingDate());
		
//		billingAgreement.setNextBillingDate(createdAgreement.geta);
		System.out.println("Agreement details after creating agreement : " + createdAgreement.getAgreementDetails());
		billingAgreementRepo.save(billingAgreement);
	}

	/**
	 * When an agreement is created, agreement id is not provided. It will be
	 * saved to the database when the agreement is executed.
	 * 
	 * @param createdAgreement
	 */
	private void savePlanAndToken(Agreement createdAgreement) {
		String token = createdAgreement.getToken();
		String planId = createdAgreement.getPlan().getId();

		BillingAgreement billingAgreement = new BillingAgreement();
		checkForPlanAvailability(planId);

		billingAgreement.setpId(billingPlanRepo.findOneByPlanId(planId).getId());
		billingAgreement.setCustomerId(SessionContext.getCustomerId());
		billingAgreement.setToken(token);

		billingAgreementRepo.save(billingAgreement);

	}

	/**
	 * Check if the plan is not in our record. Plan information will be inserted
	 * if it is not in the dataabse
	 * 
	 * @param planId
	 */
	private void checkForPlanAvailability(String planId) {
		Plan plan;
		if (billingPlanRepo.findOneByPlanId(planId) == null) {
			System.out.println("Plan id is null");
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
	}

	private CreditCard getCreditCard() {
		CreditCard creditCard = new CreditCard();
		creditCard.setExpireMonth(12);
		creditCard.setExpireYear(2021);
		creditCard.setFirstName("Pranish");
		creditCard.setLastName("Shrestha");
		creditCard.setNumber("4032035145786042");
		creditCard.setType("visa");

		return creditCard;
	}

	@Override
	public Agreement executeAgreement(String token) {
		Agreement executedAgreement = null;
		try {
			executedAgreement = Agreement.execute(SessionContext.getAPIContext(), token);
		
			System.out.println("executedAgreement.getToken() Before calling saveBilllingAgreementID : "
					+ executedAgreement.getToken());

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
		billingAgreement.setStatus(executedAgreement.getState());

		billingAgreementRepo.save(billingAgreement);

	}

	@Override
	public void changeAgreementState(Agreement agreement) {
		BillingAgreement billingAgreement = billingAgreementRepo.findOneByBillingAgreementId(agreement.getId());
		billingAgreement.setStatus(agreement.getState());
		billingAgreementRepo.save(billingAgreement);
	}

	@Override
	public Page<BillingAgreement> retriveAllBillingAgreements(Pageable pageable) {
		
		return billingAgreementRepo.findAllByCustomerId(SessionContext.getCustomerId(), pageable);
	}

	@Override
	public List<Agreement> retriveAllAgreements() {
		List<BillingAgreement> billingAgreementList = billingAgreementRepo.findAllByCustomerId(SessionContext.getCustomerId());
		List<Agreement> agreementList = new ArrayList<>();
		APIContext apiContext = SessionContext.getAPIContext();
		
		for(int i = 0;  i<billingAgreementList.size(); i++){
			try {
				Agreement agreement = Agreement.get(apiContext, billingAgreementList.get(i).getBillingAgreementId());
				agreementList.add(agreement);
			} catch (PayPalRESTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return agreementList;
	}

}
