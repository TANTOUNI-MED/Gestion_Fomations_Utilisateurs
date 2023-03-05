package com.toni.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Formation;
import com.toni.beans.FormationType;

public class FormationsDAO {
	private static FormationsDAO INSTANCE;
	
	private FormationsDAO() {
		
	}
	
	public static FormationsDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new FormationsDAO();
		}
		return INSTANCE;
	}
	
	public List<Formation> getFormationsByEmployeeEmail(String email) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Formation> formations = new ArrayList<Formation>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT ef.start_date, ef.end_date, f.*, ft.formation_type_code, ft.formation_type_label from employees e, employees_formations ef, formations f, formation_types ft where e.employee_email=? and e.employee_id=ef.employee_id and ef.formation_id=f.formation_id and f.formation_type_id=ft.formation_type_id");
		preparedStatement.setString(1, email);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationId = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			String formationDescription = resultSet.getString("formation_description");
			Integer formationDuration = resultSet.getInt("formation_duration");
			String formationUrl = resultSet.getString("formation_url");
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			Date startDate = resultSet.getDate("start_date");
			Date endDate = resultSet.getDate("end_date");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			Formation formation = new Formation();
			formation.setFormationId(formationId);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationDescription(formationDescription);
			formation.setFormationDuration(formationDuration);
			formation.setFormationUrl(formationUrl);
			formation.setFormationType(formationType);
			
			String statusLabel;
			String statusColor;
			boolean showButton = false;
			String buttonLabel = null;
			if(startDate == null) {
				statusLabel = "A passer";
				statusColor = "primary";
				showButton = true;
				buttonLabel = "Passer";
			} else {
				if(endDate != null) {
					statusLabel = "Passée";
					statusColor = "success";
				} else {
					LocalDate sd = startDate.toLocalDate();
					LocalDate cd = LocalDate.now();
					LocalDate rd = sd.plusMonths(formationDuration);
					int result = rd.compareTo(cd);
					if(result > 0) {
						statusLabel = "En cours";
						statusColor = "warning";
						showButton = true;
						buttonLabel = "Terminer";
					} else {
						statusLabel = "Expirée";
						statusColor = "danger";
					}
				}
			}
			formation.setStatusLabel(statusLabel);
			formation.setStatusColor(statusColor);
			formation.setShowButton(showButton);
			formation.setButtonLabel(buttonLabel);
			
			formations.add(formation);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formations;
	}
	
	public List<Formation> getFormations() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Formation> formations = new ArrayList<Formation>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT f.*, ft.formation_type_code, ft.formation_type_label from formations f, formation_types ft where f.formation_type_id=ft.formation_type_id");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationId = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			String formationDescription = resultSet.getString("formation_description");
			Integer formationDuration = resultSet.getInt("formation_duration");
			String formationUrl = resultSet.getString("formation_url");
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			Formation formation = new Formation();
			formation.setFormationId(formationId);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationDescription(formationDescription);
			formation.setFormationDuration(formationDuration);
			formation.setFormationUrl(formationUrl);
			formation.setFormationType(formationType);
			
			formations.add(formation);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formations;
	}
	
	public List<Formation> getFormationsByFormationTypeId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Formation> formations = new ArrayList<Formation>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT f.* from formations f where f.formation_type_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationId = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			String formationDescription = resultSet.getString("formation_description");
			Integer formationDuration = resultSet.getInt("formation_duration");
			String formationUrl = resultSet.getString("formation_url");
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			
			Formation formation = new Formation();
			formation.setFormationId(formationId);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationDescription(formationDescription);
			formation.setFormationDuration(formationDuration);
			formation.setFormationUrl(formationUrl);
			formation.setFormationType(formationType);
			
			formations.add(formation);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formations;
	}
	
	public Formation findFormationByRequestId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Formation formation = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT f.*, ft.formation_type_code, ft.formation_type_label from requests r, formations f, formation_types ft where r.formation_id=f.formation_id and f.formation_type_id=ft.formation_type_id and r.request_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer formationId = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			String formationDescription = resultSet.getString("formation_description");
			Integer formationDuration = resultSet.getInt("formation_duration");
			String formationUrl = resultSet.getString("formation_url");
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			formation = new Formation();
			formation.setFormationId(formationId);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationDescription(formationDescription);
			formation.setFormationDuration(formationDuration);
			formation.setFormationUrl(formationUrl);
			formation.setFormationType(formationType);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formation;
	}
	
	public Formation findFormationByFormationId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Formation formation = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT f.*, ft.formation_type_code, ft.formation_type_label from formations f, formation_types ft where f.formation_type_id=ft.formation_type_id and f.formation_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer formationId = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			String formationDescription = resultSet.getString("formation_description");
			Integer formationDuration = resultSet.getInt("formation_duration");
			String formationUrl = resultSet.getString("formation_url");
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			formation = new Formation();
			formation.setFormationId(formationId);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationDescription(formationDescription);
			formation.setFormationDuration(formationDuration);
			formation.setFormationUrl(formationUrl);
			formation.setFormationType(formationType);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formation;
	}
	
	public Formation findFormationByFormationCodeOrFormationLabel(String code, String label) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Formation formation = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT f.*, ft.formation_type_code, ft.formation_type_label from formations f, formation_types ft where f.formation_type_id=ft.formation_type_id and (f.formation_code=? or f.formation_label=?)");
		preparedStatement.setString(1, code);
		preparedStatement.setString(2, label);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer formationId = resultSet.getInt("formation_id");
			String formationCode = resultSet.getString("formation_code");
			String formationLabel = resultSet.getString("formation_label");
			String formationDescription = resultSet.getString("formation_description");
			Integer formationDuration = resultSet.getInt("formation_duration");
			String formationUrl = resultSet.getString("formation_url");
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			
			formation = new Formation();
			formation.setFormationId(formationId);
			formation.setFormationCode(formationCode);
			formation.setFormationLabel(formationLabel);
			formation.setFormationDescription(formationDescription);
			formation.setFormationDuration(formationDuration);
			formation.setFormationUrl(formationUrl);
			formation.setFormationType(formationType);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formation;
	}
	
	public Boolean addFormation(String code, String label, String description, Integer duration, String url, Integer formationTypeId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO formations(formation_code, formation_label, formation_description, formation_duration, formation_url, formation_type_id) values(?, ?, ?, ?, ?, ?)");
		preparedStatement.setString(1, code);
		preparedStatement.setString(2, label);
		preparedStatement.setString(3, description);
		preparedStatement.setInt(4, duration);
		preparedStatement.setString(5, url);
		preparedStatement.setInt(6, formationTypeId);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean updateFormation(Integer id, String code, String label, String description, Integer duration, String url, Integer formationTypeId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("update formations set formation_code=?, formation_label=?, formation_description=?, formation_duration=?, formation_url=?, formation_type_id=? where formation_id=?");
		preparedStatement.setString(1, code);
		preparedStatement.setString(2, label);
		preparedStatement.setString(3, description);
		preparedStatement.setInt(4, duration);
		preparedStatement.setString(5, url);
		preparedStatement.setInt(6, formationTypeId);
		preparedStatement.setInt(7, id);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean deleteFomration(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("delete from formations where formation_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean startFomration(Integer formationId, Integer employeeId, Date startDate) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("update employees_formations set start_date=? where formation_id=? and employee_id=?");
		preparedStatement.setDate(1, startDate);
		preparedStatement.setInt(2, formationId);
		preparedStatement.setInt(3, employeeId);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean endFomration(Integer formationId, Integer employeeId, Date endDate) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("update employees_formations set end_date=? where formation_id=? and employee_id=?");
		preparedStatement.setDate(1, endDate);
		preparedStatement.setInt(2, formationId);
		preparedStatement.setInt(3, employeeId);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
}
