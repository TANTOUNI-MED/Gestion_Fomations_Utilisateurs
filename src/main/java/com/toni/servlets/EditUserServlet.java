package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.Typology;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.CustomersService;
import com.toni.services.TypologiesService;
import com.toni.services.UsersService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditUserServlet() {
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
				String id = request.getParameter("userId");
				String typologyId = request.getParameter("typology");
				TypologiesService typologiesService = TypologiesService.getInstance();
				try {
					User user = new User();
					if(StringUtils.isNotEmpty(id)) {
						UsersService usersService = UsersService.getInstance();
						user = usersService.findUser(Integer.valueOf(id));
						request.setAttribute("title","Modification de l'utilisateur " + user.getUserLastName() + " " + user.getUserFirstName());
					} else {
						request.setAttribute("title","Nouvel utilisateur");
					}
					if(StringUtils.isNotEmpty(typologyId)) {
						String lastName = request.getParameter("lastName");
						String firstName = request.getParameter("firstName");
						String login = request.getParameter("login");
						String password = request.getParameter("password");
						user.setUserLastName(lastName);
						user.setUserFirstName(firstName);
						user.setUserLogin(login);
						user.setUserPassword(password);
						user.getProfile().getTypology().setTypologyId(Integer.valueOf(typologyId));
					}
					Integer userTypologyId = user.getProfile().getTypology().getTypologyId();
					if(userTypologyId != null && userTypologyId != 0) {
						Typology typology = typologiesService.getTypologyByTypologyId(userTypologyId);
						if("E".equals(typology.getTypologyCode())
								|| "C".equals(typology.getTypologyCode())) {
							CustomersService customersService = CustomersService.getInstance();
							request.setAttribute("customers", customersService.getCustomers());
						}
					}
					request.setAttribute("editUser", user);
					request.setAttribute("typologies", typologiesService.getTypologies());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.setAttribute("activeMenu", "users");
				request.getServletContext().getRequestDispatcher("/WEB-INF/editUser.jsp").forward(request, response);
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
				UsersService usersService = UsersService.getInstance();
				Boolean done = Boolean.FALSE;
				String id = request.getParameter("userId");
				try {
					if(StringUtils.isNotEmpty(id)) {
						done = usersService.updateUser(request);
					} else {
						done = usersService.addUser(request);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				if(done) {
					request.getServletContext().getRequestDispatcher("/UsersServlet").forward(request, response);
				} else {
					doGet(request, response);
				}
			}
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
