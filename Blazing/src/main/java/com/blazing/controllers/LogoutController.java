package com.blazing.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/logout")
public class LogoutController {

	@RequestMapping(method=RequestMethod.POST)
	public void logout(HttpSession session)
	{
		session.setAttribute("currentUser", null);
	}
	
	
}
