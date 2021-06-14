package com.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api.configuration.DBConnection;
public class VerifyLogin {

public  boolean checkLogin(String email,String password){
	boolean status=false;
	Connection con=DBConnection.getCon();
	try {
		PreparedStatement ps=con.prepareStatement("Select * from MAILCASTINGUSER where email = ? and password =?");
		
		ps.setString(1,email);
		ps.setString(2,password);
		ResultSet rs=ps.executeQuery();
		status=rs.next();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return status;
}

public  boolean validatePassword(int id, String password) {
	Connection con=DBConnection.getCon();
	try {
		PreparedStatement ps=con.prepareStatement("Select * from MAILCASTINGUSER where id = ? and password =?");
		ps.setInt(1, id);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
			return true;
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}

public  boolean changePassword(int id, String password) {
	Connection con=DBConnection.getCon();
	try {
		PreparedStatement ps=con.prepareStatement("UPDATE MAILCASTINGUSER SET password=? WHERE id =?");
		
		ps.setString(1, password);
		ps.setInt(2, id);
		int res=ps.executeUpdate();
		if(res>0)
			return true;
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}
}
