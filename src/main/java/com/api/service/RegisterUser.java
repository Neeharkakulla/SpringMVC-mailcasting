package com.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api.configuration.DBConnection;
import com.api.model.UserModel;
public class RegisterUser {
static int status=0;
public static int register(UserModel user){
	

	
	try {
		Connection con=DBConnection.getCon();
		PreparedStatement ps;
		
		String ps1 = "Insert into MAILCASTINGUSER(email,password,name,gender,contact,country) VALUES (?, ?, ?, ?, ?, ?)";
		ps = con.prepareStatement(ps1);
		ps.setString(1,user.getEmail());
		ps.setString(2,user.getPassword());
		ps.setString(3,user.getName());
		ps.setString(4,user.getGender());
		ps.setString(5,user.getContact());
		ps.setString(6,user.getCountry());
		
		status=ps.executeUpdate();

		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	return status;
	
}
public static UserModel getUserByEmail(String email) {
	
	try {
		Connection con=DBConnection.getCon();
		PreparedStatement ps=con.prepareStatement("select * from MAILCASTINGUSER where email=?");
		ps.setString(1, email);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) 
			return new UserModel(rs.getInt(1),rs.getString(2),
					"",rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}	
	
	return null;
}
}
