package com.toni.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.toni.beans.Typology;
import com.toni.beans.User;
import com.toni.dao.TypologiesDAO;
import com.toni.dao.UsersDAO;
import com.toni.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class UsersService {
	private static UsersService INSTANCE;
	
	private UsersService() {
		
	}
	
	public static UsersService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UsersService();
		}
		return INSTANCE;
	}
	
	public List<User> getUsers() throws ClassNotFoundException, SQLException {
		UsersDAO usersDAO = UsersDAO.getInstance();
		return usersDAO.getUsers();
	}
	
	public User findUser(Integer userId) throws ClassNotFoundException, SQLException {
		UsersDAO usersDAO = UsersDAO.getInstance();
		return usersDAO.findUserByUserId(userId);
	}
	
	public List<User> findUser(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		String keyWord = request.getParameter("keyWord");
		UsersDAO usersDAO = UsersDAO.getInstance();
		if(StringUtils.isNotEmpty(keyWord)) {
			User user =  usersDAO.findUserByLogin(keyWord);
			if(user != null) {
				return Arrays.asList(user);
			} else {
				request.setAttribute("message", "Aucun utilisateur avec l'adresse mail " + keyWord + " n'a été trouvé !");
			}
		} else {
			return this.getUsers();
		}
		return Collections.EMPTY_LIST;
	}
	
	public Boolean addUser(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String typologyId = request.getParameter("typology");
		String profileId = request.getParameter("profile");
		String customerId = request.getParameter("customer");
		if(StringUtils.isNotEmpty(lastName)
				&& StringUtils.isNotEmpty(firstName)
				&& StringUtils.isNotEmpty(login)
				&& StringUtils.isNotEmpty(password)
				&& StringUtils.isNotEmpty(typologyId) && !"0".equals(typologyId)
				&& StringUtils.isNotEmpty(profileId) && !"0".equals(profileId)) {
			TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
			Typology typology = typologiesDAO.findTypologyByTypologieId(Integer.valueOf(typologyId));
				if("E".equals(typology.getTypologyCode())
						|| "C".equals(typology.getTypologyCode())) {
					if(StringUtils.isNotEmpty(customerId) && !"0".equals(customerId)) {
						UsersDAO usersDAO = UsersDAO.getInstance();
						User user = usersDAO.findUserByLogin(login);
						if(user == null) {
							 result = usersDAO.addUserEmployeeOrClient(lastName, firstName, login, password, Integer.valueOf(profileId), Integer.valueOf(customerId), typology.getTypologyCode());
							 if(!result) {
								 request.setAttribute("message", "Echec d'ajout !");
							 }
						} else {
							request.setAttribute("message", "L'adresse mail " + login + " existe déjà !");
						}
					} else {
						request.setAttribute("message", "Tous les champs sont obligatoires !");
					}
				} else {
					UsersDAO usersDAO = UsersDAO.getInstance();
					User user = usersDAO.findUserByLogin(login);
					if(user == null) {
						 result = usersDAO.addUser(lastName, firstName, login, password, Integer.valueOf(profileId));
						 if(!result) {
							 request.setAttribute("message", "Echec d'ajout !");
						 }
					} else {
						request.setAttribute("message", "L'adresse mail " + login + " existe déjà !");
					}
				}
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public Boolean updateUser(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String id = request.getParameter("userId");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String typologyId = request.getParameter("typology");
		String profileId = request.getParameter("profile");
		String customerId = request.getParameter("customer");
		if(StringUtils.isNotEmpty(lastName)
				&& StringUtils.isNotEmpty(firstName)
				&& StringUtils.isNotEmpty(login)
				&& StringUtils.isNotEmpty(password)
				&& StringUtils.isNotEmpty(typologyId) && !"0".equals(typologyId)
				&& StringUtils.isNotEmpty(profileId) && !"0".equals(profileId)) {
			TypologiesDAO typologiesDAO = TypologiesDAO.getInstance();
			Typology typology = typologiesDAO.findTypologyByTypologieId(Integer.valueOf(typologyId));
			if("E".equals(typology.getTypologyCode())
					|| "C".equals(typology.getTypologyCode())) {
				if(StringUtils.isNotEmpty(customerId) && !"0".equals(customerId)) {
					UsersDAO usersDAO = UsersDAO.getInstance();
					result = usersDAO.updateUserEmployeeOrClient(Integer.valueOf(id), lastName, firstName, login, password, Integer.valueOf(profileId), Integer.valueOf(customerId));
					 if(!result) {
						 request.setAttribute("message", "Echec de modification !");
					 }
				} else {
					request.setAttribute("message", "Tous les champs sont obligatoires !");
				}
			} else {
				UsersDAO usersDAO = UsersDAO.getInstance();
				result = usersDAO.updateUser(Integer.valueOf(id), lastName, firstName, login, password, Integer.valueOf(profileId));
				 if(!result) {
					 request.setAttribute("message", "Echec de modification !");
				 }
			}
		} else {
			request.setAttribute("message", "Tous les champs sont obligatoires !");
		}
		return result;
	}
	
	public void deleteUser(HttpServletRequest request) throws NumberFormatException, ClassNotFoundException, SQLException {
		Boolean result = Boolean.FALSE;
		String id = request.getParameter("userId");
		if(StringUtils.isNotEmpty(id)) {
			UsersDAO usersDAO = UsersDAO.getInstance();
			User user = usersDAO.findUserByUserId(Integer.valueOf(id));
			String typologyCode = user.getProfile().getTypology().getTypologyCode();
			if("E".equals(typologyCode)
					|| "C".equals(typologyCode)) {
				result = usersDAO.deleteUserEmployeeOrClient(Integer.valueOf(id));
				if(!result) {
					request.setAttribute("message", "Echec de suppression !");
				}
			} else {
				result = usersDAO.deleteUser(Integer.valueOf(id));
				if(!result) {
					request.setAttribute("message", "Echec de suppression !");
				}
			}
		}
	}
}
