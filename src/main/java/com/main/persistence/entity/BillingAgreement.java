package com.main.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="billing_agreement")
public class BillingAgreement {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="billing_agreement_id")
	private String billingAgreementId;

	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="plan_id")
	private int pId;
	
	@Column(name="token")
	private String token;
	
	@Column(name="state")
	private String state;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
