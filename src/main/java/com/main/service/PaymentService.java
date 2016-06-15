package com.main.service;

import com.paypal.api.payments.Payment;

public interface PaymentService {
	public Payment createSimplePaypalPayment(String accessToken);
	
	public Payment createCreditCardPayment(String accessToken);
	
}
