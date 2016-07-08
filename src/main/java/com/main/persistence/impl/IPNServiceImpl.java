package com.main.persistence.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.persistence.entity.IPN;
import com.main.persistence.repo.IPNRepo;
import com.main.persistence.service.IPNService;

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
		this.ipnMap = map;

		IPN ipn = new IPN();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		ipn.setIpnId(findValue("ipn_track_id"));
		ipn.setCreatedDate(sdf.format(new Date()));
		ipn.setCurrencyCode(findValue("currency_code"));
		ipn.setPayerId(findValue("payer_id"));
		ipn.setTransactionType(transactionType);
		ipn.setPaidAmount(Double.parseDouble(findValue("mc_gross")));

		ipn.setPaymentDate(findValue("payment_date"));
		ipn.setPaymentStatus(findValue("payment_status"));

		ipn.setShippingAmount(Double.parseDouble(findValue("shipping")));
		ipn.setTaxAmount(Double.parseDouble(findValue("tax")));
		ipn.setTransactionId(findValue("txn_id"));

		if (transactionType.equalsIgnoreCase("web_accept")) {
			ipn.setReferenceId(findValue("receipt_id"));
		} else if(transactionType.equalsIgnoreCase("recurring_payment")){
			ipn.setReferenceId(findValue("recurring_payment_id"));
		}
		
		ipnRepo.save(ipn);
		return ipn;
	}

	private String findValue(String key) {
		String value = (String) this.ipnMap.get(key);
		return value;
	}
}
