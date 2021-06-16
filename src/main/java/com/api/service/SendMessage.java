package com.api.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.api.configuration.DBConnection;
import com.api.model.SentBoxModel;


@Service
public class SendMessage {
 int status=0;

public  int sendMsg(SentBoxModel mail){
	Connection con=DBConnection.getCon();
	java.util.Date sqdate=Calendar.getInstance().getTime();
	java.sql.Timestamp sqlTime=new java.sql.Timestamp(sqdate.getTime());
	try {
		PreparedStatement ps=con.prepareStatement("Insert into INBOX(reciever,sender,message,date,subject) values(?,?,?,?,?)");
		
		ps.setString(1,mail.getReciever());
		ps.setString(2,mail.getSender());
		ps.setString(3,mail.getMessage());
		ps.setTimestamp(4,sqlTime);
		ps.setString(5, mail.getSubject());
		
		PreparedStatement ps1=con.prepareStatement("Insert into SENTBOX(reciever,sender,message,date,subject) values(?,?,?,?,?)");
		ps1.setString(1,mail.getReciever());
		ps1.setString(2,mail.getSender());
		ps1.setString(3,mail.getMessage());
		ps1.setTimestamp(4,sqlTime);
		ps1.setString(5, mail.getSubject());
		
		
		status=ps.executeUpdate();
		ps1.executeUpdate();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return status;
}
}
