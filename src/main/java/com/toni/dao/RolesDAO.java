package com.toni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toni.beans.Customer;
import com.toni.beans.Profile;
import com.toni.beans.Typology;
import com.toni.beans.Role;

public class RolesDAO {
private static RolesDAO INSTANCE;
	
	private RolesDAO() {
		
	}
	
	public static RolesDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new RolesDAO();
		}
		return INSTANCE;
	}
	
	public List<Role> getRoles() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Role> roles = new ArrayList<Role>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT r.*, p.*, t.* FROM  roles r left join profiles p on r.profile_id=p.profile_id left join typologies t on p.typology_id=t.typology_id;");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer roleId = resultSet.getInt("role_id");
			String rolelabel = resultSet.getString("role_label");
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
			Role role = new Role();
			role.setRoleId(roleId);
			role.setProfile(profile);
			role.setRoleLabel(rolelabel);
			roles.add(role);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return roles;
	}
	
	public Role findRoleByRoleId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Role role = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT r.*, p.*, t.* FROM roles r, profiles p, typologies t WHERE r.profile_id=p.profile_id and p.typology_id=t.typology_id and r.role_id=?;");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer roleId = resultSet.getInt("role_id");
			String rolelabel = resultSet.getString("role_label");
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
		    role = new Role();
			role.setRoleId(roleId);
			role.setProfile(profile);
			role.setRoleLabel(rolelabel);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return role;
	}
	
	
	public Role findRoleByLabel(String label) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Role role = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT r.*, p.*, t.* FROM roles r, profiles p, typologies t WHERE r.profile_id=p.profile_id and p.typology_id=t.typology_id and r.role_label=?;");
		preparedStatement.setString(1, label);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer roleId = resultSet.getInt("role_id");
			String rolelabel = resultSet.getString("role_label");
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
		    role = new Role();
			role.setRoleId(roleId);
			role.setProfile(profile);
			role.setRoleLabel(rolelabel);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return role;
	}
	
	
	public Boolean addRole(String roleLabel,Integer profileId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO roles(role_label, profile_id) values(?, ?)");
		preparedStatement.setString(1, roleLabel);
		preparedStatement.setInt(2, profileId);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}

	
	public Boolean updateRole(Integer id, String roleLabel, Integer profileId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE roles set role_label=?, profile_id=? where role_id=?");
		preparedStatement.setString(1, roleLabel);
		preparedStatement.setInt(2, profileId);
		preparedStatement.setInt(3, id);
		int result = preparedStatement.executeUpdate(); 
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	

	public Boolean deleteRole(Integer id) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("DELETE FROM roles where role_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
}
