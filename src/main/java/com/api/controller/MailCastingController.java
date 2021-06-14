package com.api.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.api.model.UserModel;
import com.api.service.BinService;
import com.api.service.RegisterUser;
import com.api.service.SendMessage;
import com.api.service.VerifyLogin;


@Controller
public class MailCastingController {
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public void  hello(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String uname=request.getParameter("email");
		String password=request.getParameter("password");
		boolean status=VerifyLogin.checkLogin(uname,password);
		if(status==true){
			HttpSession session=request.getSession();
			session.setAttribute("username",uname);
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			rd.include(request, response);

		}
		else{
			String Error="Please check your EMail and Password";
			request.setAttribute("Error", Error);
			
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.include(request, response);
			
		
		}

	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession session=request.getSession(false);
		session.invalidate();
		request.setAttribute("logout","You have been sucessfully logged out");
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request,response);
		
	}
	@RequestMapping(value="/composeEmail",method=RequestMethod.POST)
	private void composeEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession session=request.getSession(false);
		
		
		String sender=(String)session.getAttribute("username");
		
		
		String reciever=request.getParameter("reciever_id");
		String msg=request.getParameter("message");
		String sub=request.getParameter("subject");
		int i=SendMessage.sendMsg(sender,reciever,sub,msg);
		if(i>0){
			
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			rd.include(request, response);
		}
		else{
			
			RequestDispatcher rd=request.getRequestDispatcher("compose.jsp");
			rd.include(request, response);
		}
		
		}
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	private void validatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		
		String password=request.getParameter("password");
	
	
		if(VerifyLogin.validatePassword(id,password)) {
			request.setAttribute("success", "success");
			
			RequestDispatcher rd=request.getRequestDispatcher("myProfile.jsp");
			rd.include(request, response);
		}
		else {
			request.setAttribute("success", "Invalid");
			RequestDispatcher rd=request.getRequestDispatcher("myProfile.jsp");
			rd.include(request, response);
		}
		
		
	}
	@RequestMapping(value="/newPasswordRequest",method=RequestMethod.POST)
	private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		String password=request.getParameter("password");
		if(VerifyLogin.changePassword(id,password)) {
			request.setAttribute("newPassword", true);
			request.setAttribute("sucsess", null);
			RequestDispatcher rd=request.getRequestDispatcher("myProfile.jsp");
			rd.include(request, response);
			
		}
		
	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String name=request.getParameter("name");
		String gender=request.getParameter("gender");
		
		String contact=request.getParameter("contact");
		
		String country=request.getParameter("country");
		
		UserModel user=new UserModel(email, password, name, gender, contact, country);
		
		int status=RegisterUser.register(user);
		if(status>0){
			String register= "You are Successfully registered";
			request.setAttribute("register",register);
			RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
			rd.include(request, response);
		}
		else{
			String registererror="Sorry,Registration failed. please try later";
			request.setAttribute("registererror",registererror);
			RequestDispatcher rd=request.getRequestDispatcher("Register.jsp");
			rd.include(request, response);
		
		
	}
	}
	@RequestMapping(value="/retriveMail",method=RequestMethod.GET)
	private void retriveFromBin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
			String type=BinService.retriveFromBin(id);
			if(type.equalsIgnoreCase("inbox")) {
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			rd.forward(request, response);
			}
			if(type.equalsIgnoreCase("sentbox")) {
				RequestDispatcher rd=request.getRequestDispatcher("sent.jsp");
				rd.forward(request, response);
				}
		
		
		
	}
	
		
}

