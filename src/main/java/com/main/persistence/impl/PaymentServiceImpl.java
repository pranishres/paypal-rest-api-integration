package com.main.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.main.dto.CreditCardDTO;
import com.main.persistence.service.PaymentService;
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

@Service /*
			 * Creates a bean of this class as a service. Required when @Autowired
			 * of the service interface is done
			 */
public class PaymentServiceImpl implements PaymentService {

	String MY_CARD_NO = "4032035145786042";
	String MY_MASTERCARD = "5308170003013017";

	public Payment createSimplePaypalPayment(String accessToken) {

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setReturnUrl("http://localhost:8080/PaypalRestClient/payment/success");
		redirectUrls.setCancelUrl("http://localhost:8080/PaypalRestClient/payment/failure");

		/* Let's you specify a payment amount. */
		Amount amount = new Amount();
		amount.setCurrency("USD");

		/* Total must be equal to sum of shipping, tax and subtotal. */
		amount.setTotal("10");

		/*
		 * A transaction defines the contract of a payment - what is the payment
		 * for and who is fulfilling it. Transaction is created with a `Payee`
		 * and `Amount` types
		 */
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);

		/*
		 * The Payment creation API requires a list of Transaction; add the
		 * created `Transaction` to a List
		 */
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		/*
		 * A resource representing a Payer that funds a payment Use the List of
		 * `FundingInstrument` and the Payment Method as 'credit_card'
		 */
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		/*
		 * A Payment Resource; create one using the above types and intent as
		 * 'sale'
		 */
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setRedirectUrls(redirectUrls);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		Payment createdPayment = null;

		try {

			/*
			 * Pass in a `ApiContext` object to authenticate the call and to
			 * send a unique request id (that ensures idempotency). The SDK
			 * generates a request id if you do not pass one explicitly.
			 */
			APIContext apiContext = new APIContext(accessToken);

			/*
			 * Create a payment by posting to the APIService using a valid
			 * AccessToken The return object contains the status;
			 */
			createdPayment = payment.create(apiContext);

			System.out.println("Created payment with id = " + createdPayment.getId() + " and status = "
					+ createdPayment.getState());
		} catch (PayPalRESTException e) {
			System.out.println("Cannot make the payment from here: " + e.getMessage());
		}
		return createdPayment;

	}

	@Override
	public Payment createCreditCardPayment(String accessToken, String paymentType) {

		CreditCard creditCard = getCreditCard();

		/**
		 * The Payment creation API requires a list of Transaction; add the
		 * created `Transaction` to a List
		 */
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(getTransaction());

		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");

		/**
		 * Pass in a `ApiContext` object to authenticate the call and to send a
		 * unique request id (that ensures idempotency). The SDK generates a
		 * request id if you do not pass one explicitly.
		 */
		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);



		/*
		 * ###FundingInstrument A resource representing a Payeer's funding
		 * instrument. Use a Payer ID (A unique identifier of the payer
		 * generated and provided by the facilitator. This is required when
		 * creating or using a tokenized funding instrument) and the
		 * `CreditCardDetails`
		 */
		FundingInstrument fundingInstrument = new FundingInstrument();

		if (paymentType == "storedCard" || paymentType.equals("storedCard")) {
			/* Now the credit card token will be used to make the payment */
			System.out.println("Stored");
			CreditCardToken creditCardToken = new CreditCardToken();
			creditCardToken.setCreditCardId("CARD-350555972B835351DK5ZAWYQ");
			System.out.println("Credit card id : " + creditCardToken);
			fundingInstrument.setCreditCardToken(creditCardToken);

		} else if (paymentType == "direct" || paymentType.equals("direct")) {
			CreditCard createdCreditCard = null;
			try {
				createdCreditCard = creditCard.create(apiContext);
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

		/*
		 * The Payment creation API requires a list of FundingInstrument; add
		 * the created `FundingInstrument` to a List
		 */
		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		/*
		 * ###Payment A Payment Resource; create one using the above types and
		 * intent as 'sale'
		 */
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(getPayer(fundingInstrumentList));
		payment.setTransactions(transactions);
		Payment createdPayment = null;

		try {
			/*
			 * Create a payment by posting to the APIService using a valid
			 * AccessToken The return object contains the status;
			 */
			createdPayment = payment.create(apiContext);
			System.out.println("Created payment with id = " + createdPayment.getId() + " and status = "
					+ createdPayment.getState());
			
			saveDetails();
			
		} catch (PayPalRESTException e) {
			System.out.println("Cannot make the payment from here: " + e.getMessage());
		}
		return createdPayment;

	}

	@Override
	public void storeCreditCard(String accesstoken, CreditCardDTO creditCardDTO) {
		/**
		 * CREATE credit card api context here
		 */
		
		CreditCard creditCardd = new CreditCard();
		creditCardd.setExpireMonth(creditCardDTO.getExpiryMonth());
		creditCardd.setExpireYear(creditCardDTO.getExpiryYear());
		creditCardd.setFirstName(creditCardDTO.getFirstName());
		creditCardd.setLastName(creditCardDTO.getLastName());
		creditCardd.setNumber(creditCardDTO.getCardNumber());
		creditCardd.setType(creditCardDTO.getCardType());

		CreditCardToken creditCardToken = new CreditCardToken();
		creditCardToken.setCreditCardId(creditCardd.getId());
		
		System.out.println("Credit card obhect : " + creditCardd );
		System.out.println("Credit Card ID : " + creditCardToken.getCreditCardId());
		System.out.println("Credit card ID as it is : " + creditCardd.getId());
	}

	private Address getBillingAddress() {

		/*
		 * Base Address object used as shipping or billing address in a payment.
		 */
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

		/**
		 * ###Transaction A transaction defines the contract of a payment - what
		 * is the payment for and who is fulfilling it. Transaction is created
		 * with a `Payee` and `Amount` types
		 */
		Transaction transaction = new Transaction();
		transaction.setAmount(getAmount());
		transaction.setDescription("This is the payment transaction description.");

		return transaction;
	}

	private Payer getPayer(List<FundingInstrument> fundingInstrumentList) {
		/*
		 * ###Payer A resource representing a Payer that funds a payment Use the
		 * List of `FundingInstrument` and the Payment Method - as 'credit_card'
		 */
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");

		return payer;

	}
	
	private void saveDetails(){
		
	}
}
