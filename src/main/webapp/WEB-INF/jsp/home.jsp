<%@page import="java.sql.*" %>

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
<body>
<div class="main">
			<div class="main-col-1">
				
					<div>
							<h2>Welcome to Mailservices!</h2>		
					</div>
					
							<div>
									<% 	
									if(session.getAttribute("username")!=null){
									String username=(String)session.getAttribute("username");		
									out.print("<font style='color:navy'>Welcome "+username+"</font>");
									}
									else{
									request.setAttribute("Error1","Plz Do login First");
									%>
									<jsp:forward page="index.jsp"></jsp:forward>
										<%}
									%>
							</div>
							<jsp:include page="inbox.jsp"></jsp:include>
			</div>
			<div  class="main-col-2">
				<table>
					<tr>
						<td style="width: 25%;"></td>
						<td style="width: 65%;">
							<div id="viewInBoxMail">
			
							</div>
						</td>
						<td style="width: 10%;"></td>
					</tr>
					
				</table>
				
			</div>
		
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

		function viewMail(id)
		{
			$.ajax({url: "GetInBoxMail.jsp?id="+id, success: function(result){
				
				document.getElementById("viewInBoxMail").innerHTML=result
    	}
	});
}




</script>
	