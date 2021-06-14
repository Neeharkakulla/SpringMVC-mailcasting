<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
 <%@ page import="java.sql.*,com.api.model.*,com.api.service.*,java.text.*,java.util.*"%>  
<jsp:include page="header.jsp"></jsp:include>
	
	
	<style>
	.main{
		width: 100vw;
		display: flex;
	}
	.main-col-1{
		display: inline-block;
		width: 40%;
		border:1px solid;
		height: 100vh;
	}
	.main-col-2{
		width: 60%;
		display: inline-block;
		
		border: 1px solid;
		height: 100vh;
		
	}
	
</style>
<div class="main">
			<div class="main-col-1">
				
					<div>
							<h2>Welcome to Mailservices!</h2>		
					</div>
					
							<div>
									<% 	
									if(session.getAttribute("username")!=null){
									String username=(String)session.getAttribute("username");		
									out.print("");
									}
									else{
									request.setAttribute("Error1","Plz Do login First");
									%>
									<jsp:forward page="index.jsp"></jsp:forward>
										<%}
									%>
							</div>
							<div>
									
								<% 
								
							if(session.getAttribute("username")!=null){
							String username=(String)session.getAttribute("username");		
							out.print("<font style='color:navy'>Welcome "+username+"</font><br><h5>Bin Box</h5>");
							if(request.getAttribute("delete")!=null){
							String delete=(String)session.getAttribute("username");		
							out.print("<font style='color:navy'>"+delete+"</font>");
							}
							try {
							
						
			List<BinModel> mails=(List<BinModel>)request.getAttribute("binmails");
			if(mails.size()>0){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			out.print("<div id='bottom'></div>");
		
		out.print("<table border=4 cellspacing='4' cellpadding='5'>");
		out.print("<tr><td>Reciever&nbsp;	&nbsp;</td><td>SUBJECT</td><th>MESSAGE	&nbsp; 	&nbsp;</td><td>DATE OF RECIEVING 	&nbsp;	&nbsp;</td><td>Permanent<br/>Delete</td><td>Retrive</td></tr>");
		for(BinModel m:mails){
			int id=m.getId();
			
			String date=formatter.format(m.getDate());
			out.print("<tr onclick='viewBinMail("+id+")'>");

			if(m.getReciever().length()>=5)
				out.print("<td>" + m.getReciever().substring(0,5) + "....</td>");
			else
				out.print("<td>" +m.getReciever() + "</td>");
			
			if(m.getSubject().length()>=5)
				out.print("<td>" + m.getSubject().substring(0,5) + "....</td>");
			else
				out.print("<td>" + m.getSubject() + "</td>");

			if(m.getMessage().length()>=5)
				out.print("<td>" + m.getMessage().substring(0,5) + "......</td>");
			else
				out.print("<td>" + m.getMessage()+ "</td>");
			
			out.print("<td>" +date + "</td>");
			
			out.print("<td><a href='deleteBinboxMail?id="+m.getId() + "'> Delete</a></td>");
			out.print("<td><a href='retriveMail?id="+m.getId() + "'> Retrive</a></td>");
			out.print("</tr>");
		
		}
		out.print("</table>");
		out.print("<table align='right'width='40%'>");
		out.print("</table>");
			}
		else
			{
				out.print("Bin is empty");
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
							
	else{
	request.setAttribute("Error1","Plz Do login First");
	%>
	<jsp:forward page="index.jsp"></jsp:forward>
		<%
		
		}
	%>
	
							
</div>
		</div>
			<div  class="main-col-2">
				<table>
					<tr>
						<td style="width: 25%;"></td>
						<td style="width: 65%;">
							<div id="viewBinBoxMail">
			
							</div>
						</td>
						<td style="width: 10%;"></td>
					</tr>
					
				</table>
				
			</div>
		
			
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

		function viewBinMail(id)
		{
			
			$.ajax({url: "GetBinMail.jsp?id="+id, success: function(result){
				
				document.getElementById("viewBinBoxMail").innerHTML=result
    	}
	});
}




</script>				

			
	
