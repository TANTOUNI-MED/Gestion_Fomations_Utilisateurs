package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.ProfilesService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ProfilesServlet
 */
@WebServlet("/ProfilesServlet")
public class ProfilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ProfilesServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(authenticationService.isAuthenticated(request)) {
			HttpSession session = request.getSession();
			User userC = (User)session.getAttribute("user");
			String typologyCode = userC.getProfile().getTypology().getTypologyCode();
			if("A".equals(typologyCode)) {
				ProfilesService profilesService = ProfilesService.getInstance();
				String action = request.getParameter("action");
				try {
					if("delete".equals(action)) {
						profilesService.deleteProfile(request);
					}
					request.setAttribute("profiles", profilesService.getProfiles());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/profiles.jsp").forward(request, response);
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
			HttpSession session = request.getSession();
			User userC = (User)session.getAttribute("user");
			String typologyCode = userC.getProfile().getTypology().getTypologyCode();
			if("A".equals(typologyCode)) {
				ProfilesService profilesService = ProfilesService.getInstance();
				try {
					request.setAttribute("profiles", profilesService.findProfile(request));
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/profiles.jsp").forward(request, response);
			} else {
				request.getServletContext().getRequestDispatcher("/WEB-INF/accessDenied.jsp").forward(request, response);
			}
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
