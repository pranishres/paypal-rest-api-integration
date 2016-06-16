package com.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.main.service.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.OAuthTokenCredential;
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
	public Payment getPayment(@PathVariable("paymentId") String paymentId){
		try {
			return Payment.get(getAccessToken(), paymentId);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**Accept payment from paypal
	 * @return -> payment object
	 */
	@RequestMapping(value="/paypal",method = RequestMethod.POST, produces = "application/JSON")
	public Payment makePaypal(){
		return paymentService.createSimplePaypalPayment(getAccessToken());
	}
	
	/**Accept payment from creditcard
	 * @return -> payment object
	 */
	@RequestMapping(value="/creditcard", method=RequestMethod.POST, produces="application/JSON")
	public Payment makeCreditCardPayment(){
		return paymentService.createCreditCardPayment(getAccessToken());
	}
	
	@RequestMapping(value="/success")
	public String success(){
		return "Success";
	}
	
	/**Getting accesstoken by providing valid clientId and secret
	 * @return
	 */
	private String getAccessToken(){
		String accessToken = "";
		Map<String, String > map = new HashMap<String, String>();
		map.put("mode", "sandbox");

		String clientID = "AYDNebhrsuqiUKPU_ab-tCvGGVkzaxw2y4bIJFIl4rMuCWZsPLQqEsBelM3kjlaB0_Nu-UX-LJQw8l0Z";
		String clientSecret="ENgjkFRgy1yGhal0aobwdF8kLNglkDaDeDItLN-lgQJZV4W1FpNQ27g3FC6TNd1swtroXAdVT390O4C8";
		if(accessToken.equals("") || accessToken==""){
		try {
			accessToken = new OAuthTokenCredential(clientID, clientSecret,map).getAccessToken();
			System.out.println("Yeapee authentication is done : " + accessToken);
			return accessToken;
		} catch (PayPalRESTException e) {
			System.out.println("Cannot make the OAuthentication :" + e.getMessage());
			return accessToken;
		}
		}else return accessToken;
	}
}