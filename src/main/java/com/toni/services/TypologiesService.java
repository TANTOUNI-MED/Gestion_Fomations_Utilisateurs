package com.toni.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Typology;
import com.toni.dao.ProfilesDAO;
import com.toni.dao.TypologiesDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class TypologiesService {
	private static TypologiesService INSTANCE;
	
	private TypologiesService() {
		
	}
	
	public static TypologiesService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TypologiesService();
		}
		return INSTANCE;
	}
	
	public List<Typology> getTypologies() throws ClassNotFoundException, SQLException {
		TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
		List<Typology> typologies = typologiesDAO.getTypologies();
		if(typologies.size() > 0) {
			ProfilesDAO profilesDAO = ProfilesDAO.getInstance();
			for(Typology typology : typologies) {
				typology.setProfiles(profilesDAO.getProfilesByTypologyId(typology.getTypologyId()));
			}
		}
		return typologies;
	}
	
	public void deleteTypology(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		String id = request.getParameter("typologyId");
		if(StringUtils.isNotEmpty(id)) {
			TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
			Boolean result = typologiesDAO.deleteTypology(Integer.valueOf(id));
			if(!result) {
				request.setAttribute("message", "Echec de suppression !");
			}	
		}
	}
	
	public Boolean addTypology(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String code = request.getParameter("typologyCode");
		String label = request.getParameter("typologyLabel");
		if(StringUtils.isNotEmpty(code)
				&& StringUtils.isNotEmpty(label)) {
			TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
			Typology typology = typologiesDAO.findTypologyByCodeOrLabel(code, label);
			if(typology == null) {
				 result = typologiesDAO.addTypology(code, label);
				 if(!result) {
					 request.setAttribute("message", "Echec d'ajout !");
				 }
			} else {
				request.setAttribute("message", "La typologie " + code + " - " + label + " existe déjà !");
			}
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public Boolean updateTypology(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String id = request.getParameter("id");
		String code = request.getParameter("typologyCode");
		String label = request.getParameter("typologyLabel");
		if(StringUtils.isNotEmpty(code)
				&& StringUtils.isNotEmpty(label)) {
			TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
			result = typologiesDAO.updateTypology(Integer.valueOf(id), code, label);
			 if(!result) {
				 request.setAttribute("message", "Echec de modification !");
			 }
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public Typology getTypologyByTypologyId(Integer typologyId) throws ClassNotFoundException, SQLException {
		TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
		return typologiesDAO.findTypologyByTypologieId(typologyId);
	}
	
	public List<Typology> getTypologiesByTypologyLabel(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
		List<Typology> typologies = new ArrayList<Typology>();
		if(StringUtils.isNotEmpty(keyWord)) {
			typologies =  typologiesDAO.getTypologiesByTypologyLabel(keyWord);
			if(typologies.size() == 0) {
				request.setAttribute("message", "Aucune typologie avec le libellé " + keyWord + " n'a été trouvée !");
			}
		} else {
			typologies = this.getTypologies();
		}
		return typologies;
	}
}
