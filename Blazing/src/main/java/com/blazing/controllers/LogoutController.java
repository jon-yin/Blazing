package com.blazing.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/logout")
public class LogoutController {

	public String logout(HttpSession session)
	{
		session.setAttribute("currentUser", null);
		return "home";
	}
	
	
}
