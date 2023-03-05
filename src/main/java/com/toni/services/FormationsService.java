package com.toni.services;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.toni.beans.Employee;
import com.toni.beans.Formation;
import com.toni.beans.User;
import com.toni.dao.EmployeesDAO;
import com.toni.dao.FormationsDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class FormationsService {
	private static FormationsService INSTANCE;
	
	private FormationsService() {
		
	}
	
	public static FormationsService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new FormationsService();
		}
		return INSTANCE;
	}
	
	public List<Formation> getFormations(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		FormationsDAO formationsDAO = FormationsDAO.getInstance();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String typologyCode = user.getProfile().getTypology().getTypologyCode();
		if("E".equals(typologyCode)) {
			String email = user.getUserLogin();
			return formationsDAO.getFormationsByEmployeeEmail(email);
		} else {
			String requestId = request.getParameter("requestId");
			if(StringUtils.isNotEmpty(requestId)) {
				return Arrays.asList(formationsDAO.findFormationByRequestId(Integer.valueOf(requestId)));
			} else {
				return formationsDAO.getFormations();
			}
		}
	}
	
	public Formation findFormation(Integer formationId) throws NumberFormatException, ClassNotFoundException, SQLException {
		FormationsDAO formationsDAO = FormationsDAO.getInstance();
		return formationsDAO.findFormationByFormationId(formationId);
	}
	
	public Boolean addFormation(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String code = request.getParameter("code");
		String label = request.getParameter("label");
		String description = request.getParameter("description");
		String duration = request.getParameter("duration");
		String url = request.getParameter("url");
		String formationTypeId = request.getParameter("formationType");
		if(StringUtils.isNotEmpty(code)
				&& StringUtils.isNotEmpty(label)
				&& StringUtils.isNotEmpty(description)
				&& StringUtils.isNotEmpty(duration)
				&& StringUtils.isNotEmpty(url)
				&& StringUtils.isNotEmpty(formationTypeId) && !"0".equals(formationTypeId)) {
			FormationsDAO formationsDAO = FormationsDAO.getInstance();
			Formation formation = formationsDAO.findFormationByFormationCodeOrFormationLabel(code, label);
			if(formation == null) {
				 result = formationsDAO.addFormation(code, label, description, Integer.valueOf(duration), url, Integer.valueOf(formationTypeId));
				 if(!result) {
					 request.setAttribute("message", "Echec d'ajout !");
				 }
			} else {
				request.setAttribute("message", "La formation " + code + " - " + label + " existe déjà !");
			}
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public Boolean updateFormation(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String label = request.getParameter("label");
		String description = request.getParameter("description");
		String duration = request.getParameter("duration");
		String url = request.getParameter("url");
		String formationTypeId = request.getParameter("formationType");
		if(StringUtils.isNotEmpty(code)
				&& StringUtils.isNotEmpty(label)
				&& StringUtils.isNotEmpty(description)
				&& StringUtils.isNotEmpty(duration)
				&& StringUtils.isNotEmpty(url)
				&& StringUtils.isNotEmpty(formationTypeId) && !"0".equals(formationTypeId)) {
			FormationsDAO formationsDAO = FormationsDAO.getInstance();
			result = formationsDAO.updateFormation(Integer.valueOf(id), code, label, description, Integer.valueOf(duration), url, Integer.valueOf(formationTypeId));
			 if(!result) {
				 request.setAttribute("message", "Echec de modification !");
			 }
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public void deleteFormation(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		String id = request.getParameter("formationId");
		if(StringUtils.isNotEmpty(id)) {
			FormationsDAO formationsDAO = FormationsDAO.getInstance();
			Boolean result = formationsDAO.deleteFomration(Integer.valueOf(id));
			if(!result) {
				request.setAttribute("message", "Echec de suppression !");
			}	
		}
	}
	
	public void startFormation(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		String fomrationId = request.getParameter("formationId");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String email = user.getUserLogin();
		if(StringUtils.isNotEmpty(fomrationId) && StringUtils.isNotEmpty(email)) {
			LocalDate currentDate = LocalDate.now();
			Date startDate = Date.valueOf(currentDate);
			EmployeesDAO employeesDAO = EmployeesDAO.getInstance();
			Employee employee = employeesDAO.findEmployeeByEmail(email);
			FormationsDAO formationsDAO = FormationsDAO.getInstance();
			Boolean result = formationsDAO.startFomration(Integer.valueOf(fomrationId), employee.getEmployeeId(), startDate);
			if(!result) {
				request.setAttribute("message", "Echec de démarrage de la formation !");
			}	
		}
	}
	
	public void endFormation(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		String fomrationId = request.getParameter("formationId");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String email = user.getUserLogin();
		if(StringUtils.isNotEmpty(fomrationId) && StringUtils.isNotEmpty(email)) {
			LocalDate currentDate = LocalDate.now();
			Date startDate = Date.valueOf(currentDate);
			EmployeesDAO employeesDAO = EmployeesDAO.getInstance();
			Employee employee = employeesDAO.findEmployeeByEmail(email);
			FormationsDAO formationsDAO = FormationsDAO.getInstance();
			Boolean result = formationsDAO.endFomration(Integer.valueOf(fomrationId), employee.getEmployeeId(), startDate);
			if(!result) {
				request.setAttribute("message", "Echec de démarrage de la formation !");
			}	
		}
	}
}
