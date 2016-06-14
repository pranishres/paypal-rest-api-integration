package com.main.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.main.utils.GenerateAccessToken;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

@RestController
@RequestMapping(value="/payment")

public class PaymentController{ 
	String accessToken="";
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/JSON")
	public Payment getString(){
		System.out.println("started");
/*		InputStream is = PaymentController.class.getResourceAsStream("/sdk_config.properties");

		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", "sandbox");
		String clientID = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
		String clientSecret = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";
	
		String accessToken = new OAuthTokenCredential(clientID, clientSecret, map).getAccessToken();
		String paymentID = "";
				System.out.println("Access tokn : " + accessToken + "fsdf " +clientID + clientSecret);
		Payment.get(accessToken, paymentID);
		System.out.println("after getting : " + accessToken + ":" + paymentID);*/
		 
		InputStream is = PaymentController.class
				.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
			System.out.println("initiialization done");
		} catch (PayPalRESTException e) {
			System.out.println("Paypal Rest Exception : " + e.getMessage());
		}
		
		Map<String, String > map = new HashMap<String, String>();
		map.put("mode", "sandbox");
//		AYDNebhrsuqiUKPU_ab-tCvGGVkzaxw2y4bIJFIl4rMuCWZsPLQqEsBelM3kjlaB0_Nu-UX-LJQw8l0Z
//		ENgjkFRgy1yGhal0aobwdF8kLNglkDaDeDItLN-lgQJZV4W1FpNQ27g3FC6TNd1swtroXAdVT390O4C8
		String clientID = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
		String clientSecret="EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";
		
		try {
			accessToken = new OAuthTokenCredential(clientID, clientSecret,map).getAccessToken();
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot make the OAuthentication :" + e.getMessage());
		}
		
		Payment payment = createPayment();
		
		return payment;
	}
	
	public Payment createPayment(){

		System.out.println("ASFDASDF");
		// ###Address
		// Base Address object used as shipping or billing
		// address in a payment. [Optional]
		Address billingAddress = new Address();
		billingAddress.setCity("Johnstown");
		billingAddress.setCountryCode("US");
		billingAddress.setLine1("52 N Main ST");
		billingAddress.setPostalCode("43210");
		billingAddress.setState("OH");

		// ###CreditCard
		// A resource representing a credit card that can be
		// used to fund a payment.
		CreditCard creditCard = new CreditCard();        
		creditCard.setBillingAddress(billingAddress);
		creditCard.setCvv2(111);
		creditCard.setExpireMonth(11);
		creditCard.setExpireYear(2018);
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
		creditCard.setNumber("5500005555555559");
		creditCard.setType("mastercard");

		// ###Details
		// Let's you specify details of a payment amount.
		Details details = new Details();
		details.setShipping("1");
		details.setSubtotal("5");
		details.setTax("1");

		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("USD");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal("7");
		amount.setDetails(details);

		// ###Transaction
		// A transaction defines the contract of a
		// payment - what is the payment for and who
		// is fulfilling it. Transaction is created with
		// a `Payee` and `Amount` types
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription("This is the payment transaction description.");

		// The Payment creation API requires a list of
		// Transaction; add the created `Transaction`
		// to a List
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// ###FundingInstrument
		// A resource representing a Payeer's funding instrument.
		// Use a Payer ID (A unique identifier of the payer generated
		// and provided by the facilitator. This is required when
		// creating or using a tokenized funding instrument)
		// and the `CreditCardDetails`
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);  

		// The Payment creation API requires a list of
		// FundingInstrument; add the created `FundingInstrument`
		// to a List
		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		// ###Payer
		// A resource representing a Payer that funds a payment
		// Use the List of `FundingInstrument` and the Payment Method
		// as 'credit_card'
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");

		// ###Payment
		// A Payment Resource; create one using
		// the above types and intent as 'sale'
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		Payment createdPayment = null;
		try {
			// ###AccessToken
			// Retrieve the access token from
			// OAuthTokenCredential by passing in
			// ClientID and ClientSecret
			// It is not mandatory to generate Access Token on a per call basis.
			// Typically the access token can be generated once and
			// reused within the expiry window
//			String accessToken = GenerateAccessToken.getAccessToken();

//			String realAccessToken = "A101.kPIsO7eGXhg420XIjnZmPboCS27CeDF6TZjVfGR31f6ja1IotK3e6U-E_k9MwOO5.VDuLz4HOp7U6THjlS1sNwB1pDhu";
			
			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			APIContext apiContext = new APIContext(accessToken);
			// Use this variant if you want to pass in a request id
			// that is meaningful in your application, ideally
			// a order id.
			
			/*
			 * String requestId = Long.toString(System.nanoTime(); APIContext
			 * apiContext = new APIContext(accessToken, requestId ));
			 */

			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			createdPayment = payment.create(apiContext);

			System.out.println("Created payment with id = " + createdPayment.getId()
			+ " and status = " + createdPayment.getState());
		} catch (PayPalRESTException e) {
			System.out.println("Cannot make the payment from here: " + e.getMessage());
		}
		return createdPayment;
	}
}