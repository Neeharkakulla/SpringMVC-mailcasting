package com.api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.api.configuration.DBConnection;
import com.api.model.BinModel;
import com.api.model.InBoxModel;
import com.api.model.SentBoxModel;

public class BinService {
	
	public static int deleteByBinId(int id) {
		try{
			Connection con=DBConnection.getCon();
			PreparedStatement ps=con.prepareStatement("delete  from BIN where id=?");
			
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	public static BinModel getMailById(int id) {
		BinModel mail=null;
		try {
			
			Connection con=DBConnection.getCon();
			PreparedStatement ps = con.prepareStatement("Select *  from bin where id=?");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) 
				mail=new BinModel(rs.getInt(1),rs.getString(2)
						,rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6)
						,rs.getString(7),rs.getTimestamp(8),rs.getString(9));
			
			
			
			return mail;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return mail;

	}
	
	public static List<BinModel> getBinMailsByMailId(String email){
		try {
			
			List<BinModel> list=new ArrayList<>();
			Connection con=DBConnection.getCon();
			PreparedStatement ps=con.prepareStatement("select * from bin where usermail=?");
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				list.add(new BinModel(rs.getInt(1),rs.getString(2)
						,rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6)
						,rs.getString(7),rs.getTimestamp(8),rs.getString(9)));
			}
			
			return list.stream().sorted((m1,m2)->-m1.getDate().compareTo(m2.getDate()))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public static int addInboxMailtoBin(int mailid) {
		
		try {
			Connection con=DBConnection.getCon();
			PreparedStatement ps=con.
					prepareStatement
					("insert into bin(type,mailid,usermail,reciever,sender,message,date,subject)"
							+ " values(?,?,?,?,?,?,?,?)");
			
				InBoxModel mail=InBoxService.getMailById(mailid);
				ps.setString(1, "inbox");
				ps.setInt(2, mailid);
				ps.setString(3, mail.getReciever());
				ps.setString(4, mail.getReciever());
				ps.setString(5, mail.getSender());
				ps.setString(6, mail.getMessage());
				ps.setTimestamp(7, mail.getDate());
				ps.setString(8, mail.getSubject());
				InBoxService.deleteById(mailid);
				
			
			return ps.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}
	public static int addSentBoxMailtoBin(int mailid) {
		
		try {
			Connection con=DBConnection.getCon();
			PreparedStatement ps=con.
					prepareStatement
					("insert into bin(type,mailid,usermail,reciever,sender,message,date,subject)"
							+ " values(?,?,?,?,?,?,?,?)");
			
			SentBoxModel mail=SentBoxService.getMailById(mailid);
			
			ps.setString(1, "sentbox");
			ps.setInt(2, mailid);
			ps.setString(3, mail.getSender());
			ps.setString(4, mail.getReciever());
			ps.setString(5, mail.getSender());
			ps.setString(6, mail.getMessage());
			ps.setTimestamp(7, mail.getDate());
			ps.setString(8, mail.getSubject());
			SentBoxService.deleteById(mailid);
			return ps.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static String retriveFromBin(int id) {
		
		BinModel mail=BinService.getMailById(id);
		if(mail.getType().equalsIgnoreCase("inbox")) {
		InBoxService.retriveMail(new InBoxModel(mail.getReciever(),mail.getSender()
				,mail.getMessage(),mail.getDate(),mail.getSubject()));
		}
		else if(mail.getType().equalsIgnoreCase("sentbox")) {
			SentBoxService.retriveMail(new SentBoxModel(mail.getReciever(),mail.getSender()
					,mail.getMessage(),mail.getDate(),mail.getSubject()));
			}
		BinService.deleteByBinId(id);
		return mail.getType();
	}
}
