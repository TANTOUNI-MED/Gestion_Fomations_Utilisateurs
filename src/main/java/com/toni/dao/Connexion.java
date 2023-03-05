package com.toni.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	private static Connexion INSTANCE;
	private static Connection CONNECTION;
	
	private Connexion() {
		
	}
	
	public static Connexion getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Connexion();
		}
		return INSTANCE;
	}
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		if(CONNECTION == null) {
			Class.forName("com.mysql.jdbc.Driver");
			CONNECTION = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "");
		}
		return CONNECTION;
	}
	
	public void closeConnection() throws SQLException {
		if(CONNECTION == null) {
			CONNECTION.close();
		}
	}
}
