package com.toni.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.toni.beans.Employee;
import com.toni.beans.User;
import com.toni.dao.EmployeesDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class EmployeesService {
	private static EmployeesService INSTANCE;
	
	private EmployeesService() {
		
	}
	
	public static EmployeesService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new EmployeesService();
		}
		return INSTANCE;
	}
	
	public List<Employee> getEmployeesToCreateRequest(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		Integer customerId = user.getCustomer().getCustomerId();
		String formation = request.getParameter("formation");
		if(customerId != null && StringUtils.isNotEmpty(formation) && !"0".equals(formation)) {
			EmployeesDAO employeesDAO = EmployeesDAO.getInstance();
			return employeesDAO.getEmployeesByCustomerIdAndFormationId(customerId, Integer.valueOf(formation));
		}
		return Collections.EMPTY_LIST;
	}
	
	public List<Employee> getEmployeesToEditRequest(HttpServletRequest request, String formationId) throws ClassNotFoundException, SQLException {
		String requestId = request.getParameter("requestId");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		Integer customerId = user.getCustomer().getCustomerId();
		if(StringUtils.isNotEmpty(requestId)) {
			EmployeesDAO employeesDAO = EmployeesDAO.getInstance();
			List<Employee> requestEmployees = employeesDAO.getEmployeesByRequestId(Integer.valueOf(requestId));
			List<Employee> possibleEmployees = employeesDAO.getEmployeesByCustomerIdAndFormationIdAndNotRequestId(customerId, Integer.valueOf(formationId), Integer.valueOf(requestId));
			for (Employee pEmployee : possibleEmployees) {
				for(Employee rEmployee : requestEmployees) {
					if(pEmployee.getEmployeeEmail().equals(rEmployee.getEmployeeEmail())) {
						pEmployee.setSelected(true);
						break;
					}
				}
			}
			return possibleEmployees;
			
		}
		return Collections.EMPTY_LIST;
	}
	
	public List<Employee> getEmployees(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String requestId = request.getParameter("requestId");
		EmployeesDAO employeesDAO = EmployeesDAO.getInstance();
		if(StringUtils.isNotEmpty(requestId)) {
			return employeesDAO.getEmployeesByRequestId(Integer.valueOf(requestId));
		} else {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			Integer customerId = user.getCustomer().getCustomerId();
			if(customerId != null) {
				return employeesDAO.getEmployeesByCustomerId(customerId);
			}
		}
		return Collections.EMPTY_LIST;
	}
	
	public List<Employee> getEmployeeByEmail(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		if(StringUtils.isNotEmpty(keyWord)) {
			EmployeesDAO employeesDAO = EmployeesDAO.getInstance();
			Employee employee =  employeesDAO.findEmployeeByEmail(keyWord);
			if(employee != null) {
				return Arrays.asList(employee);
			} else {
				request.setAttribute("message", "Aucun employé avec l'adresse mail " + keyWord + " n'a été trouvé !");
			}
		}
		return Collections.EMPTY_LIST;
	}
}
