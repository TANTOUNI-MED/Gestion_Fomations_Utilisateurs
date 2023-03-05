package com.toni.services;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.toni.beans.Employee;
import com.toni.beans.Formation;
import com.toni.beans.Request;
import com.toni.beans.User;
import com.toni.dao.EmployeesDAO;
import com.toni.dao.FormationsDAO;
import com.toni.dao.RequestsDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RequestsService {
	private static RequestsService INSTANCE;
	
	private RequestsService() {
		
	}
	
	public static RequestsService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new RequestsService();
		}
		return INSTANCE;
	}
	
	public List<Request> getRequestsByNumber(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		if(StringUtils.isNotEmpty(keyWord)) {
			RequestsDAO requestsDAO = RequestsDAO.getInstance();
			List<Request> requests =  requestsDAO.getRequestsByNumber(keyWord);
			if(requests.size() == 0) {
				return requests;
			} else {
				request.setAttribute("message", "Aucune demande avec le numéro " + keyWord + " n'a été trouvée !");
			}
		}else {
			return this.getRequests(request);
		}
		return Collections.EMPTY_LIST;
	}
	
	public List<Request> getRequests(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		RequestsDAO requestsDAO = RequestsDAO.getInstance();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String typology = user.getProfile().getTypology().getTypologyCode();
		if("C".equals(typology)) {
			Integer customerId = user.getCustomer().getCustomerId();
			if(customerId != null) {
				return requestsDAO.getRequestsByCustomerId(customerId);
			}
			return Collections.EMPTY_LIST;
		}
		return requestsDAO.getRequests();
	}
	
	public Request findRequest(Integer id) throws ClassNotFoundException, SQLException {
		RequestsDAO requestsDAO = RequestsDAO.getInstance();
		return requestsDAO.findRequestByRequestId(id);
	}
	
	public String generateRequestNumber() throws ClassNotFoundException, SQLException {
		RequestsDAO requestsDAO = RequestsDAO.getInstance();
		Integer maxRequestsId = requestsDAO.getMaxRequestsId();
		maxRequestsId++;
		return "DMD_"+maxRequestsId;
	}
	
	public void deleteRequest(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String requestId = request.getParameter("requestId");
		if(StringUtils.isNotEmpty(requestId)) {
			RequestsDAO requestsDAO = RequestsDAO.getInstance();
			result = requestsDAO.deleteRequest(Integer.valueOf(requestId));
			if(!result) {
				request.setAttribute("message", "Echec d'ajout !");
			}
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
	}
	
	public Boolean acceptRequest(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String requestId = request.getParameter("requestId");
		if(StringUtils.isNotEmpty(requestId)) {
			FormationsDAO formationsDAO = FormationsDAO.getInstance();
			EmployeesDAO employeesDAO = EmployeesDAO.getInstance();
			RequestsDAO requestsDAO = RequestsDAO.getInstance();
			Formation formation = formationsDAO.findFormationByRequestId(Integer.valueOf(requestId));
			List<Employee> employees = employeesDAO.getEmployeesByRequestId(Integer.valueOf(requestId));
			List<Integer> employeesId = new ArrayList<Integer>();
			for(Employee employee: employees) {
				employeesId.add(employee.getEmployeeId());
			}
			result = requestsDAO.acceptRequest(Integer.valueOf(requestId), formation.getFormationId(), employeesId);
			if(!result) {
				request.setAttribute("message", "Echec d'ajout !");
			}
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public Boolean updateRequest(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String requestId = request.getParameter("requestId");
		LocalDate currentDate = LocalDate.now();
		Date requestDate = Date.valueOf(currentDate);
		String requestStatus = "sent";
		String formationId = request.getParameter("formation");
		String[] selectedEmployees = request.getParameterValues("selectedEmployees");
		if(StringUtils.isNotEmpty(requestId) && StringUtils.isNotEmpty(formationId) && selectedEmployees != null && selectedEmployees.length > 0) {
			List<Integer> employeesId = new ArrayList<Integer>();
			for(String selectedEmployee : Arrays.asList(selectedEmployees)) {
				employeesId.add(Integer.valueOf(selectedEmployee));
			}
			RequestsDAO requestsDAO = RequestsDAO.getInstance();
			result = requestsDAO.updateRequest(Integer.valueOf(requestId), requestDate, requestStatus, Integer.valueOf(formationId), employeesId);
			if(!result) {
				request.setAttribute("message", "Echec d'envoi de la demande !");
			}
		} else {
			request.setAttribute("message", "Vous devez au moins choisir un employé !");
		}
		return result;
	}
	
	public Boolean addRequest(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String requestNumber = request.getParameter("number");
		LocalDate currentDate = LocalDate.now();
		Date requestDate = Date.valueOf(currentDate);
		String requestStatus = "sent";
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		Integer customerId = user.getCustomer().getCustomerId();
		String formationId = request.getParameter("formation");
		String[] selectedEmployees = request.getParameterValues("selectedEmployees");
		if(StringUtils.isNotEmpty(formationId) && selectedEmployees != null && selectedEmployees.length > 0) {
			List<Integer> employeesId = new ArrayList<Integer>();
			for(String selectedEmployee : Arrays.asList(selectedEmployees)) {
				employeesId.add(Integer.valueOf(selectedEmployee));
			}
			RequestsDAO requestsDAO = RequestsDAO.getInstance();
			result = requestsDAO.addRequest(requestNumber, requestDate, requestStatus, customerId, Integer.valueOf(formationId), employeesId);
			if(!result) {
				request.setAttribute("message", "Echec d'envoi de la demande !");
			}
		} else {
			request.setAttribute("message", "Vous devez au moins choisir un employé !");
		}
		return result;
	}
}
