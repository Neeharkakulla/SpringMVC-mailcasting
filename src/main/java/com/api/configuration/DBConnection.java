package com.api.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	

public static Connection getCon(){
	Connection con=null;
	try {
		Class.forName(DBProperties.DB_DRIVER);
		con=DriverManager.getConnection(DBProperties.DB_URL,
				DBProperties.DB_USERNAME,
				DBProperties.DB_PASSWORD);
		
	}
	catch (ClassNotFoundException e) {
		e.printStackTrace();
		
	} 
	catch (SQLException e) {
		e.printStackTrace();
	}
	
	return con;
	
	}
}
