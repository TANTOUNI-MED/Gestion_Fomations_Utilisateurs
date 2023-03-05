package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.Typology;
import com.toni.beans.Role;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.RolesService;
import com.toni.services.TypologiesService;
import com.toni.services.UsersService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class EditRoleServlet
 */
@WebServlet("/EditRoleServlet")
public class EditRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditRoleServlet() {
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
				String id = request.getParameter("roleId");
				String typologyId = request.getParameter("typology");
				TypologiesService typologiesService = TypologiesService.getInstance();
				try {
					Role role = new Role();
					if(StringUtils.isNotEmpty(id)) {
						RolesService rolesService = RolesService.getInstance();
						role = rolesService.findRole(Integer.valueOf(id));
						request.setAttribute("title","Modification du role " + role.getRoleLabel());
					} else {
						request.setAttribute("title","Nouvel role");
					}
					if(StringUtils.isNotEmpty(typologyId)) {
						String roleLabel = request.getParameter("roleLabel");
						role.setRoleLabel(roleLabel);
						role.getProfile().getTypology().setTypologyId(Integer.valueOf(typologyId));
					}
					request.setAttribute("editRole", role);
					request.setAttribute("typologies", typologiesService.getTypologies());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/editRole.jsp").forward(request, response);
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
			String changeTypology = request.getParameter("changeTypology");
			if(Boolean.TRUE.equals(Boolean.valueOf(changeTypology))) {
				doGet(request, response);
			} else {
				RolesService rolesService = RolesService.getInstance();
				Boolean done = Boolean.FALSE;
				String id = request.getParameter("roleId");
				try {
					if(StringUtils.isNotEmpty(id)) {
						done = rolesService.updateRole(request);
					} else {
						done = rolesService.addRole(request);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				if(done) {
					request.getServletContext().getRequestDispatcher("/RolesServlet").forward(request, response);
				} else {
					doGet(request, response);
				}
			}
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
