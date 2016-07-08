package com.main.persistence.service;

import java.util.Map;

import com.main.persistence.entity.IPN;

public interface IPNService {
	public IPN findByIPNId();

	public IPN saveIPN(Map<String, String> map, String transactionType);
}
