package com.toni.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.toni.beans.FormationType;
import com.toni.dao.FormationTypesDAO;
import com.toni.dao.FormationsDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class FormationTypesService {
	private static FormationTypesService INSTANCE;
	
	private FormationTypesService() {
		
	}
	
	public static FormationTypesService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new FormationTypesService();
		}
		return INSTANCE;
	}
	
	public List<FormationType> getFormationTypes() throws ClassNotFoundException, SQLException {
		FormationTypesDAO formationTypesDAO = FormationTypesDAO.getInstance();
		List<FormationType> formationTypes = formationTypesDAO.getFormationTypes();
		if(formationTypes.size() > 0) {
			FormationsDAO formationsDAO = FormationsDAO.getInstance();
			for(FormationType formationType : formationTypes) {
				formationType.setFormations(formationsDAO.getFormationsByFormationTypeId(formationType.getFormationTypeId()));
			}
		}
		return formationTypes;
	}
	
	public List<FormationType> getFormationTypess() throws ClassNotFoundException, SQLException {
		FormationTypesDAO formationTypesDAO = FormationTypesDAO.getInstance();
		return formationTypesDAO.getFormationTypes();
	}
	
	public FormationType findFormationType(Integer FormationTypeId) throws ClassNotFoundException, SQLException {
		FormationTypesDAO formationTypesDAO = FormationTypesDAO.getInstance();
		return formationTypesDAO.findFormationTypeById(FormationTypeId);
	}
	public Boolean addFormationType(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String formationTypeCode = request.getParameter("formationTypeCode");
		String formationTypeLabel = request.getParameter("formationTypeLabel");
		if(StringUtils.isNotEmpty(formationTypeCode)
				&& StringUtils.isNotEmpty(formationTypeLabel)) {
			FormationTypesDAO formationTypesDAO = FormationTypesDAO.getInstance();
			result = formationTypesDAO.addFormationType(formationTypeCode, formationTypeLabel);
				 if(!result) {
								 request.setAttribute("message", "Echec d'ajout !");
				 }
						
			} else {
						request.setAttribute("message", "Tous les champs sont obligatoires !");
					}
				
		return result;
	}
	
	public Boolean updateFormationType(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String formationTypeId = request.getParameter("formationTypeId");
		String formationTypeCode = request.getParameter("formationTypeCode");
		String formationTypeLabel = request.getParameter("formationTypeLabel");
		if(StringUtils.isNotEmpty(formationTypeCode)
				&& StringUtils.isNotEmpty(formationTypeLabel)) {
			FormationTypesDAO formationTypesDAO = FormationTypesDAO.getInstance();
			result = formationTypesDAO.updateFormationType(Integer.valueOf(formationTypeId),formationTypeCode, formationTypeLabel);
					if(!result) {
					 request.setAttribute("message", "Echec de modification !");
				 }
			
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	public void deleteFormationType(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		Boolean result =Boolean.FALSE;
		String formationTypeId = request.getParameter("formationTypeId");
		if(StringUtils.isNotEmpty(formationTypeId)) {
			FormationTypesDAO formationTypesDAO = FormationTypesDAO.getInstance();
			result = formationTypesDAO.deleteFormationType(Integer.valueOf(formationTypeId));
			if(!result) request.setAttribute("message","Echec de suppression !");
		}
		
	}
	public List<FormationType> findFormationType(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		FormationTypesDAO formationTypesDAO = FormationTypesDAO.getInstance();
		if(StringUtils.isNotEmpty(keyWord)) {
			FormationType formationType =  formationTypesDAO.findFormationTypeByCode(keyWord);
			if(formationType != null) {
				return Arrays.asList(formationType);
			} else {
				request.setAttribute("message", "Aucun Type de formation avec le code " + keyWord + " n'a été trouvé !");
				return Collections.EMPTY_LIST;
			}
		} else {
			return this.getFormationTypes();
		}
	}
}
