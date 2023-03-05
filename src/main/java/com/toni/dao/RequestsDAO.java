package com.toni.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Customer;
import com.toni.beans.Formation;
import com.toni.beans.FormationType;
import com.toni.beans.Request;

public class RequestsDAO {
private static RequestsDAO INSTANCE;
	
	private RequestsDAO() {
		
	}
	
	public static RequestsDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new RequestsDAO();
		}
		return INSTANCE;
	}
	
	public Boolean acceptRequest(Integer requestId, Integer formationId, List<Integer> employeesId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		int result = 0;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("update requests set request_status=? where request_id=?");
		preparedStatement.setString(1, "accepted");
		preparedStatement.setInt(2, requestId);
		result = preparedStatement.executeUpdate();
		if(result > 0) {
			for(Integer employeeId: employeesId) {
				preparedStatement = connection.prepareStatement("INSERT INTO employees_formations(employee_id, formation_id) values(?, ?)");
				preparedStatement.setInt(1, employeeId);
				preparedStatement.setInt(2, formationId);
				result = preparedStatement.executeUpdate();
			}
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Request findRequestByRequestId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Request request = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT r.*, f.formation_code, f.formation_label, ft.formation_type_id, ft.formation_type_code, ft.formation_type_label FROM requests r, formations f, formation_types ft WHERE r.formation_id=f.formation_id AND f.formation_type_id=ft.formation_type_id and r.request_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			Integer formationid = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			Integer requestId = resultSet.getInt("request_id");
			String requestNumber = resultSet.getString("request_number");
			Date requestDate = resultSet.getDate("request_date");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			Formation formation = new Formation();
			formation.setFormationId(formationid);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationType(formationType);
			
			request = new Request();
			request.setRequestId(requestId);
			request.setRequestNumber(requestNumber);
			request.setRequestDate(requestDate);
			request.setFormation(formation);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return request;
	} 
	
	public List<Request> getRequestsByCustomerId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Request> requests = new ArrayList<Request>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT r.*, f.formation_code, f.formation_label, ft.formation_type_id, ft.formation_type_code, ft.formation_type_label, c.customer_external_code, c.customer_label FROM requests r, formations f, formation_types ft, customers c WHERE r.customer_id=c.customer_id AND r.formation_id=f.formation_id AND f.formation_type_id=ft.formation_type_id AND r.customer_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			Integer formationid = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			Integer customerId = resultSet.getInt("customer_id");
			String customerExternalCode = resultSet.getString("customer_external_code");
			String customerLabel = resultSet.getString("customer_label");
			Integer requestId = resultSet.getInt("request_id");
			String requestNumber = resultSet.getString("request_number");
			Date requestDate = resultSet.getDate("request_date");
			String requestStatus = resultSet.getString("request_status");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			Formation formation = new Formation();
			formation.setFormationId(formationid);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationType(formationType);
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerExternalCode(customerExternalCode);
			customer.setCustomerLabel(customerLabel);
			
			Request request = new Request();
			request.setRequestId(requestId);
			request.setRequestNumber(requestNumber);
			request.setRequestDate(requestDate);
			request.setFormation(formation);
			request.setCustomer(customer);
			
			String statusLabel;
			String statusColor;
			boolean showActionsButtons = false;
			if("sent".equals(requestStatus)) {
				statusLabel = "Envoyée";
				statusColor = "primary";
				showActionsButtons = true;
			} else {
				statusLabel = "Traitée";
				statusColor = "success";
			}
			request.setRequestStatusLabel(statusLabel);
			request.setRequestStatusColor(statusColor);
			request.setShowActionsButtons(showActionsButtons);
			
			requests.add(request);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return requests;
	}
	
	public List<Request> getRequests() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Request> requests = new ArrayList<Request>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT r.*, f.formation_code, f.formation_label, ft.formation_type_id, ft.formation_type_code, ft.formation_type_label, c.customer_external_code, c.customer_label FROM requests r, formations f, formation_types ft, customers c WHERE r.customer_id=c.customer_id AND r.formation_id=f.formation_id AND f.formation_type_id=ft.formation_type_id");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			Integer formationid = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			Integer customerId = resultSet.getInt("customer_id");
			String customerExternalCode = resultSet.getString("customer_external_code");
			String customerLabel = resultSet.getString("customer_label");
			Integer requestId = resultSet.getInt("request_id");
			String requestNumber = resultSet.getString("request_number");
			Date requestDate = resultSet.getDate("request_date");
			String requestStatus = resultSet.getString("request_status");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			Formation formation = new Formation();
			formation.setFormationId(formationid);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationType(formationType);
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerExternalCode(customerExternalCode);
			customer.setCustomerLabel(customerLabel);
			
			Request request = new Request();
			request.setRequestId(requestId);
			request.setRequestNumber(requestNumber);
			request.setRequestDate(requestDate);
			request.setFormation(formation);
			request.setCustomer(customer);
			
			String statusLabel;
			String statusColor;
			boolean showAcceptButton = false;
			if("sent".equals(requestStatus)) {
				statusLabel = "A traiter";
				statusColor = "primary";
				showAcceptButton = true;
			} else {
				statusLabel = "Traitée";
				statusColor = "success";
			}
			request.setRequestStatusLabel(statusLabel);
			request.setRequestStatusColor(statusColor);
			request.setShowAcceptButton(showAcceptButton);
			
			requests.add(request);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return requests;
	}
	
	public List<Request> getRequestsByNumber(String number) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Request> requests = new ArrayList<Request>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT r.*, f.formation_code, f.formation_label, ft.formation_type_id, ft.formation_type_code, ft.formation_type_label, c.customer_external_code, c.customer_label FROM requests r, formations f, formation_types ft, customers c WHERE r.request_number LIKE ? AND r.customer_id=c.customer_id AND r.formation_id=f.formation_id AND f.formation_type_id=ft.formation_type_id");
		preparedStatement.setString(1, "%"+number+"%");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			Integer formationid = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			Integer customerId = resultSet.getInt("customer_id");
			String customerExternalCode = resultSet.getString("customer_external_code");
			String customerLabel = resultSet.getString("customer_label");
			Integer requestId = resultSet.getInt("request_id");
			String requestNumber = resultSet.getString("request_number");
			Date requestDate = resultSet.getDate("request_date");
			String requestStatus = resultSet.getString("request_status");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			Formation formation = new Formation();
			formation.setFormationId(formationid);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationType(formationType);
			
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerExternalCode(customerExternalCode);
			customer.setCustomerLabel(customerLabel);
			
			Request request = new Request();
			request.setRequestId(requestId);
			request.setRequestNumber(requestNumber);
			request.setRequestDate(requestDate);
			request.setFormation(formation);
			request.setCustomer(customer);
			
			String statusLabel;
			String statusColor;
			boolean showAcceptButton = false;
			if("sent".equals(requestStatus)) {
				statusLabel = "A traiter";
				statusColor = "primary";
				showAcceptButton = true;
			} else {
				statusLabel = "Traitée";
				statusColor = "success";
			}
			request.setRequestStatusLabel(statusLabel);
			request.setRequestStatusColor(statusColor);
			request.setShowAcceptButton(showAcceptButton);
			
			requests.add(request);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return requests;
	}
	
	public Integer getMaxRequestsId() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer maxRequestId = new Integer(0);
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT MAX(r.request_id) as max_request_id from requests r");
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			maxRequestId = resultSet.getInt("max_request_id");
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return maxRequestId;
	}
	
	public Boolean deleteRequest(Integer requestId) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("delete from requests where request_id=?");
		preparedStatement.setInt(1, requestId);
		int result = preparedStatement.executeUpdate();
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean addRequest(String requestNumber, Date requestDate, String requestStatus, Integer customerId, Integer formationId, List<Integer> employeesId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO requests(request_number, request_date, request_status, customer_id, formation_id) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, requestNumber);
		preparedStatement.setDate(2, requestDate);
		preparedStatement.setString(3, requestStatus);
		preparedStatement.setInt(4, customerId);
		preparedStatement.setInt(5, formationId);
		int result = preparedStatement.executeUpdate();
		
		if(result>0) {
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
                Integer requestId = generatedKeys.getInt(1);
                for(Integer employeeId: employeesId) {
    				preparedStatement = connection.prepareStatement("INSERT INTO employees_requests values(?, ?)");
    				preparedStatement.setInt(1, employeeId);
    				preparedStatement.setInt(2, requestId);
    				result = preparedStatement.executeUpdate();
    			}
            }
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean updateRequest(Integer requestId, Date requestDate, String requestStatus, Integer formationId, List<Integer> employeesId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("update requests set request_date=?, request_status=?, formation_id=? where request_id=?");
		preparedStatement.setDate(1, requestDate);
		preparedStatement.setString(2, requestStatus);
		preparedStatement.setInt(3, formationId);
		preparedStatement.setInt(4, requestId);
		int result = preparedStatement.executeUpdate();
		
		if(result>0) {
			preparedStatement = connection.prepareStatement("delete from employees_requests where request_id=?");
			preparedStatement.setInt(1, requestId);
			result = preparedStatement.executeUpdate();
			
			if(result>0) {
				for(Integer employeeId: employeesId) {
					preparedStatement = connection.prepareStatement("INSERT INTO employees_requests values(?, ?)");
					preparedStatement.setInt(1, employeeId);
					preparedStatement.setInt(2, requestId);
					result = preparedStatement.executeUpdate();
				}
			}
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
}
