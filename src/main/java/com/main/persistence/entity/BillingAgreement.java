package com.main.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="billing_agreement")
public class BillingAgreement {
	@Id
	@Column(name="billing_agreement_id")
	private String billingAgreementId;

	@Column(name="customer_id")
	private int customerId;
	
	public String getBillingAgreementId() {
		return billingAgreementId;
	}

	public void setBillingAgreementId(String billingAgreementId) {
		this.billingAgreementId = billingAgreementId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
}
