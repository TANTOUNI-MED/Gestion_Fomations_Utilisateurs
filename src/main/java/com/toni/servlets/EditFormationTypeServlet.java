package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.FormationType;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.FormationTypesService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditFormationTypeServlet
 */
@WebServlet("/EditFormationTypeServlet")
public class EditFormationTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditFormationTypeServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(authenticationService.isAuthenticated(request)) {
			HttpSession session = request.getSession();
			User userC = (User)session.getAttribute("user");
			String typologyCode = userC.getProfile().getTypology().getTypologyCode();
			if("A".equals(typologyCode)) {
				String id = request.getParameter("formationTypeId");
				try {
					FormationType formationType = new FormationType();
					if(StringUtils.isNotEmpty(id)) {
						FormationTypesService formationTypesService = FormationTypesService.getInstance();
						formationType = formationTypesService.findFormationType(Integer.valueOf(id));
						request.setAttribute("title","Modification du type de formation " + formationType.getFormationTypeLabel());
					} else {
						request.setAttribute("title","Nouveau type de formation");
					}
					request.setAttribute("editFormationType",formationType);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/editFormationType.jsp").forward(request, response);
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
		request.setCharacterEncoding("UTF-8");
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(authenticationService.isAuthenticated(request)) {
			
			FormationTypesService formationTypesService = FormationTypesService.getInstance();
				Boolean done = Boolean.FALSE;
				String id = request.getParameter("formationTypeId");
				try {
					if(StringUtils.isNotEmpty(id)) {
						done = formationTypesService.updateFormationType(request);
					} else {
						done = formationTypesService.addFormationType(request);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				if(done) {
					request.getServletContext().getRequestDispatcher("/FormationTypesServlet").forward(request, response);
				} else {
					doGet(request, response);
				}
			}else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		  }
	}

}
