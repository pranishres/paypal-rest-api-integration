package com.main.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_credit_card")
public class CustomerCreditCard {

	@Id
	@Column(name="id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="customer_id")
	private int customerId; 
	
	@Column(name="card_number")
	private String cardNumber; 
	
	@Column(name="card_id")
	private String cardId;
	
/*	@Column(name="payer_id")
	private String payerId;*/
	
	@Column(name="first_name")
	private String firstName; 
	
	@Column(name="last_name")
	private String lastName; 
	
	@Column(name="expiry_month")
	private int expiryMonth; 
	
	@Column(name="expiry_year")
	private int expiryYear; 
	
/*	@Column(name="cvv2")
	private int cvv2;*/
	
	@Column(name="card_type")
	private String cardType; 
	
	@Column(name="default_card")
	private int defaultCard;

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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

/*	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}*/

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getDefaultCard() {
		return defaultCard;
	}

	public void setDefaultCard(int defaultCard) {
		this.defaultCard = defaultCard;
	}
	
}
