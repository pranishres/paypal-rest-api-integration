package com.main.persistence.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.main.dto.BillingAgreementDTO;
import com.main.persistence.service.BillingAgreementService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class BillingAgreementServiceImpl implements BillingAgreementService{

	@Override
	public Agreement createAgreement(String planId, BillingAgreementDTO billingAgreementDTO) {

		Agreement agreement = new Agreement();

		agreement.setName(billingAgreementDTO.getAggrementName());
		agreement.setDescription(billingAgreementDTO.getAggrementDescription());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

		String startDate = sdf.format(tomorrow);

		agreement.setStartDate(startDate); // effective start date for the
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

		// add instruments if applicable

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
		return createdAgreement;
	}
	
}
