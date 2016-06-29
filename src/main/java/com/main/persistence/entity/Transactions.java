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
public class Transactions {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="customer_credit_card_id")
	private String creditCardId;
	
	@Column(name="payment_id")
	private String paymentId;
	
	@Column(name="intent")
	private String intent;
	
	@Column(name="pay_method")
	private String paymentMethod;
	
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

	public String getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(String creditCardId) {
		this.creditCardId = creditCardId;
	}

	
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
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
