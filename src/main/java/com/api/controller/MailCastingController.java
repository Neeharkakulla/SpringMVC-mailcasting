package com.api.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.api.model.UserModel;
import com.api.service.BinService;
import com.api.service.InBoxService;
import com.api.service.RegisterUser;
import com.api.service.SendMessage;
import com.api.service.SentBoxService;
import com.api.service.VerifyLogin;

@ComponentScan(basePackages = {"com.api.services"})
@Controller
public class MailCastingController {
	
	@Autowired
	BinService binService;
	@Autowired
	InBoxService inboxService;
	@Autowired
	SentBoxService sentboxService;
	@Autowired
	RegisterUser registerService;
	@Autowired
	VerifyLogin loginService;
	@Autowired
	SendMessage messageService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView showIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("index");
	}
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView showHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/sent",method=RequestMethod.GET)
	public ModelAndView showSentBox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("sent");
	}
	
	@RequestMapping(value="/compose",method=RequestMethod.GET)
	public ModelAndView showCompose(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("compose");
	}
	
	@RequestMapping(value="/contactus",method=RequestMethod.GET)
	public ModelAndView showContactUs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("contactus");
	}
	@RequestMapping(value="/myProfile",method=RequestMethod.GET)
	public ModelAndView showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("myProfile");
	}
	@RequestMapping(value="/bin",method=RequestMethod.GET)
	public ModelAndView showBin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("bin");
	}
	
	@RequestMapping(value="/deleteSentboxMail",method=RequestMethod.GET)
	public ModelAndView deleteSentboxMail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		binService.addSentBoxMailtoBin(Integer.parseInt(id));
		return new ModelAndView("sent");
	}
	@RequestMapping(value="/deleteBinboxMail",method=RequestMethod.GET)
	public ModelAndView deleteBinBoxMail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		binService.deleteByBinId(Integer.parseInt(id));
		return new ModelAndView("bin");
	}
	@RequestMapping(value="/deleteInboxMail",method=RequestMethod.GET)
	public ModelAndView deleteinboxMail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");

		binService.addInboxMailtoBin(Integer.parseInt(id));
		return new ModelAndView("home");
	}
	

	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ModelAndView  login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String uname=request.getParameter("email");
		String password=request.getParameter("password");
		
		boolean status=loginService.checkLogin(uname,password);
		if(status==true){
			HttpSession session=request.getSession();
			session.setAttribute("username",uname);
			return new ModelAndView("home");
		}
		else{
			String Error="Please check your Email and Password";
			request.setAttribute("Error", Error);
			return new ModelAndView("index");					
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	private ModelAndView logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession session=request.getSession(false);
		session.invalidate();
		request.setAttribute("logout","You have been sucessfully logged out");
		return new ModelAndView("index");
		
	}
	
	@RequestMapping(value="/composeEmail",method=RequestMethod.POST)
	private ModelAndView composeEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession session=request.getSession(false);
		
		
		String sender=(String)session.getAttribute("username");
		
		
		String reciever=request.getParameter("reciever_id");
		String msg=request.getParameter("message");
		String sub=request.getParameter("subject");
		int i=messageService.sendMsg(sender,reciever,sub,msg);
		
		if(i>0)
			return new ModelAndView("home");
		else
			return new ModelAndView("compose");
		
		}
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	private ModelAndView validatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		
		String password=request.getParameter("password");
	
	
		if(loginService.validatePassword(id,password)) 
			request.setAttribute("success", "success");
		else 
			request.setAttribute("success", "Invalid");

		
		return new ModelAndView("myProfile");
		
		
	}
	@RequestMapping(value="/newPasswordRequest",method=RequestMethod.POST)
	private ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		String password=request.getParameter("password");
		if(loginService.changePassword(id,password)) {
			request.setAttribute("newPassword", true);
			request.setAttribute("sucsess", null);	
		}
		
		return new ModelAndView("myProfile");
	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	private ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String name=request.getParameter("name");
		String gender=request.getParameter("gender");
		
		String contact=request.getParameter("contact");
		
		String country=request.getParameter("country");
		
		UserModel user=new UserModel(email, password, name, gender, contact, country);
		
		if(registerService.register(user)>0){
			String register= "You are Successfully registered";
			request.setAttribute("register",register);
			return new ModelAndView("index");
		}
		else
		{
			String registererror="Sorry,Registration failed. please try later";
			request.setAttribute("registererror",registererror);
			return new ModelAndView("Register");
		}
	}
	@RequestMapping(value="/retriveMail",method=RequestMethod.GET)
	private ModelAndView retriveFromBin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			int id=Integer.parseInt(request.getParameter("id"));
			
			String type=binService.retriveFromBin(id);
			
			if(type.equalsIgnoreCase("inbox")) 
				return new ModelAndView("home");
			
			if(type.equalsIgnoreCase("sentbox")) 
				return new ModelAndView("sent");
				
		
			return new ModelAndView("index");
		
	}
	
		
}

