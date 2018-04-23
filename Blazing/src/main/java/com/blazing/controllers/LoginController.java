package com.blazing.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blazing.objects.LoginInfo;
import com.blazing.services.LoginRegisterService;

@RestController
@RequestMapping(path="/login")

public class LoginController {
	
	@Autowired
	private LoginRegisterService service;
	
	
	@RequestMapping(method=RequestMethod.POST)
	public boolean login (@RequestBody LoginInfo info, HttpSession session)
	{
		boolean statusCode = service.loginUser(info, session);
		return statusCode;
	}
	
	
}
