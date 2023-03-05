package com.toni.services;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.User;
import com.toni.dao.Connexion;
import com.toni.dao.UsersDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationService {
	private static AuthenticationService INSTANCE;
	
	private AuthenticationService() {
		
	}
	
	public static AuthenticationService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new AuthenticationService();
		}
		return INSTANCE;
	}
	
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		boolean isAuthenticated = false;
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsersDAO usersDAO = UsersDAO.getInstance();
		User user = usersDAO.findUserByLoginAndPasswor(username, password);
		
		HttpSession session = request.getSession();
		
		if(user != null) {
			if(user.getProfile() != null && user.getProfile().getTypology() != null) {
				session.setAttribute("user", user);
			}
			isAuthenticated = true;
		}
		
		session.setAttribute("isAuthenticated", Boolean.valueOf(isAuthenticated));
		
		if(isAuthenticated) {
			request.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
		} else {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("message", "Identifiants incorrects !!!");
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}
	
	public Boolean isAuthenticated(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean isAuthenticated = (Boolean)session.getAttribute("isAuthenticated");
		if(isAuthenticated != null) {
			return isAuthenticated;
		}
		return Boolean.FALSE;
	}
	
	public void goTo(HttpServletRequest request, HttpServletResponse response, String forward) throws ServletException, IOException {
		String goTo = "/WEB-INF/authentication.jsp";
		HttpSession session = request.getSession();
		Boolean isAuthenticated = (Boolean)session.getAttribute("isAuthenticated");
		if(isAuthenticated != null && Boolean.TRUE.equals(isAuthenticated)) {
			goTo = forward;
		}
		request.getServletContext().getRequestDispatcher(goTo).forward(request, response);
	}
	
	public void signOut(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		Connexion connexion = Connexion.getInstance();
		connexion.closeConnection();
		HttpSession session = request.getSession();
		session.setAttribute("isAuthenticated", Boolean.FALSE);
		request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
	}
}
