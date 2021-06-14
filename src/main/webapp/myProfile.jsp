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
		width: 30%;
		
		height: 100vh;
	}
	.main-col-2{
		width: 70%;
		display: inline-block;
		height: 100vh;
		
	}
	.main-col-2 h5{
	margin-left:12%;
	}
	
</style>
<div class="main">
	<div class="main-col-1"></div>	
	<div class="main-col-2">
	<br><br><br>						
<%
			if(session.getAttribute("username")!=null){
			String username=(String)session.getAttribute("username");		
			out.print("<h5>My Profile</h5>");
			if(request.getAttribute("delete")!=null){
			String delete=(String)session.getAttribute("username");		
			out.print("<font style='color:navy'>"+delete+"</font>");
			}
			try {
				
				UserModel user=RegisterUser.getUserByEmail(username);
				String success=(String)request.getAttribute("success");	
				
				
				out.print("<table border=4 cellspacing='4' cellpadding='5'>");
				
				
				
				out.print("<tr>");
				out.print("<td>Name</td>");
				out.print("<td>" +user.getName()+ "</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td>Email</td>");
				out.print("<td>" + user.getEmail() + "</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td>Password</td>");
				
				if((!(success==null))&&(success.equals("success")))
					out.print("<td><span style='color: green;'>Success &#10003;</span></td>");
				else if((!(success==null))&&(success.equals("Invalid")))
					out.print("<td> <form action='validate' method='post'>"+
							"<input type='hidden' name='id' value="+user.getId()+" readonly>"+
							"<input type='password' name='password'>&nbsp;&nbsp;"+
								"<button >Validate</button></form>"
									+"<span style='color: red;'>Invalid</span></td>");
				else if(success==null)
					out.print("<td> <form action='validate' method='post'>"+
				"<input type='hidden' name='id' value="+user.getId()+" readonly>"+
				"<input type='password' name='password'>&nbsp;&nbsp;"+
					"<button >Validate</button></form></td>");
				out.print("</tr>");
				if((!(success==null))&&(success.equals("success"))){
				out.print("<tr>");
				out.print("<td>New Password</td>");
				out.print("<td> <form action='newPasswordRequest' method='post'>"+
						"<input type='hidden' name='id' value="+user.getId()+" readonly>"+
						"<input type='password' name='password'>&nbsp;&nbsp;"+
							"<button >Change</button></form></td>");
				out.print("</tr>");
				}
				out.print("<tr>");
				out.print("<td>Contact</td>");
				out.print("<td>" + user.getContact() + "</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td>Country</td>");
				out.print("<td>" + user.getCountry() + "</td>");
				out.print("</tr>");
				
				out.print("</table>");
				
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
</div>