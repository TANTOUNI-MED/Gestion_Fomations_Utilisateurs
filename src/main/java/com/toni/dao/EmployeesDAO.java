package com.toni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Customer;
import com.toni.beans.Employee;

public class EmployeesDAO {
	private static EmployeesDAO INSTANCE;
	
	private EmployeesDAO() {
		
	}
	
	public static EmployeesDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new EmployeesDAO();
		}
		return INSTANCE;
	}
	
	public Employee findEmployeeByEmail(String email) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Employee employee = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("select e.* from employees e where e.employee_email=?");
		preparedStatement.setString(1, email);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer employeeId = resultSet.getInt("employee_id");
			String employeeLastName = resultSet.getString("employee_last_name");
			String employeeFirstName = resultSet.getString("employee_last_name");
			String employeeEmail = resultSet.getString("employee_email");
			String employeeSupplierRefNumber = resultSet.getString("employee_supplier_reg_number");
			String employeeCustomerRefNumber = resultSet.getString("employee_customer_reg_number");
			Integer customerId = resultSet.getInt("customer_id");
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			
			employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setEmployeeLastName(employeeLastName);
			employee.setEmployeeFirstName(employeeFirstName);
			employee.setEmployeeEmail(employeeEmail);
			employee.setEmployeeSupplierRegNumber(employeeSupplierRefNumber);
			employee.setEmployeeCstumerRegNumber(employeeCustomerRefNumber);
			employee.setCustomer(customer);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return employee;
	}
	
	public List<Employee> getEmployeesByRequestId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Employee> employees = new ArrayList<Employee>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("select e.* from requests r, employees_requests er, employees e where r.request_id=? and r.request_id=er.request_id and er.employee_id=e.employee_id");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer employeeId = resultSet.getInt("employee_id");
			String employeeLastName = resultSet.getString("employee_last_name");
			String employeeFirstName = resultSet.getString("employee_last_name");
			String employeeEmail = resultSet.getString("employee_email");
			String employeeSupplierRefNumber = resultSet.getString("employee_supplier_reg_number");
			String employeeCustomerRefNumber = resultSet.getString("employee_customer_reg_number");
			Integer customerId = resultSet.getInt("customer_id");
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setEmployeeLastName(employeeLastName);
			employee.setEmployeeFirstName(employeeFirstName);
			employee.setEmployeeEmail(employeeEmail);
			employee.setEmployeeSupplierRegNumber(employeeSupplierRefNumber);
			employee.setEmployeeCstumerRegNumber(employeeCustomerRefNumber);
			employee.setCustomer(customer);
			
			employees.add(employee);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return employees;
	}
	
	public List<Employee> getEmployeesByCustomerId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Employee> employees = new ArrayList<Employee>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT e.* from employees e where e.customer_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer employeeId = resultSet.getInt("employee_id");
			String employeeLastName = resultSet.getString("employee_last_name");
			String employeeFirstName = resultSet.getString("employee_last_name");
			String employeeEmail = resultSet.getString("employee_email");
			String employeeSupplierRefNumber = resultSet.getString("employee_supplier_reg_number");
			String employeeCustomerRefNumber = resultSet.getString("employee_customer_reg_number");
			Integer customerId = resultSet.getInt("customer_id");
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setEmployeeLastName(employeeLastName);
			employee.setEmployeeFirstName(employeeFirstName);
			employee.setEmployeeEmail(employeeEmail);
			employee.setEmployeeSupplierRegNumber(employeeSupplierRefNumber);
			employee.setEmployeeCstumerRegNumber(employeeCustomerRefNumber);
			employee.setCustomer(customer);
			
			employees.add(employee);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return employees;
	}
	
	public List<Employee> getEmployeesByCustomerIdAndFormationId(Integer cId, Integer fId) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Employee> employees = new ArrayList<Employee>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT e.* from employees e where e.customer_id=? and e.employee_id not in (select er.employee_id from requests r, employees_requests er where r.formation_id=? and r.request_id=er.request_id)");
		preparedStatement.setInt(1, cId);
		preparedStatement.setInt(2, fId);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer employeeId = resultSet.getInt("employee_id");
			String employeeLastName = resultSet.getString("employee_last_name");
			String employeeFirstName = resultSet.getString("employee_last_name");
			String employeeEmail = resultSet.getString("employee_email");
			String employeeSupplierRefNumber = resultSet.getString("employee_supplier_reg_number");
			String employeeCustomerRefNumber = resultSet.getString("employee_customer_reg_number");
			Integer customerId = resultSet.getInt("customer_id");
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setEmployeeLastName(employeeLastName);
			employee.setEmployeeFirstName(employeeFirstName);
			employee.setEmployeeEmail(employeeEmail);
			employee.setEmployeeSupplierRegNumber(employeeSupplierRefNumber);
			employee.setEmployeeCstumerRegNumber(employeeCustomerRefNumber);
			employee.setCustomer(customer);
			
			employees.add(employee);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return employees;
	}
	
	public List<Employee> getEmployeesByCustomerIdAndFormationIdAndNotRequestId(Integer cId, Integer fId, Integer rId) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Employee> employees = new ArrayList<Employee>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT e.* from employees e where e.customer_id=? and e.employee_id not in (select er.employee_id from requests r, employees_requests er where r.formation_id=? and r.request_id<>? and r.request_id=er.request_id)");
		preparedStatement.setInt(1, cId);
		preparedStatement.setInt(2, fId);
		preparedStatement.setInt(3, rId);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer employeeId = resultSet.getInt("employee_id");
			String employeeLastName = resultSet.getString("employee_last_name");
			String employeeFirstName = resultSet.getString("employee_last_name");
			String employeeEmail = resultSet.getString("employee_email");
			String employeeSupplierRefNumber = resultSet.getString("employee_supplier_reg_number");
			String employeeCustomerRefNumber = resultSet.getString("employee_customer_reg_number");
			Integer customerId = resultSet.getInt("customer_id");
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setEmployeeLastName(employeeLastName);
			employee.setEmployeeFirstName(employeeFirstName);
			employee.setEmployeeEmail(employeeEmail);
			employee.setEmployeeSupplierRegNumber(employeeSupplierRefNumber);
			employee.setEmployeeCstumerRegNumber(employeeCustomerRefNumber);
			employee.setCustomer(customer);
			
			employees.add(employee);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return employees;
	}
}
