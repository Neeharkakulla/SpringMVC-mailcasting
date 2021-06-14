<jsp:include page="header.jsp"></jsp:include>
		<style>
			.main{
				display: flex;
				width: 100vw;
			}
			.main-col-1{
				display: inline-block;
				width: 40%;
			}
			.main-col-2{
				display: inline-block;
				width: 60%;
				margin-left: 5vw;
				
			}
			
			.btn-send{
				background-color: transparent;
				border:  solid green;
				border-radius: 8px;
				height: 35px;
				
			}
			.btn-send:hover{
				background-color: green;
				color: white;
			}
			.cancel{
				background-color: transparent;
				color: red;
			}
			.cancel:hover{
				background-color: red;
				color: white;
			}
			.inputBox{
				width: 43vw;
				height: 40px;
				border: 1px solid;
				border-radius: 5px;
			}
		</style>
<div class="main">
	<div class="main-col-1">
		<h2>Welcome to Mailservices!</h2>
							<p>Here we are providing a free <b>Mail Service</b> for all the employees<br>
							you can now just login into your account and can send data to other<br> 
							employees for free! Let's Get Started!
						</p>
	</div>
	<div class="main-col-2">
		<div>
		</div>
		<h4>Compose Message</h4> <form action="composeEmail" method="post" >
			<table>
	
			<% 
			
		if(session.getAttribute("username")!=null){
		String username=(String)session.getAttribute("username");		
		out.print("<font style='color:navy'>Welcome "+username+"</font>");
		out.print("<tr><td>From : <input type='text' style='border: none;' name="+username+" value="+username+" readonly/><br/></td></tr>");
		}
		else{
		request.setAttribute("Error1","Plz Do login First");
		%>
		<jsp:forward page="index.jsp"></jsp:forward>
			<%}
		%>
		<% 
		if(request.getAttribute("compose")!=null){
		String compose= (String)request.getAttribute("compose");
		out.print("<font style='color:navy'>"+compose+"</font>");
		}
		if(request.getAttribute("composeerr")!=null){
		String composeerr= (String)request.getAttribute("composeerr");
		out.print("<font style='color:navy'>"+composeerr+"</font>");
		}
		%>
		
	 <tr><td>To&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <input type="text" name="reciever_id"/><br/></td></tr>
	 <tr><td>Subject:<br/></td></tr>
	 <tr><td><input type="text" name="subject" class="inputBox"></td></tr>
	 <tr><td>Message:<br/></td></tr>
	 
	 <tr><td><textarea rows="10" cols="80" name="message"></textarea><br/></td></tr>
	 <tr><td><input class="btn-send"type="submit" value="Send"/>&nbsp;&nbsp;&nbsp;<a class="cancel" href="home.jsp">cancel</a></td></tr>
	 
	 </table></form>
						
										
	</div>
</div>