package com.toni.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.toni.beans.Typology;
import com.toni.beans.Role;
import com.toni.dao.TypologiesDAO;
import com.toni.dao.RolesDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class RolesService {
private static RolesService INSTANCE;
	
	private RolesService() {
		
	}
	
	public static RolesService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new RolesService();
		}
		return INSTANCE;
	}
	
	public List<Role> getRoles() throws ClassNotFoundException, SQLException {
		RolesDAO rolesDAO = RolesDAO.getInstance();
		return rolesDAO.getRoles();
	}
	
	public Role findRole(Integer roleId) throws ClassNotFoundException, SQLException {
		RolesDAO rolesDAO = RolesDAO.getInstance();
		return rolesDAO.findRoleByRoleId(roleId);
	}
	
	public List<Role> findRole(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		RolesDAO rolesDAO = RolesDAO.getInstance();
		if(StringUtils.isNotEmpty(keyWord)) {
			Role role =  rolesDAO.findRoleByLabel(keyWord);
			if(role != null) {
				return Arrays.asList(role);
			} else {
				request.setAttribute("message", "Aucun role avec le libellé " + keyWord + " n'a été trouvé !");
			}
		} else {
			return this.getRoles();
		}
		return Collections.EMPTY_LIST;
	}
	
	public Boolean addRole(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String roleLabel = request.getParameter("roleLabel");
		String profileId = request.getParameter("profile");
		String typologyId = request.getParameter("typology");
		if(StringUtils.isNotEmpty(roleLabel)
				&& StringUtils.isNotEmpty(typologyId) && !"0".equals(typologyId)
				&& StringUtils.isNotEmpty(profileId) && !"0".equals(profileId)) {
			TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
			Typology typology = typologiesDAO.findTypologyByTypologieId(Integer.valueOf(typologyId));
					RolesDAO rolesDAO = RolesDAO.getInstance();
					Role role = rolesDAO.findRoleByLabel(roleLabel);
					if(role == null) {
						 result = rolesDAO.addRole(roleLabel, Integer.valueOf(profileId));
						 if(!result) {
							 request.setAttribute("message", "Echec d'ajout !");
						 }
					} else {
						request.setAttribute("message", "le libellé " + roleLabel + " existe déjà !");
					}
				} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public Boolean updateRole(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String id = request.getParameter("roleId");
		String roleLabel = request.getParameter("roleLabel");
		String typologyId = request.getParameter("typology");
		String profileId = request.getParameter("profile");
		if(StringUtils.isNotEmpty(roleLabel)
				&& StringUtils.isNotEmpty(typologyId) && !"0".equals(typologyId)
				&& StringUtils.isNotEmpty(profileId) && !"0".equals(profileId)) {
			TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
			Typology typology = typologiesDAO.findTypologyByTypologieId(Integer.valueOf(typologyId));
		
				RolesDAO rolesDAO = RolesDAO.getInstance();
				result = rolesDAO.updateRole(Integer.valueOf(id), roleLabel , Integer.valueOf(profileId));
				 if(!result) {
					 request.setAttribute("message", "Echec de modification !");
				 }
			
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public void deleteRole(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String id = request.getParameter("roleId");
		if(StringUtils.isNotEmpty(id)) {
			RolesDAO rolesDAO = RolesDAO.getInstance();
				result = rolesDAO.deleteRole(Integer.valueOf(id));
				if(!result) {
					request.setAttribute("message", "Echec de suppression !");
				}
			
		}
	}
	
}
