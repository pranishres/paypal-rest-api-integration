package com.main.persistence.service;

import com.main.dto.CreditCardDTO;
import com.paypal.api.payments.Payment;

public interface PaymentService {
	public Payment createSimplePaypalPayment(String accessToken);
	
	public Payment createCreditCardPayment(String accessToken, String paymentType);

	public void storeCreditCard(String accessToken, CreditCardDTO creditCardDTO);
	
}
