package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.services.AuthenticationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AuthenticationServlet() {
    	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.removeAttribute("message");
		String signOut = request.getParameter("signOut");
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(signOut != null) {
			try {
				authenticationService.signOut(request, response);
			} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
			}
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		try {
			authenticationService.authenticate(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
