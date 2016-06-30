package com.main.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.dto.BillingAgreementDTO;
import com.main.persistence.service.BillingAgreementService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/billings/agreements")
public class BillingAgreementController {
	
	@Autowired
	BillingAgreementService billingAgreementService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getDate() {
		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return sdf.format(tomorrow);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{planId}")
	public Agreement createAgreement(@PathVariable("planId") String planId, @RequestBody BillingAgreementDTO billingAgreementDTO)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
		
		Agreement createdAgreement = billingAgreementService.createAgreement(planId, billingAgreementDTO);

		return createdAgreement;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/execute/{token}")
	public Agreement executeAgreement(@PathVariable("token") String token)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {

		return billingAgreementService.executeAgreement(token);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/agreementDetail/{agreementId}")
	public Agreement getById(@PathVariable("agreementId") String token)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
		Agreement agreement = Agreement.get(SessionContext.getAPIContext(), token);

		return agreement;

	}

}
