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
	
	@Column(name="status")
	private String status;
	
	@Column(name="last_payent_date")
	private String lastPayentDate;
	
	@Column(name="next_billing_date")
	private String nextBillingDate;
	
	@Column(name="amount")
	private  Float amount;
	
	@Column(name="total_paid_amount")
	private Float totalPaidAmount;
	
	@Column(name="outstanding_amount")
	private Float outstandingAmount;
	
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String state) {
		this.status = state;
	}

	public String getlastPayentDate() {
		return lastPayentDate;
	}

	public void setlastPayentDate(String lastPayentDate) {
		this.lastPayentDate = lastPayentDate;
	}

	public String getNextBillingDate() {
		return nextBillingDate;
	}

	public void setNextBillingDate(String nextBillingDate) {
		this.nextBillingDate = nextBillingDate;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(Float totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public Float getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(Float outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
}
