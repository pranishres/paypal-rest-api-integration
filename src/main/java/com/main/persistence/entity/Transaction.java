package com.main.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transaction")
public class Transaction {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="customer_credit_card_id")
	private int creditCardId;
	
	@Column(name="pay_type")
	private String payType;
	
	@Column(name="amount_currency")
	private String amountCurrency;
	
	@Column(name="details_shipping")
	private float detailsShipping;
	
	@Column(name="details_sub_total")
	private float detailsSubTotal;
	
	@Column(name="details_tax")
	private float detailsTax;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="transaction_description")
	private String transactionDescription;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(int creditCardId) {
		this.creditCardId = creditCardId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAmountCurrency() {
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public float getDetailsShipping() {
		return detailsShipping;
	}

	public void setDetailsShipping(float detailsShipping) {
		this.detailsShipping = detailsShipping;
	}

	public float getDetailsSubTotal() {
		return detailsSubTotal;
	}

	public void setDetailsSubTotal(float detailsSubTotal) {
		this.detailsSubTotal = detailsSubTotal;
	}

	public float getDetailsTax() {
		return detailsTax;
	}

	public void setDetailsTax(float detailsTax) {
		this.detailsTax = detailsTax;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}
}
