package com.main.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Event;
import com.paypal.api.payments.EventType;
import com.paypal.api.payments.EventTypeList;
import com.paypal.api.payments.Webhook;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@RestController
public final class PaypaylWebHooks {

	@RequestMapping(value = "/callBackCapture", method = RequestMethod.POST)
	public void webHookEventCapture(@RequestBody String body, @RequestHeader HttpHeaders headers)
			throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		String clientId = "AYDNebhrsuqiUKPU_ab-tCvGGVkzaxw2y4bIJFIl4rMuCWZsPLQqEsBelM3kjlaB0_Nu-UX-LJQw8l0Z";
		String clientSecret = "ENgjkFRgy1yGhal0aobwdF8kLNglkDaDeDItLN-lgQJZV4W1FpNQ27g3FC6TNd1swtroXAdVT390O4C8";
		HashMap<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox"); // when you're live, change "sandbox"

		String accessToken = new OAuthTokenCredential(clientId, clientSecret, sdkConfig).getAccessToken();

		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);

		System.out.println("logging headers : ");

		Map<String, String> headerMap = headers.toSingleValueMap();
		//

		Map<String, String> map = new HashMap<String, String>();
		// for (String key : headerMap.keySet()) {
		// // change key
		// String value = headerMap.get(key);
		// if (key.matches("paypal-.*")) {
		// StringTokenizer tokenizer = new StringTokenizer(key, "-");
		//
		// String newKey = "";
		// int counter = 0;
		// while (tokenizer.hasMoreTokens()) {
		// String token = tokenizer.nextToken();
		// String k = token.substring(0, 1).toUpperCase() + token.substring(1);
		//
		// newKey += (counter != 0) ? "-" : "";
		//
		// newKey += k;
		//
		// counter++;
		// }
		// key = newKey;
		//
		// map.put(key, value);
		// }
		//
		// System.out.println(key + " --> " + value);
		// }

		// boolean isGenuine = Event.validateReceivedEvent(apiContext,
		// headerMap, body);
		// if (isGenuine) {
		System.out.println();
		System.out.println("Capturing webhooks data from paypal webhooks!");
		// String text = request.toString();
		//
		System.out.println(body);
		// }
	}

	@RequestMapping("webhooks/create")
	public Webhook createWebHook() throws PayPalRESTException {
		String url = "https://e2d16944.ngrok.io";
		url += "/PaypalRestClient/";

		String clientId = "AYDNebhrsuqiUKPU_ab-tCvGGVkzaxw2y4bIJFIl4rMuCWZsPLQqEsBelM3kjlaB0_Nu-UX-LJQw8l0Z";
		String clientSecret = "ENgjkFRgy1yGhal0aobwdF8kLNglkDaDeDItLN-lgQJZV4W1FpNQ27g3FC6TNd1swtroXAdVT390O4C8";
		HashMap<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox"); // when you're live, change "sandbox"

		String accessToken = new OAuthTokenCredential(clientId, clientSecret, sdkConfig).getAccessToken();

		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);

		Webhook wbhk = new Webhook();

		wbhk.setUrl(url + "callBackCapture");

		EventType evt = new EventType();
		evt.setName("*");

		List<EventType> evtList = new ArrayList<EventType>();
		evtList.add(evt);

		wbhk.setEventTypes(evtList);

		// Event event= new Event();
		// event.setEventType("*");
		//
		// evt.add
		//
		// evt.setEventTypes(eventTypes);
		//
		//
		//// evnt.add(evt);
		//
		// wbhk.setEventTypes()

		Webhook createdWebHook = wbhk.create(apiContext, wbhk);

		return createdWebHook;

	}

}
