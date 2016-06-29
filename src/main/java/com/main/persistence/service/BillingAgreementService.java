package com.main.persistence.service;

import com.main.dto.BillingAgreementDTO;
import com.paypal.api.payments.Agreement;

public interface BillingAgreementService {
	public Agreement createAgreement(String planId, BillingAgreementDTO billingAgreementDTO);
}
