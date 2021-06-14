		<%@ page import="java.sql.*,com.api.model.*,com.api.service.*,java.text.*"%>  
			
			
		<%  
		if(request.getAttribute("binmail")!=null){
		BinModel mail=(BinModel)request.getAttribute("binmail");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date=formatter.format(mail.getDate());
		 
		out.print("<br><br><br><br>");
		out.print("<h3>"+mail.getSubject()+"</h3>");
		out.print("<p>from :"+mail.getSender()+"</p>");
		out.print("<p>to :"+mail.getReciever()+"</p>");
		out.print("<p>Date :"+date+"</p>");
		out.print("<p style='white-space: pre-line' >messsage <br><br>"+mail.getMessage()+"</p>");
		
		}
		else
			out.print("Error in fetching");
		
		  
		}catch(Exception e){e.printStackTrace();{}  
		
		%>  
