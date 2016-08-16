package com.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.util.SessionContext;
import com.paypal.api.payments.Sale;
import com.paypal.base.rest.PayPalRESTException;

@RestController
public class testController {

	private Map<String, String> ipnMap;

	@RequestMapping("/test/{str}")
	public String testFunction(@PathVariable("str") String pv) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("a", "Apple");
		map.put("b", "Ball");
		map.put("c", "Cat");
		map.put("d", "Dog");
		this.ipnMap = map;

		return findValue(pv);
	}

	private String findValue(String key) {
		String value = (String) this.ipnMap.get(key);
		return value;
	}

	/*
	 * Sales Details for retrieving payment id
	 */
	@RequestMapping("/transactionDetail/{str}")
	public Sale transactionTest(@PathVariable("str") String saleId) throws PayPalRESTException {

		Sale sale = new Sale();
		sale = Sale.get(SessionContext.getAPIContext(), saleId);
		return sale;
		// create(SessionContext.getAPIContext());

	}
}
