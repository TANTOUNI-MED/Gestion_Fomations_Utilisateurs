package com.toni.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Request {
	private Integer requestId;
	private String requestNumber;
	private Date requestDate;
	private String requestStatusLabel;
	private String requestStatusColor;
	private boolean showAcceptButton;
	private boolean showActionsButtons;
	private Formation formation = new Formation();
	private Customer customer = new Customer();
	private List<Employee> employees = new ArrayList<Employee>();
	
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestStatusLabel() {
		return requestStatusLabel;
	}
	public void setRequestStatusLabel(String requestStatusLabel) {
		this.requestStatusLabel = requestStatusLabel;
	}
	public String getRequestStatusColor() {
		return requestStatusColor;
	}
	public void setRequestStatusColor(String requestStatusColor) {
		this.requestStatusColor = requestStatusColor;
	}
	public boolean isShowAcceptButton() {
		return showAcceptButton;
	}
	public void setShowAcceptButton(boolean showAcceptButton) {
		this.showAcceptButton = showAcceptButton;
	}
	public boolean isShowActionsButtons() {
		return showActionsButtons;
	}
	public void setShowActionsButtons(boolean showActionsButtons) {
		this.showActionsButtons = showActionsButtons;
	}
	public Formation getFormation() {
		return formation;
	}
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
