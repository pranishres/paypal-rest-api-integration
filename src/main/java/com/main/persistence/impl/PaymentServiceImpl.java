package com.main.persistence.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.persistence.entity.CustomerCreditCard;
import com.main.persistence.repo.CustomerCreditCardRepo;
import com.main.persistence.repo.TransactionRepo;
import com.main.persistence.service.PaymentService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.CreditCardToken;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.main.persistence.entity.Transactions;

@Service /*
			 * Creates a bean of this class as a service. Required when @Autowired
			 * of the service interface is done
			 */
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	CustomerCreditCardRepo customerCreditCardRepo;
	
	@Autowired
	TransactionRepo transactionRepo;

	String MY_CARD_NO = "4032035145786042";
	String MY_MASTERCARD = "5308170003013017";

	public Payment createSimplePaypalPayment(String accessToken) {

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setReturnUrl("http://localhost:8080/PaypalRestClient/payment/success");
		redirectUrls.setCancelUrl("http://localhost:8080/PaypalRestClient/payment/failure");

/*		Amount amount = new Amount();
		amount.setCurrency("USD");
		 Total must be equal to sum of shipping, tax and subtotal. 
		amount.setTotal("10");

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);*/

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(getTransaction());

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setRedirectUrls(redirectUrls);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		Payment createdPayment = null;

		try {
			createdPayment = payment.create(SessionContext.getAPIContext());

			System.out.println("Created payment with id = " + createdPayment.getId() + " and status = "
					+ createdPayment.getState());
		} catch (PayPalRESTException e) {
			System.out.println("Cannot make the payment from here: " + e.getMessage());
		}
		return createdPayment;

	}

	@Override
	public Payment createCreditCardPayment(String accessToken, String paymentType) {
		int customerId= SessionContext.getCustomerId();
		
		CreditCard creditCard = getCreditCard();

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(getTransaction());

		/**
		 * ###FundingInstrument A resource representing a Payeer's funding
		 * instrument. Use a Payer ID (A unique identifier of the payer
		 * generated and provided by the facilitator. This is required when
		 * creating or using a tokenized funding instrument) and the
		 * `CreditCardDetails`
		 */
		FundingInstrument fundingInstrument = new FundingInstrument();

		if (paymentType == "storedCard" || paymentType.equals("storedCard")) {
			
			/** Now the credit card token will be used to make the payment */
			System.out.println("Stored");
			
			String creditCardId = customerCreditCardRepo.findOneByCustomerIdAndDefaultCard(customerId, 1).getCardId();
			
			CreditCardToken creditCardToken = new CreditCardToken();
			creditCardToken.setCreditCardId(creditCardId);
			System.out.println("Credit card id : " + creditCardToken);
			fundingInstrument.setCreditCardToken(creditCardToken);

		} else if (paymentType == "direct" || paymentType.equals("direct")) {
			CreditCard createdCreditCard = null;
			try {
				createdCreditCard = creditCard.create(SessionContext.getAPIContext());
			} catch (PayPalRESTException e1) {
				e1.printStackTrace();
			}

			System.out.println("ID : " + createdCreditCard.getId());
			System.out.println("First Name : " + createdCreditCard.getFirstName());

			System.out.println("---First Credit card object----");
			System.out.println("ID : " + creditCard.getId());
			System.out.println("First NAme : " + creditCard.getFirstName());
			System.out.println("Direct");
			fundingInstrument.setCreditCard(creditCard);
		}else{
			System.out.println("Please provide a valid payment Type");
		}

		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(getPayer(fundingInstrumentList));
		payment.setTransactions(transactions);
		Payment createdPayment = null;

		try {
			createdPayment = payment.create(SessionContext.getAPIContext());
			System.out.println("Created payment with id = " + createdPayment.getId() + " and status = "
					+ createdPayment.getState());
			saveDetails(customerId, createdPayment);
			
		} catch (PayPalRESTException e) {
			System.out.println("Cannot make the payment from here: " + e.getMessage());
		}
		return createdPayment;

	}

	@Override
	public CustomerCreditCard storeCreditCard(String accessToken, CustomerCreditCard creditCardDTO) {
		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");

		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);
		
		
		CreditCard creditCard = new CreditCard();
		creditCard.setExpireMonth(creditCardDTO.getExpiryMonth());
		creditCard.setExpireYear(creditCardDTO.getExpiryYear());
		creditCard.setFirstName(creditCardDTO.getFirstName());
		creditCard.setLastName(creditCardDTO.getLastName());
		creditCard.setNumber(creditCardDTO.getCardNumber());
		creditCard.setType(creditCardDTO.getCardType());

		CreditCard createdCreditCard = new CreditCard();
		
		try{
			createdCreditCard = creditCard.create(apiContext);
		}catch(Exception e){
			
		}
		creditCardDTO.setCustomerId(SessionContext.getCustomerId());
		creditCardDTO.setCardId(createdCreditCard.getId());
		
		System.out.println("Credit card object : " + creditCard);
		System.out.println("Credit card ID as it is : " + createdCreditCard.getId());
		
		saveCreditCardDetails(creditCardDTO);
		
		return creditCardDTO;
	}

	/** Storing credit card details to the database
	 * 
	 * @param creditCardDTO
	 */
	private void saveCreditCardDetails(CustomerCreditCard creditCardDTO) {
		try{
		customerCreditCardRepo.save(creditCardDTO);
		}catch(Exception e){
			System.out.println("Could not save credit card details : " + e.getMessage());
		}
	}

	/**Billing address is required just for card payment method
	 * @return
	 */
	private Address getBillingAddress() {
		Address billingAddress = new Address();
		billingAddress.setCity("Johnstown");
		billingAddress.setCountryCode("US");
		billingAddress.setLine1("52 N Main ST");
		billingAddress.setPostalCode("43210");
		billingAddress.setState("OH");

		return billingAddress;
	}

	private CreditCard getCreditCard() {
		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(getBillingAddress());
//		creditCard.setCvv2(111);
		creditCard.setExpireMonth(12);
		creditCard.setExpireYear(2021);
		creditCard.setFirstName("Pranish");
		creditCard.setLastName("Shrestha Credit Card");
		creditCard.setNumber(MY_CARD_NO);
		creditCard.setType("visa");

		return creditCard;
	}

	private Details getDetails() {
		Details details = new Details();
		details.setShipping("1");
		details.setSubtotal("1");
		details.setTax("1");

		return details;
	}

	private Amount getAmount() {
		Amount amount = new Amount();
		amount.setCurrency("USD");
		/** Total must be equal to sum of shipping, tax and subtotal. */
		amount.setTotal("3");
		amount.setDetails(getDetails());

		return amount;
	}

	private Transaction getTransaction() {

		Transaction transaction = new Transaction();
		transaction.setAmount(getAmount());
		transaction.setDescription("This is the payment transaction description.");

		return transaction;
	}

	private Payer getPayer(List<FundingInstrument> fundingInstrumentList) {
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");

		return payer;

	}
	
	private void saveDetails(int customerId ,Payment createdPayment){
		Payer payer = createdPayment.getPayer();
		FundingInstrument fundingInstrument = payer.getFundingInstruments().get(0);
		Transaction transaction = createdPayment.getTransactions().get(0);
		Amount amount = transaction.getAmount();
		Details details = amount.getDetails();
		
		Transactions transactions = new Transactions();
		
		if(fundingInstrument.getCreditCardToken() != null)
		{
			transactions.setCreditCardId(fundingInstrument.getCreditCardToken().getCreditCardId());
		}
		
		transactions.setCustomerId(customerId);
		transactions.setPaymentId(createdPayment.getId());
		transactions.setIntent(createdPayment.getIntent());
		transactions.setPaymentMethod(payer.getPaymentMethod());
		transactions.setAmountCurrency(amount.getCurrency());
		transactions.setDetailsShipping(Float.parseFloat(details.getShipping()));
		transactions.setDetailsSubTotal(Float.parseFloat(details.getSubtotal()));
		transactions.setDetailsTax(Float.parseFloat(details.getTax()));
		transactions.setDate(new Date());
		transactions.setTransactionDescription(transaction.getDescription());
		
		transactionRepo.save(transactions);
	}
}
