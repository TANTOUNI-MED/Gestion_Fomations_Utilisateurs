package com.toni.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.toni.beans.Profile;
import com.toni.dao.ProfilesDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class ProfilesService {
	private static ProfilesService INSTANCE;
	
	private ProfilesService() {
		
	}
	
	public static ProfilesService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ProfilesService();
		}
		return INSTANCE;
	}
	public List<Profile> getProfiles() throws ClassNotFoundException, SQLException {
		ProfilesDAO profileDAO = ProfilesDAO.getInstance();
		return profileDAO.getProfiles();
	}
	public List<Profile> getProfilesByTypologyId(Integer typologyId) throws ClassNotFoundException, SQLException {
		ProfilesDAO profilesDAO = ProfilesDAO.getInstance();
		return profilesDAO.getProfilesByTypologyId(typologyId);
	}
	public Profile findProfile(Integer ProfileId) throws ClassNotFoundException, SQLException {
		ProfilesDAO profilesDAO = ProfilesDAO.getInstance();;
		return profilesDAO.findProfileByProfileId(ProfileId);
	}
	public Boolean addProfile(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String profileCode = request.getParameter("profileCode");
		String profileLabel = request.getParameter("profileLabel");
		String typologyId = request.getParameter("typology");
		if(StringUtils.isNotEmpty(profileCode)
				&& StringUtils.isNotEmpty(profileLabel)
				&& StringUtils.isNotEmpty(typologyId) && !"0".equals(typologyId)) {
			ProfilesDAO profilesDAO = ProfilesDAO.getInstance();
			result = profilesDAO.addProfile(profileCode, profileLabel, Integer.valueOf(typologyId));
				 if(!result) {
								 request.setAttribute("message", "Echec d'ajout !");
				 }
						
			} else {
						request.setAttribute("message", "Tous les champs sont obligatoires !");
					}
				
		return result;
	}
	
	public Boolean updateProfile(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String profileId = request.getParameter("profileId");
		String profileCode = request.getParameter("profileCode");
		String profileLabel = request.getParameter("profileLabel");
		String typologyId = request.getParameter("typology");
		if(StringUtils.isNotEmpty(profileCode)
				&& StringUtils.isNotEmpty(profileLabel)
				&& StringUtils.isNotEmpty(typologyId) && !"0".equals(typologyId)) {
			ProfilesDAO profilesDAO = ProfilesDAO.getInstance();
			result = profilesDAO.updateProfile(Integer.valueOf(profileId),profileCode, profileLabel, Integer.valueOf(typologyId));
					if(!result) {
					 request.setAttribute("message", "Echec de modification !");
				 }
			
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	public void deleteProfile(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		Boolean result =Boolean.FALSE;
		String profileId = request.getParameter("profileId");
		if(StringUtils.isNotEmpty(profileId)) {
			ProfilesDAO profilesDAO = ProfilesDAO.getInstance();
			result = profilesDAO.deleteProfile(Integer.valueOf(profileId));
			if(!result) request.setAttribute("message","Echec de suppression !");
		}
		
	}
	public List<Profile> findProfile(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		ProfilesDAO profilesDAO = ProfilesDAO.getInstance();
		if(StringUtils.isNotEmpty(keyWord)) {
			Profile profile =  profilesDAO.findProfileByCode(keyWord);
			if(profile != null) {
				return Arrays.asList(profile);
			} else {
				request.setAttribute("message", "Aucun Profile avec le code " + keyWord + " n'a été trouvé !");
			}
		} else {
			return this.getProfiles();
		}
		return Collections.EMPTY_LIST;
	}
	
}
