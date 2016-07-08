package com.main.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.persistence.entity.CustomerCreditCard;
import com.main.persistence.service.PaymentService;
import com.main.util.SessionContext;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping(value="/payment")

public class PaymentController{ 
	@Autowired					/*Dependency Injection*/
	PaymentService paymentService;
	
	/**Getting details of a particular transaction
	 * @param paymentId -> the id of the transaction that you want to get the details of.
	 * @return -> Payment object
	 */
	@RequestMapping(value="/{paymentId}",method=RequestMethod.GET, produces="application/JSON")
	public Payment getById(@PathVariable("paymentId") String paymentId){
		try {
			return Payment.get(SessionContext.getAccessToken(), paymentId);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**Accept payment from paypal
	 * @return -> payment object
	 */
	@RequestMapping(value="/paypal",method = RequestMethod.POST, produces = "application/JSON")
	public Payment makePaypalPayment(){
		return paymentService.createSimplePaypalPayment(SessionContext.getAccessToken());
	}
	
	/**Accept payment from creditcard
	 * @return -> payment object
	 */
	@RequestMapping(value="/creditcard", method=RequestMethod.POST, produces="application/JSON")
	public Payment makeCreditCardPayment(@RequestParam("paymentType") String paymentType){
		return paymentService.createCreditCardPayment(SessionContext.getAccessToken(), paymentType);
	}
	
	@RequestMapping(value="/paypal/execute", method=RequestMethod.POST, produces="application/JSON")
	public Payment executePayment(){
		Payment payment = new Payment();
		payment.setId("PAY-1JW87731W6492404XK55TKQY");
		Payer payer = new Payer();
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId("4DQ9P2RFZ4SPS");
		try {
			payment.execute(SessionContext.getAPIContext(), paymentExecution);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return payment;
	}

	/** Storing credit card
	 */
	@RequestMapping(value="/storeCard", method=RequestMethod.POST)
	public CustomerCreditCard storeCreditCard(@RequestBody CustomerCreditCard creditCardDto){
		return paymentService.storeCreditCard(SessionContext.getAccessToken(), creditCardDto);
	}
	
}