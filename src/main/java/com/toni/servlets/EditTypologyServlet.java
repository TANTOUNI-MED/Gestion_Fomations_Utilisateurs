package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.Typology;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.TypologiesService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditTypologyServlet
 */
@WebServlet("/EditTypologyServlet")
public class EditTypologyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditTypologyServlet() {
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
				String id = request.getParameter("typologyId");
				try {
					Typology typology = new Typology();
					if(StringUtils.isNotEmpty(id)) {
						TypologiesService typologiesService = TypologiesService.getInstance();
						typology = typologiesService.getTypologyByTypologyId(Integer.valueOf(id));
						request.setAttribute("title","Modification de la typologie " + typology.getTypologyLabel());
					} else {
						request.setAttribute("title","Nouvelle typologie");
					}
					request.setAttribute("editTypology", typology);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/editTypology.jsp").forward(request, response);
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
			TypologiesService typologiesService = TypologiesService.getInstance();
			Boolean done = Boolean.FALSE;
			String id = request.getParameter("id");
			try {
				if(StringUtils.isNotEmpty(id)) {
					done = typologiesService.updateTypology(request);
				} else {
					done = typologiesService.addTypology(request);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			if(done) {
				request.getServletContext().getRequestDispatcher("/TypologiesServlet").forward(request, response);
			} else {
				doGet(request, response);
			}
		
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
