<%@page import="java.sql.*,com.api.model.*,com.api.service.*,java.util.*,java.text.*" %>			
<div>
								
<%
			if(session.getAttribute("username")!=null){
			String username=(String)session.getAttribute("username");		
			out.print("<h5>Inbox</h5>");
			if(request.getAttribute("delete")!=null){
			String delete=(String)session.getAttribute("username");		
			out.print("<font style='color:navy'>"+delete+"</font>");
			}
			try {
				List<InBoxModel> mails=InBoxService.getAllMailsByEmail(username);
				if(mails.size()>0){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				out.print("<div id='bottom'></div>");

				out.print("<table border=4 cellspacing='4' cellpadding='5'>");
				out.print("<tr><td>SENDER 	&nbsp;	&nbsp;</td><td>SUBJECT</td><th>MESSAGE	&nbsp; 	&nbsp;</td><td>DATE OF RECIEVING 	&nbsp;	&nbsp;</td><td>Delete</td></tr>");
				
				for(InBoxModel m:mails){
				int id=m.getId();
				String date=formatter.format(m.getDate());
				out.print("<tr onclick='viewMail("+id+")'>");
			if(m.getSender().length()>=5)
			out.print("<td>" + m.getSender().substring(0,5) + "....</td>");
				else
			out.print("<td>" + m.getSender() + "</td>");
				
				if(m.getSubject().length()>=5)
			out.print("<td>" + m.getSubject().substring(0,5) + "....</td>");
				else
			out.print("<td>" + m.getSubject() + "</td>");

				if(m.getMessage().length()>=5)
			out.print("<td>" + m.getMessage().substring(0,5) + "......</td>");
				else
			out.print("<td>" + m.getMessage() + "</td>");
				
				
				out.print("<td>" + date + "</td>");
				
				out.print("<td><a href='deleteInboxMail.jsp?id="+m.getId() + "'> Delete</a></td>");

						out.print("</tr>");

					}
					out.print("</table>");
					out.print("<table align='right'width='40%'>");

					out.print("</table>");
				}
				else{
					out.print("Inbox is empty");
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				request.setAttribute("Error1", "Plz Do login First");
		%>
	<jsp:forward page="index.jsp"></jsp:forward>
		<%
		
		}
	%>
		
								
</div>
									
				
			
		
	