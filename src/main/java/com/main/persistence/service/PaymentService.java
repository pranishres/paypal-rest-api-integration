package com.main.persistence.service;

import com.main.persistence.entity.CustomerCreditCard;
import com.paypal.api.payments.Payment;

public interface PaymentService {
	public Payment createSimplePaypalPayment(String accessToken);
	
	public Payment createCreditCardPayment(String accessToken, String paymentType);

	public CustomerCreditCard storeCreditCard(String accessToken, CustomerCreditCard creditCardDTO);
	
	
}
