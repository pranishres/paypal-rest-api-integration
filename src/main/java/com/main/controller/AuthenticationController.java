package com.main.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.main.util.SessionContext;

@RestController
public class AuthenticationController {

	@RequestMapping(value="/{id}",method = RequestMethod.POST)
	public String saveCustomerID(@PathVariable ("id") int id){
		SessionContext.setCustomerId(id);
		System.out.println("SessionContext.setCustomerId(id) : " + SessionContext.getCustomerId());
		
		return "Customer ID : " + SessionContext.getCustomerId();
	}
}
