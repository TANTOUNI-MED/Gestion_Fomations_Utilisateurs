package com.toni.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.toni.beans.Request;
import com.toni.beans.User;
import com.toni.services.AuthenticationService;
import com.toni.services.EmployeesService;
import com.toni.services.FormationTypesService;
import com.toni.services.RequestsService;
import com.toni.utils.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditRequest
 */
@WebServlet("/EditRequestServlet")
public class EditRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditRequestServlet() {
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
			if("C".equals(typologyCode)) {
				String id = request.getParameter("requestId");
				boolean editMode = StringUtils.isNotEmpty(id);
				String formationTypeId = request.getParameter("formationType");
				String formationId = request.getParameter("formation");
				String number = request.getParameter("number");
				FormationTypesService formationTypesService = FormationTypesService.getInstance();
				EmployeesService employeesService = EmployeesService.getInstance();
				RequestsService requestsService = RequestsService.getInstance();
				try {
					Request req  = new Request();
					if(editMode) {
						request.setAttribute("title","Mise à jour de la demande");
						req = requestsService.findRequest(Integer.valueOf(id));
						if(StringUtils.isEmpty(formationId)) {
							formationId = req.getFormation().getFormationId().toString();
						}
						if(StringUtils.isEmpty(formationTypeId)) {
							formationTypeId = req.getFormation().getFormationType().getFormationTypeId().toString();
						}
					} else {
						if(StringUtils.isNotEmpty(number)) {
							req.setRequestNumber(number);
						} else {
							req.setRequestNumber(requestsService.generateRequestNumber());
						}
						request.setAttribute("title","Nouvelle demande");
					}
					if(StringUtils.isNotEmpty(formationTypeId)) {
						req.getFormation().getFormationType().setFormationTypeId(Integer.valueOf(formationTypeId));
						if(!"0".equals(formationTypeId)) {
							if(StringUtils.isNotEmpty(formationId)) {
								req.getFormation().setFormationId(Integer.valueOf(formationId));
								if(!"0".equals(formationId)) {
									if(editMode) {
										request.setAttribute("employees", employeesService.getEmployeesToEditRequest(request, formationId));
									} else {
										request.setAttribute("employees", employeesService.getEmployeesToCreateRequest(request));
									}
								}
							}
						}
					}
					request.setAttribute("editRequest", req);
					request.setAttribute("formationTypes", formationTypesService.getFormationTypes());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.getServletContext().getRequestDispatcher("/WEB-INF/editRequest.jsp").forward(request, response);
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
			String changeFormationType = request.getParameter("changeFormationType");
			String changeFormation = request.getParameter("changeFormation");
			if(Boolean.TRUE.equals(Boolean.valueOf(changeFormationType))
					|| Boolean.TRUE.equals(Boolean.valueOf(changeFormation))) {
				doGet(request, response);
			} else {
				RequestsService requestsService = RequestsService.getInstance();
				Boolean done = Boolean.FALSE;
				String id = request.getParameter("requestId");
				try {
					if(StringUtils.isNotEmpty(id)) {
						done = requestsService.updateRequest(request);
					} else {
						done = requestsService.addRequest(request);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				if(done) {
					request.getServletContext().getRequestDispatcher("/RequestsServlet").forward(request, response);
				} else {
					doGet(request, response);
				}
			}
		} else {
			request.getServletContext().getRequestDispatcher("/WEB-INF/authentication.jsp").forward(request, response);
		}
	}

}
