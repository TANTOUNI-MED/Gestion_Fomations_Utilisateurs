package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.TypologiesService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class TypologiesServlet
 */
@WebServlet("/TypologiesServlet")
public class TypologiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TypologiesServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthenticationService authenticationService = AuthenticationService.getInstance();
		if(authenticationService.isAuthenticated(request)) {
			HttpSession session = request.getSession();
			User userC = (User)session.getAttribute("user");
			String typologyCode = userC.getProfile().getTypology().getTypologyCode();
			if("A".equals(typologyCode)) {
				TypologiesService typologiesService = TypologiesService.getInstance();
				String action = request.getParameter("action");
				try {
					if("delete".equals(action)) {
						typologiesService.deleteTypology(request);
					}
					request.setAttribute("typologies", typologiesService.getTypologies());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/typologies.jsp").forward(request, response);
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
				TypologiesService typologiesService = TypologiesService.getInstance();
				try {
					request.setAttribute("typologies", typologiesService.getTypologiesByTypologyLabel(request));
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/typologies.jsp").forward(request, response);
			} else {
				request.getServletContext().getRequestDispatcher("/WEB-INF/accessDenied.jsp").forward(request, response);
			}
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
