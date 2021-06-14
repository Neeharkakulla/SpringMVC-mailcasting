<%@ page language="java" import="java.util.*,com.api.service.*" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<jsp:include page="header.jsp"></jsp:include>

<%
String id=request.getParameter("id");

if(id.length()>0){
try{

out.print("<br>");
int res=BinService.addInboxMailtoBin(Integer.parseInt(id));
if(res>0)
	out.print("Mail has been successfully deleted");
else
	out.print("There is an error in deleting your mail");
}catch(Exception e){e.printStackTrace();}
}
%>
		