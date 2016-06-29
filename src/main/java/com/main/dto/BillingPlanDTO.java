package com.main.dto;

public class BillingPlanDTO {

	private String planName;
	private String planDescription;
	private String planType;
	private String currency;
	private String currenctValue;
	private String payDefName;
	private String payDefFrequency;
	private String payDefFrequencyInterval;
	private String payDefType;
	private String payDefCycles;
	private String merchantPrefAutoBillAmount;
	private String merchantPrefCancelURL;
	private String merchantPrefReturnURL;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrenctValue() {
		return currenctValue;
	}

	public void setCurrenctValue(String currenctValue) {
		this.currenctValue = currenctValue;
	}

	public String getPayDefName() {
		return payDefName;
	}

	public void setPayDefName(String payDefName) {
		this.payDefName = payDefName;
	}

	public String getPayDefFrequency() {
		return payDefFrequency;
	}

	public void setPayDefFrequency(String payDefFrequency) {
		this.payDefFrequency = payDefFrequency;
	}

	public String getPayDefFrequencyInterval() {
		return payDefFrequencyInterval;
	}

	public void setPayDefFrequencyInterval(String payDefFrequencyInterval) {
		this.payDefFrequencyInterval = payDefFrequencyInterval;
	}

	public String getPayDefType() {
		return payDefType;
	}

	public void setPayDefType(String payDefType) {
		this.payDefType = payDefType;
	}

	public String getPayDefCycles() {
		return payDefCycles;
	}

	public void setPayDefCycles(String payDefCycles) {
		this.payDefCycles = payDefCycles;
	}

	public String getMerchantPrefAutoBillAmount() {
		return merchantPrefAutoBillAmount;
	}

	public void setMerchantPrefAutoBillAmount(String merchantPrefAutoBillAmount) {
		this.merchantPrefAutoBillAmount = merchantPrefAutoBillAmount;
	}

	public String getMerchantPrefCancelURL() {
		return merchantPrefCancelURL;
	}

	public void setMerchantPrefCancelURL(String merchantPrefCancelURL) {
		this.merchantPrefCancelURL = merchantPrefCancelURL;
	}

	public String getMerchantPrefReturnURL() {
		return merchantPrefReturnURL;
	}

	public void setMerchantPrefReturnURL(String merchantPrefReturnURL) {
		this.merchantPrefReturnURL = merchantPrefReturnURL;
	}
}
