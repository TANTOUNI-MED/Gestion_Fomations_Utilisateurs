package com.toni.beans;

public class Customer {
	private Integer customerId;
	private String customerExternalCode;
	private String customerLabel;
	private String customerEmail;
	private String customerPhoneNumber;
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerExternalCode() {
		return customerExternalCode;
	}
	public void setCustomerExternalCode(String customerExternalCode) {
		this.customerExternalCode = customerExternalCode;
	}
	public String getCustomerLabel() {
		return customerLabel;
	}
	public void setCustomerLabel(String customerLabel) {
		this.customerLabel = customerLabel;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
}
