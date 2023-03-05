package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.services.AuthenticationService;
import com.toni.services.FormationsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormationsServlet
 */
@WebServlet("/FormationsServlet")
public class FormationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FormationsServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(authenticationService.isAuthenticated(request)) {
			FormationsService formationsService = FormationsService.getInstance();
			String action = request.getParameter("action");
			try {
				if("delete".equals(action)) {
					formationsService.deleteFormation(request);
				} else if("start".equals(action)) {
					formationsService.startFormation(request);
				} else if("end".equals(action)) {
					formationsService.endFormation(request);
				}
				request.setAttribute("formations", formationsService.getFormations(request));
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("activeMenu", "formations");
			request.getServletContext().getRequestDispatcher("/WEB-INF/formations.jsp").forward(request, response);
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
