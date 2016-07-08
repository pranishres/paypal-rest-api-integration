package com.main.persistence.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.main.dto.BillingAgreementDTO;
import com.main.persistence.entity.BillingAgreement;
import com.paypal.api.payments.Agreement;

public interface BillingAgreementService {
	public Agreement createAgreement(String planId, BillingAgreementDTO billingAgreementDTO);
	
	public Agreement executeAgreement(String token);
	
	public void changeAgreementState(Agreement agreement);
	
	public Page<BillingAgreement> retriveAllBillingAgreements(Pageable pageable);
	
	public List<Agreement> retriveAllAgreements();
}
