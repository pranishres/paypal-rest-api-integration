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
@RequestMapping("/billingAgreements")
public class BillingAgreementController {
	
	@Autowired
	BillingAgreementService billingAgreementService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getDate() {
		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

		return tomorrow.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{planId}")
	public Agreement createAgreement(@PathVariable("planId") String planId, @RequestBody BillingAgreementDTO billingAgreementDTO)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
		
		Agreement createdAgreement2 = billingAgreementService.createAgreement(planId, billingAgreementDTO);

		return createdAgreement2;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/execute/{token}")
	public Agreement executeAgreement(@PathVariable("token") String token)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
/*		String clientId = "AYnFTc-GiQj7woam2EdCMZvasffdjMbK8X7Wa0X1F5LxFxBriAQrlljnXXMIdDWXshV4xkqP7pDsxZEF";
		String clientSecret = "ECe3xZ-S3BRqxtnPsNN0qK6hCA_a9vyOZDSG-iIBMtNjwu7xlQwMc8zy-9u0kryY9xR9OQc-hxz7ednZ";
		HashMap<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox"); // when you're live, change "sandbox"

		String accessToken = new OAuthTokenCredential(clientId, clientSecret, sdkConfig).getAccessToken();
		APIContext apiContext = new APIContext(Sessio);
		apiContext.setConfigurationMap(sdkConfig);*/

		Agreement executedAgreement = Agreement.execute(SessionContext.getAPIContext(), token);

		return executedAgreement;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/agreementDetail/{token}")
	public Agreement agreementDetail(@PathVariable("token") String token)
			throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {
		String clientId = "AYnFTc-GiQj7woam2EdCMZvasffdjMbK8X7Wa0X1F5LxFxBriAQrlljnXXMIdDWXshV4xkqP7pDsxZEF";
		String clientSecret = "ECe3xZ-S3BRqxtnPsNN0qK6hCA_a9vyOZDSG-iIBMtNjwu7xlQwMc8zy-9u0kryY9xR9OQc-hxz7ednZ";
		HashMap<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox"); // when you're live, change "sandbox"

		String accessToken = new OAuthTokenCredential(clientId, clientSecret, sdkConfig).getAccessToken();
		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);

		Agreement agreement = Agreement.get(apiContext, token);

		return agreement;

	}

}
