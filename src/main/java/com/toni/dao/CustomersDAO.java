package com.toni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Customer;

public class CustomersDAO {
	private static CustomersDAO INSTANCE;
	
	private CustomersDAO() {
		
	}
	
	public static CustomersDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new CustomersDAO();
		}
		return INSTANCE;
	}
	
	public List<Customer> getCustomers() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Customer> customers = new ArrayList<Customer>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("select * from customers");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer customerId = resultSet.getInt("customer_id");
			String customerExternalCode = resultSet.getString("customer_external_code");
			String customerLabel = resultSet.getString("customer_label");
			String customerEmail = resultSet.getString("customer_email");
			String customerPhoneNumber = resultSet.getString("customer_phone_number");
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerExternalCode(customerExternalCode);
			customer.setCustomerLabel(customerLabel);
			customer.setCustomerEmail(customerEmail);
			customer.setCustomerPhoneNumber(customerPhoneNumber);
			customers.add(customer);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return customers;
	}
	
	public Customer findCustomerByCustomerId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("select * from customers where customer_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer customerId = resultSet.getInt("customer_id");
			String customerExternalCode = resultSet.getString("customer_external_code");
			String customerLabel = resultSet.getString("customer_label");
			String customerEmail = resultSet.getString("customer_email");
			String customerPhoneNumber = resultSet.getString("customer_phone_number");
			
			customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerExternalCode(customerExternalCode);
			customer.setCustomerLabel(customerLabel);
			customer.setCustomerEmail(customerEmail);
			customer.setCustomerPhoneNumber(customerPhoneNumber);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return customer;
	}
	
	public Customer findCustomerByEmailOrExternalCodeOrLabel(String email, String externalCode, String label) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("select * from customers where customer_email=? or customer_external_code=? or customer_label=?");
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, externalCode);
		preparedStatement.setString(3, label);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer customerId = resultSet.getInt("customer_id");
			String customerExternalCode = resultSet.getString("customer_external_code");
			String customerLabel = resultSet.getString("customer_label");
			String customerEmail = resultSet.getString("customer_email");
			String customerPhoneNumber = resultSet.getString("customer_phone_number");
			
			customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerExternalCode(customerExternalCode);
			customer.setCustomerLabel(customerLabel);
			customer.setCustomerEmail(customerEmail);
			customer.setCustomerPhoneNumber(customerPhoneNumber);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return customer;
	}
	
	public Boolean addCustomer(String externalCode, String label, String email, String phoneNumber) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO customers(customer_external_code, customer_label, customer_email, customer_phone_number) values(?, ?, ?, ?)");
		preparedStatement.setString(1, externalCode);
		preparedStatement.setString(2, label);
		preparedStatement.setString(3, email);
		preparedStatement.setString(4, phoneNumber);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean updateCustomer(Integer id, String externalCode, String label, String email, String phoneNumber) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE customers set customer_external_code=?, customer_label=?, customer_email=?, customer_phone_number=? where customer_id=?");
		preparedStatement.setString(1, externalCode);
		preparedStatement.setString(2, label);
		preparedStatement.setString(3, email);
		preparedStatement.setString(4, phoneNumber);
		preparedStatement.setInt(5, id);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean deleteCustomer(Integer id) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("DELETE FROM customers where customer_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public List<Customer> getCustomersByCustomerLabel(String label) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Customer> customers = new ArrayList<Customer>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("select * from customers where customer_label like ?");
		preparedStatement.setString(1, "%"+label+"%");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer customerId = resultSet.getInt("customer_id");
			String customerExternalCode = resultSet.getString("customer_external_code");
			String customerLabel = resultSet.getString("customer_label");
			String customerEmail = resultSet.getString("customer_email");
			String customerPhoneNumber = resultSet.getString("customer_phone_number");
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerExternalCode(customerExternalCode);
			customer.setCustomerLabel(customerLabel);
			customer.setCustomerEmail(customerEmail);
			customer.setCustomerPhoneNumber(customerPhoneNumber);
			customers.add(customer);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return customers;
	}
}
