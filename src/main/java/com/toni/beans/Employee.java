package com.toni.beans;

public class Employee {
	private Integer employeeId;
	private String employeeLastName;
	private String employeeFirstName;
	private String employeeEmail;
	private String employeeSupplierRegNumber;
	private String employeeCstumerRegNumber;
	private boolean selected;
	private Customer customer = new Customer();
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeeSupplierRegNumber() {
		return employeeSupplierRegNumber;
	}
	public void setEmployeeSupplierRegNumber(String employeeSupplierRegNumber) {
		this.employeeSupplierRegNumber = employeeSupplierRegNumber;
	}
	public String getEmployeeCstumerRegNumber() {
		return employeeCstumerRegNumber;
	}
	public void setEmployeeCstumerRegNumber(String employeeCstumerRegNumber) {
		this.employeeCstumerRegNumber = employeeCstumerRegNumber;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
