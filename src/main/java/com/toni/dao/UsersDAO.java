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
import com.toni.beans.User;

public class UsersDAO {
	private static UsersDAO INSTANCE;
	
	private UsersDAO() {
		
	}
	
	public static UsersDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UsersDAO();
		}
		return INSTANCE;
	}
	
	public List<User> getUsers() throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<User>();
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT c.*, u.*, p.*, t.* FROM customers c right join users u on c.customer_id=u.customer_id left join profiles p on u.profile_id=p.profile_id left join typologies t on p.typology_id=t.typology_id;");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Integer userId = resultSet.getInt("user_id");
			String userLastName = resultSet.getString("user_last_name");
			String userFirstName = resultSet.getString("user_first_name");
			String userLogin = resultSet.getString("user_login");
			String userPassword = resultSet.getString("user_password");
			Integer profileId = resultSet.getInt("profile_id");
			String profileCode = resultSet.getString("profile_code");
			String profileLabel = resultSet.getString("profile_label");
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			Integer customerId = resultSet.getInt("customer_id");
			String customerLabel = resultSet.getString("customer_label");
			Typology typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
			Profile profile = new Profile();
			profile.setProfileId(profileId);
			profile.setProfileCode(profileCode);
			profile.setProfileLabel(profileLabel);
			profile.setTypology(typology);
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerLabel(customerLabel);
			User user = new User();
			user.setUserId(userId);
			user.setUserLastName(userLastName);
			user.setUserFirstName(userFirstName);
			user.setUserLogin(userLogin);
			user.setUserPassword(userPassword);
			user.setProfile(profile);
			user.setCustomer(customer);
			users.add(user);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return users;
	}
	
	public User findUserByUserId(Integer id) throws ClassNotFoundException, SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT u.*, p.*, t.* FROM users u, profiles p, typologies t WHERE u.profile_id=p.profile_id and p.typology_id=t.typology_id and u.user_id=?;");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer userId = resultSet.getInt("user_id");
			String userLastName = resultSet.getString("user_last_name");
			String userFirstName = resultSet.getString("user_first_name");
			String userLogin = resultSet.getString("user_login");
			String userPassword = resultSet.getString("user_password");
			Integer customerId = resultSet.getInt("customer_id");
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
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			user = new User();
			user.setUserId(userId);
			user.setUserLastName(userLastName);
			user.setUserFirstName(userFirstName);
			user.setUserLogin(userLogin);
			user.setUserPassword(userPassword);
			user.setCustomer(customer);
			user.setProfile(profile);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return user;
	}
	
	public User findUserByLoginAndPasswor(String login, String password) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT u.*, t.typology_id, t.typology_code, t.typology_label FROM users u, profiles p, typologies t WHERE u.profile_id=p.profile_id and p.typology_id=t.typology_id AND u.user_login = ? AND u.user_password = ?;");
		preparedStatement.setString(1, login);
		preparedStatement.setString(2, password);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer userId = resultSet.getInt("user_id");
			String userLastName = resultSet.getString("user_last_name");
			String userFirstName = resultSet.getString("user_first_name");
			String userLogin = resultSet.getString("user_login");
			String userPassword = resultSet.getString("user_password");
			Integer typologyId = resultSet.getInt("typology_id");
			String typologyCode = resultSet.getString("typology_code");
			String typologyLabel = resultSet.getString("typology_label");
			Integer customerId = resultSet.getInt("customer_id");
			user = new User();
			user.setUserId(userId);
			user.setUserLastName(userLastName);
			user.setUserFirstName(userFirstName);
			user.setUserLogin(userLogin);
			user.setUserPassword(userPassword);
			Profile profile = new Profile();
			Typology typology = new Typology();
			typology.setTypologyId(typologyId);
			typology.setTypologyCode(typologyCode);
			typology.setTypologyLabel(typologyLabel);
			profile.setTypology(typology);
			user.setProfile(profile);
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			user.setCustomer(customer);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return user;
	}
	
	public User findUserByLogin(String login) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("SELECT u.* FROM users u, profiles p, typologies t WHERE u.profile_id=p.profile_id and p.typology_id=t.typology_id AND u.user_login = ?;");
		preparedStatement.setString(1, login);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Integer userId = resultSet.getInt("user_id");
			String userLastName = resultSet.getString("user_last_name");
			String userFirstName = resultSet.getString("user_first_name");
			String userLogin = resultSet.getString("user_login");
			String userPassword = resultSet.getString("user_password");
			user = new User();
			user.setUserId(userId);
			user.setUserLastName(userLastName);
			user.setUserFirstName(userFirstName);
			user.setUserLogin(userLogin);
			user.setUserPassword(userPassword);
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		if(resultSet != null) {
			resultSet.close();
		}
		
		return user;
	}
	
	public Boolean addUserEmployeeOrClient(String lastName, String firstName, String login, String password, Integer profileId, Integer customerId, String typology) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO users(user_last_name, user_first_name, user_login, user_password, profile_id, customer_id) values(?, ?, ?, ?, ?, ?)");
		preparedStatement.setString(1, lastName);
		preparedStatement.setString(2, firstName);
		preparedStatement.setString(3, login);
		preparedStatement.setString(4, password);
		preparedStatement.setInt(5, profileId);
		preparedStatement.setInt(6, customerId);
		int result = preparedStatement.executeUpdate();
		
		if("E".equals(typology) && result>0) {
			preparedStatement = connection.prepareStatement("INSERT INTO employees(employee_last_name, employee_first_name, employee_email, customer_id) values(?, ?, ?, ?)");
			preparedStatement.setString(1, lastName);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, login);
			preparedStatement.setInt(4, customerId);
			result = preparedStatement.executeUpdate();
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean addUser(String lastName, String firstName, String login, String password, Integer profileId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("INSERT INTO users(user_last_name, user_first_name, user_login, user_password, profile_id) values(?, ?, ?, ?, ?)");
		preparedStatement.setString(1, lastName);
		preparedStatement.setString(2, firstName);
		preparedStatement.setString(3, login);
		preparedStatement.setString(4, password);
		preparedStatement.setInt(5, profileId);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean updateUserEmployeeOrClient(Integer id, String lastName, String firstName, String login, String password, Integer profileId, Integer customerId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		User user = findUserByUserId(id);
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE users set user_last_name=?, user_first_name=?, user_login=?, user_password=?, profile_id=?, customer_id=? where user_id=?");
		preparedStatement.setString(1, lastName);
		preparedStatement.setString(2, firstName);
		preparedStatement.setString(3, login);
		preparedStatement.setString(4, password);
		preparedStatement.setInt(5, profileId);
		preparedStatement.setInt(6, customerId);
		preparedStatement.setInt(7, id);
		int result = preparedStatement.executeUpdate();
		
		if(result > 0) {
			preparedStatement = connection.prepareStatement("UPDATE employees set employee_last_name=?, employee_first_name=?, employee_email=? where employee_email=?");
			preparedStatement.setString(1, lastName);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, login);
			preparedStatement.setString(4, user.getUserLogin());
			result = preparedStatement.executeUpdate();
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean updateUser(Integer id, String lastName, String firstName, String login, String password, Integer profileId) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE users set user_last_name=?, user_first_name=?, user_login=?, user_password=?, profile_id=? where user_id=?");
		preparedStatement.setString(1, lastName);
		preparedStatement.setString(2, firstName);
		preparedStatement.setString(3, login);
		preparedStatement.setString(4, password);
		preparedStatement.setInt(5, profileId);
		preparedStatement.setInt(6, id);
		int result = preparedStatement.executeUpdate(); 
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean deleteUserEmployeeOrClient(Integer id) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		User user = findUserByUserId(id);
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("DELETE FROM users where user_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		
		if(result > 0) {
			preparedStatement = connection.prepareStatement("DELETE FROM employees where employee_email=?");
			preparedStatement.setString(1, user.getUserLogin());
			result = preparedStatement.executeUpdate();
		}
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
	
	public Boolean deleteUser(Integer id) throws SQLException, ClassNotFoundException {
		PreparedStatement preparedStatement = null;
		
		Connexion connexion = Connexion.getInstance();
		Connection connection = connexion.getConnection();
		preparedStatement = connection.prepareStatement("DELETE FROM users where user_id=?");
		preparedStatement.setInt(1, id);
		int result = preparedStatement.executeUpdate();
		
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		
		return Boolean.valueOf(result>0);
	}
}
