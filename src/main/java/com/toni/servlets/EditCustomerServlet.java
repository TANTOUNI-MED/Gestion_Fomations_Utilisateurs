package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.Customer;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.CustomersService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditCustomerServlet
 */
@WebServlet("/EditCustomerServlet")
public class EditCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditCustomerServlet() {
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
				String id = request.getParameter("customerId");
				try {
					Customer customer = new Customer();
					if(StringUtils.isNotEmpty(id)) {
						CustomersService customersService = CustomersService.getInstance();
						customer = customersService.findCustomer(Integer.valueOf(id));
						request.setAttribute("title","Modification du client " + customer.getCustomerLabel());
					} else {
						request.setAttribute("title","Nouveau client");
					}
					request.setAttribute("editCustomer", customer);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/editCustomer.jsp").forward(request, response);
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
			CustomersService customersService = CustomersService.getInstance();
			Boolean done = Boolean.FALSE;
			String id = request.getParameter("id");
			try {
				if(StringUtils.isNotEmpty(id)) {
					done = customersService.updateCustomer(request);
				} else {
					done = customersService.addCustomer(request);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			if(done) {
				request.getServletContext().getRequestDispatcher("/CustomersServlet").forward(request, response);
			} else {
				doGet(request, response);
			}
		
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
