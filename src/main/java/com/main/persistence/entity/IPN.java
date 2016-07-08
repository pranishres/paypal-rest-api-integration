package com.main.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ipn")
public class IPN {

	@Id
	@Column(name = "ipn_id")
	private String ipnId;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "payer_id")
	private String payerId;

	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "paid_amount")
	private double paidAmount;

	@Column(name = "shipping_amount")
	private double shippingAmount;

	@Column(name = "tax_amount")
	private double taxAmount;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "payment_date")
	private String paymentDate;
	
	@Column(name = "currency_code")
	private String currencyCode;

	
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getIpnId() {
		return ipnId;
	}

	public void setIpnId(String ipnId) {
		this.ipnId = ipnId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

}
