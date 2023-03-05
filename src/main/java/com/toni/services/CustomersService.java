package com.toni.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Customer;
import com.toni.dao.CustomersDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class CustomersService {
	private static CustomersService INSTANCE;
	
	private CustomersService() {
		
	}
	
	public static CustomersService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new CustomersService();
		}
		return INSTANCE;
	}
	
	public List<Customer> getCustomers() throws ClassNotFoundException, SQLException {
		CustomersDAO customersDAO = CustomersDAO.getInstance();
		return customersDAO.getCustomers();
	}
	
	public Customer findCustomer(Integer customerId) throws ClassNotFoundException, SQLException {
		CustomersDAO customersDAO = CustomersDAO.getInstance();
		return customersDAO.findCustomerByCustomerId(customerId);
	}
	
	public Boolean addCustomer(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String externalCode = request.getParameter("externalCode");
		String label = request.getParameter("label");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		if(StringUtils.isNotEmpty(externalCode)
				&& StringUtils.isNotEmpty(label)
				&& StringUtils.isNotEmpty(email)
				&& StringUtils.isNotEmpty(phoneNumber)) {
			CustomersDAO customersDAO = CustomersDAO.getInstance();
			Customer customer = customersDAO.findCustomerByEmailOrExternalCodeOrLabel(email, externalCode, label);
			if(customer == null) {
				 result = customersDAO.addCustomer(externalCode, label, email, phoneNumber);
				 if(!result) {
					 request.setAttribute("message", "Echec d'ajout !");
				 }
			} else {
				request.setAttribute("message", "L'adresse mail " + email + ", le code externe " + externalCode + " ou le nom " + label + " existe déjà !");
			}
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public Boolean updateCustomer(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String id = request.getParameter("id");
		String externalCode = request.getParameter("externalCode");
		String label = request.getParameter("label");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		if(StringUtils.isNotEmpty(externalCode)
				&& StringUtils.isNotEmpty(label)
				&& StringUtils.isNotEmpty(email)
				&& StringUtils.isNotEmpty(phoneNumber)) {
			CustomersDAO customersDAO = CustomersDAO.getInstance();
			result = customersDAO.updateCustomer(Integer.valueOf(id), externalCode, label, email, phoneNumber);
			 if(!result) {
				 request.setAttribute("message", "Echec de modification !");
			 }
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public void deleteCustomer(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		String id = request.getParameter("customerId");
		if(StringUtils.isNotEmpty(id)) {
			CustomersDAO customersDAO = CustomersDAO.getInstance();
			Boolean result = customersDAO.deleteCustomer(Integer.valueOf(id));
			if(!result) {
				request.setAttribute("message", "Echec de suppression !");
			}	
		}
	}
	
	public List<Customer> getCustomersByCustomerLabel(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		List<Customer> customers = new ArrayList<Customer>();
		if(StringUtils.isNotEmpty(keyWord)) {
			CustomersDAO customersDAO = CustomersDAO.getInstance();
			customers =  customersDAO.getCustomersByCustomerLabel(keyWord);
			if(customers.size() == 0) {
				request.setAttribute("message", "Aucun client avec le libellé " + keyWord + " n'a été trouvé !");
			}
		} else {
			customers = this.getCustomers();
		}
		return customers;
	}
}
