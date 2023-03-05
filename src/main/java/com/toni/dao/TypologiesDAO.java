package com.toni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Typology;

public class TypologiesDAO {
	private static TypologiesDAO INSTANCE;
	
	private TypologiesDAO() {
		
	}
	
	public static TypologiesDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TypologiesDAO();
		}
		return INSTANCE;
	}
	
	public List<Typology> getTypologies() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Typology> typologies = new ArrayList<Typology>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT t.* FROM typologies t");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			Typology typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
			typologies.add(typology);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return typologies;
	}
	
	public Typology findTypologyByTypologieId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Typology typology = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT * FROM typologies where typology_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return typology;
	}
	
	public Typology findTypologyByCodeOrLabel(String code, String label) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Typology typology = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT * FROM typologies where typology_code=? or typology_label=?");
		preparedStatement.setString(1, code);
		preparedStatement.setString(2, label);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return typology;
	}
	
	public Boolean deleteTypology(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("delete from typologies where typology_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean addTypology(String code, String label) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO typologies(typology_code, typology_label) values(?, ?)");
		preparedStatement.setString(1, code);
		preparedStatement.setString(2, label);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean updateTypology(Integer id, String code, String label) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE typologies set typology_code=?, typology_label=? where typology_id=?");
		preparedStatement.setString(1, code);
		preparedStatement.setString(2, label);
		preparedStatement.setInt(3, id);
		int result = preparedStatement.executeUpdate(); 
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public List<Typology> getTypologiesByTypologyLabel(String label) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Typology> typologies = new ArrayList<Typology>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT * FROM typologies where typology_label like ?");
		preparedStatement.setString(1, "%"+label+"%");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			Typology typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
			
			typologies.add(typology);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return typologies;
	}
}
