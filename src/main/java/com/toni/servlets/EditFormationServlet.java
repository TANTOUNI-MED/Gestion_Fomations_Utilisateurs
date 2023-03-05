package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.Formation;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.FormationTypesService;
import com.toni.services.FormationsService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditFormationServlet
 */
@WebServlet("/EditFormationServlet")
public class EditFormationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditFormationServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(authenticationService.isAuthenticated(request)) {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			String typologyCode = user.getProfile().getTypology().getTypologyCode();
			if("A".equals(typologyCode)) {
				String id = request.getParameter("formationId");
				try {
					Formation formation = new Formation();
					if(StringUtils.isNotEmpty(id)) {
						FormationsService formationsService = FormationsService.getInstance();
						formation = formationsService.findFormation(Integer.valueOf(id));
						request.setAttribute("title","Modification de la formation " + formation.getFormationLabel());
					} else {
						request.setAttribute("title","Nouvelle formation");
					}
					request.setAttribute("editFormation", formation);
					FormationTypesService formationTypesService = FormationTypesService.getInstance();
					request.setAttribute("formationTypes", formationTypesService.getFormationTypes());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/editFormation.jsp").forward(request, response);
			} else {
				request.getServletContext().getRequestDispatcher("/WEB-INF/accessDenied.jsp").forward(request, response);
			}
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(authenticationService.isAuthenticated(request)) {
			FormationsService formationsService = FormationsService.getInstance();
			Boolean done = Boolean.FALSE;
			String id = request.getParameter("id");
			try {
				if(StringUtils.isNotEmpty(id)) {
					done = formationsService.updateFormation(request);
				} else {
					done = formationsService.addFormation(request);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			if(done) {
				request.getServletContext().getRequestDispatcher("/FormationsServlet").forward(request, response);
			} else {
				doGet(request, response);
			}
		
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
