package com.main.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.ipn.IPNMessage;

@RestController

public class IPNListener {
	@RequestMapping(value = "/myIpnListener")
	public void listen(HttpServletRequest request) {

		// For a full list of configuration parameters refer in wiki page.
		// (https://github.com/paypal/sdk-core-java/wiki/SDK-Configuration-Parameters)
		Map<String, String> configurationMap = Configuration.getConfig();
		IPNMessage ipnlistener = new IPNMessage(request, configurationMap);
		boolean isIpnVerified = ipnlistener.validate();
		String transactionType = ipnlistener.getTransactionType();
		Map<String, String> map = ipnlistener.getIpnMap();

		System.out.println("******* IPN (name:value) pair : " + map + "  " + "######### TransactionType : "
				+ transactionType + "  ======== IPN verified : " + isIpnVerified);
	}
}
