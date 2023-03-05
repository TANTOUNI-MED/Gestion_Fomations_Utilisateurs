package com.toni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.FormationType;
import com.toni.beans.Profile;
import com.toni.beans.Typology;

public class FormationTypesDAO {
	private static FormationTypesDAO INSTANCE;
	
	private FormationTypesDAO() {
		
	}
	
	public static FormationTypesDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new FormationTypesDAO();
		}
		return INSTANCE;
	}
	
	public List<FormationType> getFormationTypes() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FormationType> formationTypes = new ArrayList<FormationType>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT ft.* FROM formation_types ft");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			FormationType formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
			formationTypes.add(formationType);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formationTypes;
	}
		
	public FormationType findFormationTypeById(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		FormationType formationType = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT ft.* FROM formation_types ft where ft.formation_type_id=? ;");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
		
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formationType;
	}
	public Boolean addFormationType(String formationTypeCode,String formationTypeLabel) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO formation_types(formation_type_code, formation_type_label) values(?, ?)");
		preparedStatement.setString(1, formationTypeCode);
		preparedStatement.setString(2, formationTypeLabel);
		;
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	public Boolean updateFormationType(Integer id,String formationTypeCode,String formationTypeLabel) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE formation_types set formation_type_code=?, formation_type_label=? where formation_type_id=?");
		preparedStatement.setString(1, formationTypeCode);
		preparedStatement.setString(2, formationTypeLabel);
		preparedStatement.setInt(3, id);

		int result = preparedStatement.executeUpdate(); 
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	public Boolean deleteFormationType(Integer id) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement =null;
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("DELETE FROM formation_types where formation_type_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return  Boolean.valueOf(result>0);
				
		
	}
	
	
	public FormationType findFormationTypeByCode(String code) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		FormationType formationType = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT ft.* FROM formation_types ft where ft.formation_type_code=? ;");
		preparedStatement.setString(1, code);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer formationTypeId = resultSet.getInt("formation_type_id");
			String formationTypeCode = resultSet.getString("formation_type_code");
			String formationTypeLabel = resultSet.getString("formation_type_label");
			formationType = new FormationType();
			formationType.setFormationTypeId(formationTypeId);
			formationType.setFormationTypeCode(formationTypeCode);
			formationType.setFormationTypeLabel(formationTypeLabel);
		
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return formationType;
	}
	
}
