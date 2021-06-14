package com.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.api.configuration.DBConnection;
import com.api.model.SentBoxModel;

public class SentBoxService {
	
	public  int deleteById(int id) {
		try{
			Connection con=DBConnection.getCon();
			PreparedStatement ps=con.prepareStatement("delete  from SENTBOX where id=?");
			
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
		}
	
	public  List<SentBoxModel> getAllMailsByEmail(String email){
		List<SentBoxModel> list=new ArrayList<>();
		try {
			Connection con= DBConnection.getCon();
			PreparedStatement ps=con.prepareStatement("Select * from sentbox where sender=?");
			ps.setString(1,email);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				
				list.add(new SentBoxModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5),rs.getString(6)));
				
				
			}
			
			return list.stream().
					sorted((m1,m2)->-(m1.getDate()).compareTo((m2.getDate())))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public  SentBoxModel getMailById(int id) {
		SentBoxModel mail=null;
		try {
			Connection con=DBConnection.getCon();
			PreparedStatement ps;
			
			String ps1 = "Select *  from sentbox where id=?";
			ps = con.prepareStatement(ps1);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) 
				mail=new SentBoxModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5),rs.getString(6));
			
			
			return mail;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return mail;
		
	}

	public  void retriveMail(SentBoxModel mail) {
		try {
			Connection con=DBConnection.getCon();
			PreparedStatement ps=con.prepareStatement("Insert into SENTBOX(reciever,sender,message,date,subject) values(?,?,?,?,?)");
			ps.setString(1,mail.getReciever());
			ps.setString(2,mail.getSender());
			ps.setString(3,mail.getMessage());
			ps.setTimestamp(4,mail.getDate());
			ps.setString(5, mail.getSubject());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
	}


}
