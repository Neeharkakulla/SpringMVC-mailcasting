package com.api.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import com.api.configuration.DBConnection;
public class SendMessage {
static int status=0;

public static int sendMsg(String sender,String reciever,String sub,String msg ){
	Connection con=DBConnection.getCon();
	java.util.Date sqdate=Calendar.getInstance().getTime();
	java.sql.Timestamp sqlTime=new java.sql.Timestamp(sqdate.getTime());
	try {
		PreparedStatement ps=con.prepareStatement("Insert into INBOX(reciever,sender,message,date,subject) values(?,?,?,?,?)");
		
		ps.setString(1,reciever);
		ps.setString(2,sender);
		ps.setString(3,msg);
		ps.setTimestamp(4,sqlTime);
		ps.setString(5, sub);
		
		PreparedStatement ps1=con.prepareStatement("Insert into SENTBOX(reciever,sender,message,date,subject) values(?,?,?,?,?)");
		ps1.setString(1,reciever);
		ps1.setString(2,sender);
		ps1.setString(3,msg);
		ps1.setTimestamp(4,sqlTime);
		ps1.setString(5, sub);
		
		
		status=ps.executeUpdate();
		ps1.executeUpdate();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return status;
}
}
