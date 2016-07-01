package com.main.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.main.dto.BillingAgreementDTO;
import com.main.persistence.service.BillingAgreementService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.AgreementStateDescriptor;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/billings/agreements")
public class BillingAgreementController {
	
	@Autowired
	BillingAgreementService billingAgreementService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/date")
	public String getDate() {
		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return sdf.format(today);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{planId}")
	public Agreement createAgreement(@PathVariable("planId") String planId, @RequestBody BillingAgreementDTO billingAgreementDTO)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
		
		Agreement createdAgreement = billingAgreementService.createAgreement(planId, billingAgreementDTO);

		return createdAgreement;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/execute/{token}")
	public Agreement executeAgreement(@PathVariable("token") String token)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {

		return billingAgreementService.executeAgreement(token);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{agreementId}")
	public Agreement getById(@PathVariable("agreementId") String token)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
		Agreement agreement = Agreement.get(SessionContext.getAPIContext(), token);
		return agreement;

	}

	@RequestMapping(method=RequestMethod.POST,  value = "/suspend/{agreementId}")
	public Agreement suspendAgreement(@PathVariable("agreementId") String agreementId){
		AgreementStateDescriptor asd = new AgreementStateDescriptor();
		asd.setNote("Suspending the agreement");
		
		Agreement suspendedAgreement = null;
		try {
			Agreement agreement= Agreement.get(SessionContext.getAPIContext(), agreementId);
			agreement.suspend(SessionContext.getAPIContext(),asd);
			suspendedAgreement = Agreement.get(SessionContext.getAPIContext(), agreementId);
			
			billingAgreementService.changeAgreementState(suspendedAgreement);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return suspendedAgreement;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/reactivate/{agreementId}")
	public Agreement reactivateAgreement(@PathVariable("agreementId") String agreementId){
		Agreement reactivatedAgreement = null;
		
		AgreementStateDescriptor asd = new AgreementStateDescriptor();
		asd.setNote("Reactivating the agreement");
		
		try {
		Agreement agreement = Agreement.get(SessionContext.getAPIContext(), agreementId);
			agreement.reActivate(SessionContext.getAPIContext(), asd);
			reactivatedAgreement = Agreement.get(SessionContext.getAPIContext(), agreementId);
			billingAgreementService.changeAgreementState(reactivatedAgreement);
			
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reactivatedAgreement;
	}
	
	@RequestMapping(method=RequestMethod.POST, value ="/cancel/{agreementId}")
	public Agreement cancelAgreement(@PathVariable("agreementId") String agreementId){
		Agreement cancelledAgreement = null;
		
		AgreementStateDescriptor asd = new AgreementStateDescriptor();
		asd.setNote("Cancelling the agreement");
		
		try{
			Agreement agreement = Agreement.get(SessionContext.getAPIContext(), agreementId);
			agreement.cancel(SessionContext.getAPIContext(), asd);
			cancelledAgreement = Agreement.get(SessionContext.getAPIContext(), agreementId);
			billingAgreementService.changeAgreementState(cancelledAgreement);
		}catch(Exception e){
			System.out.println("Unable to make agreement : " + e.getMessage());
		}
		return cancelledAgreement;
	}
}
