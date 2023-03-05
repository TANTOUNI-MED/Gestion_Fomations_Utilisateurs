package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.Profile;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.ProfilesService;
import com.toni.services.TypologiesService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditProfileServlet
 */
@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditProfileServlet() {
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
				String id = request.getParameter("profileId");
				TypologiesService typologiesService = TypologiesService.getInstance();
				try {
					Profile profile = new Profile();
					if(StringUtils.isNotEmpty(id)) {
						ProfilesService profilesService = ProfilesService.getInstance();
						profile = profilesService.findProfile(Integer.valueOf(id));
						request.setAttribute("title","Modification de profile " + profile.getProfileLabel());
					} else {
						request.setAttribute("title","Nouvel Profil");
					}
					request.setAttribute("editProfile",profile);
					request.setAttribute("typologies", typologiesService.getTypologies());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.setAttribute("activeMenu", "Profiles");
				request.getServletContext().getRequestDispatcher("/WEB-INF/editProfile.jsp").forward(request, response);
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
			
				ProfilesService profilesService = ProfilesService.getInstance();
				Boolean done = Boolean.FALSE;
				String id = request.getParameter("profileId");
				try {
					if(StringUtils.isNotEmpty(id)) {
						done = profilesService.updateProfile(request);
					} else {
						done = profilesService.addProfile(request);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				if(done) {
					request.getServletContext().getRequestDispatcher("/ProfilesServlet").forward(request, response);
				} else {
					doGet(request, response);
				}
			}else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		  }
	}

	

}
