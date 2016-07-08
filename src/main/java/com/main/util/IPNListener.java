package com.main.util;

import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.persistence.service.IPNService;
import com.paypal.ipn.IPNMessage;

@RestController

public class IPNListener {
	@Autowired
	private IPNService ipnService;

	@RequestMapping(value = "/myIpnListener")
	public void listen(HttpServletRequest request) {
		Loger.startLogger();
		// For a full list of configuration parameters refer in wiki page.
		// (https://github.com/paypal/sdk-core-java/wiki/SDK-Configuration-Parameters)
		Map<String, String> configurationMap = Configuration.getConfig();
		IPNMessage ipnlistener = new IPNMessage(request, configurationMap);
		boolean isIpnVerified = ipnlistener.validate();
		String transactionType = ipnlistener.getTransactionType();
		Map<String, String> map = ipnlistener.getIpnMap();
		
		Loger.addLog(map.toString());
		
		System.out.println("******* IPN (name:value) pair : " + map + "  " + "######### TransactionType : "
				+ transactionType + "  ======== IPN verified : " + isIpnVerified);

		if (isIpnVerified) {
			System.out.println("Verified IPN");
			ipnService.saveIPN(map, transactionType);
		}
		else{
			
			System.out.println("Not a valid IPN Request!");
		}
	}
}
