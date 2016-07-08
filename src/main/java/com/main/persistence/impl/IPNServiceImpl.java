package com.main.persistence.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.persistence.entity.IPN;
import com.main.persistence.repo.IPNRepo;
import com.main.persistence.service.IPNService;
import com.main.util.Loger;

@Service
@Transactional
public class IPNServiceImpl implements IPNService {

	private Map<String, String> ipnMap;
	@Autowired
	private IPNRepo ipnRepo;

	@Override
	public IPN findByIPNId() {
		return null;
	}

	@Override
	public IPN saveIPN(Map<String, String> map, String transactionType) {
		IPN ipn = new IPN();
		Loger.startLogger();
		try{
		this.ipnMap = map;

		Loger.addLog("saveIPN() begins");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		ipn.setIpnId(findValue("ipn_track_id"));
		ipn.setCreatedDate(sdf.format(new Date()));
		ipn.setPayerId(findValue("payer_id"));
		ipn.setTransactionType(transactionType);


		ipn.setShippingAmount(Double.parseDouble(findValue("shipping")));
		ipn.setTaxAmount(Double.parseDouble(findValue("tax")));

		if (transactionType.equalsIgnoreCase("web_accept") || transactionType.equalsIgnoreCase("express_checkout")) {
			System.out.println("Web accept");
			ipn.setPaymentStatus(findValue("payment_status"));
			ipn.setPaymentDate(findValue("payment_date"));
			ipn.setPaidAmount(Double.parseDouble(findValue("mc_gross")));
			ipn.setTransactionId(findValue("txn_id"));
			if(transactionType.equalsIgnoreCase("web_accept")){
				ipn.setReferenceId(findValue("receipt_id"));
			}
		} else if (transactionType.equalsIgnoreCase("recurring_payment")) {
			
			System.out.println("Recurring payment");
			ipn.setCurrencyCode(findValue("currency_code"));
			ipn.setPaymentStatus(findValue("payment_status"));
			ipn.setPaymentDate(findValue("payment_date"));
			ipn.setPaidAmount(Double.parseDouble(findValue("mc_gross")));
			ipn.setReferenceId(findValue("recurring_payment_id"));
			ipn.setTransactionId(findValue("txn_id"));
			
			/*This field is kept just to remove error while ipn gets executed. 
			 * Primarily on billing_agreement -> nextBillingDate
			 * */
			ipn.setNextBillingDate(findValue("next_payment_date"));
				
		} else {
			System.out.println("IPN is type of :: " + transactionType);
		}

		ipnRepo.save(ipn);
		
		System.out.println("From impl : IPN " + ipn);
		}catch(Exception e){
			FileWriter fw;
			try {
				fw = new FileWriter ("/media/pranishres/510712721F638E31/Dropbox/ekbana files/Work/Other files/Tasks/Paypal-REST-API/exception.txt", true);
				PrintWriter pw = new PrintWriter (fw);
				e.printStackTrace (pw);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Loger.addLog(e.getMessage());
			System.out.println("Error Message : " + e.getMessage());
			System.out.println("Error : " + e);
		}
		return ipn;
	}

	private String findValue(String key) {
		String value = (String) this.ipnMap.get(key);
		return value;
	}
}
