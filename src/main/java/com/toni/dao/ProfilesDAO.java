package com.toni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Profile;
import com.toni.beans.Typology;
import com.toni.beans.User;

public class ProfilesDAO {
	private static ProfilesDAO INSTANCE;
	
	private ProfilesDAO() {
		
	}
	
	public static ProfilesDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ProfilesDAO();
		}
		return INSTANCE;
	}
	
	public List<Profile> getProfiles() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Profile> profiles = new ArrayList<Profile>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT p.*, t.* FROM profiles p join typologies t on p.typology_id=t.typology_id;");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer profileId = resultSet.getInt("profile_id");
			String profileCode = resultSet.getString("profile_code");
			String profileLabel = resultSet.getString("profile_label");
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			Typology typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
			Profile profile = new Profile();
			profile.setProfileId(profileId);
			profile.setProfileCode(profileCode);
			profile.setProfileLabel(profileLabel);
			profile.setTypology(typology);
		
			profiles.add(profile);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return profiles;
	}
	
	
	public List<Profile> getProfilesByTypologyId(Integer typologyId) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Profile> profiles = new ArrayList<Profile>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT p.* FROM profiles p where p.typology_id=?");
		preparedStatement.setInt(1, typologyId);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer profileId = resultSet.getInt("profile_id");
			String profileCode = resultSet.getString("profile_code");
			String profileLabel = resultSet.getString("profile_label");
			Profile profile = new Profile();
			profile.setProfileId(profileId);
			profile.setProfileCode(profileCode);
			profile.setProfileLabel(profileLabel);
			profiles.add(profile);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return profiles;
	}

		
	public Profile findProfileByProfileId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Profile profile = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT p.*, t.* FROM profiles p join typologies t on p.typology_id=t.typology_id where p.profile_id=? ;");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer profileId = resultSet.getInt("profile_id");
			String profileCode = resultSet.getString("profile_code");
			String profileLabel = resultSet.getString("profile_label");
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			Typology typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
		    profile = new Profile();
			profile.setProfileId(profileId);
			profile.setProfileCode(profileCode);
			profile.setProfileLabel(profileLabel);
			profile.setTypology(typology);
		
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return profile;
	}
	public Boolean addProfile(String profileCode,String profileLabel, Integer typologyId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO Profiles(profile_code, profile_label, typology_id) values(?, ?, ?)");
		preparedStatement.setString(1, profileCode);
		preparedStatement.setString(2, profileLabel);
		preparedStatement.setInt(3, typologyId);
		;
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	public Boolean updateProfile(Integer id,String profileCode,String profileLabel, Integer typologyId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE profiles set profile_code=?, profile_label=?, typology_id=? where profile_id=?");
		preparedStatement.setString(1, profileCode);
		preparedStatement.setString(2, profileLabel);
		preparedStatement.setInt(3, typologyId);
		preparedStatement.setInt(4, id);

		int result = preparedStatement.executeUpdate(); 
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	public Boolean deleteProfile(Integer id) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement =null;
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("DELETE FROM profiles where profile_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return  Boolean.valueOf(result>0);
				
		
	}
	
	
	public Profile findProfileByCode(String code) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Profile profile = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT p.*, t.* FROM profiles p join typologies t on p.typology_id=t.typology_id where p.profile_code=? ;");
		preparedStatement.setString(1, code);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer profileId = resultSet.getInt("profile_id");
			String profileCode = resultSet.getString("profile_code");
			String profileLabel = resultSet.getString("profile_label");
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			Typology typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
		    profile = new Profile();
			profile.setProfileId(profileId);
			profile.setProfileCode(profileCode);
			profile.setProfileLabel(profileLabel);
			profile.setTypology(typology);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return profile;
	}
	
	
}
