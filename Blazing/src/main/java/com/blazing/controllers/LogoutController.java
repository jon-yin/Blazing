package com.blazing.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/logout")
public class LogoutController {

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session)
	{
		session.setAttribute("currentUser", null);
		return "home";
	}
	
	
}
