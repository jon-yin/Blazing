package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.blazing.objects.LoginInfo;
import com.blazing.services.LoginRegisterService;

@RestController
@RequestMapping(path="/login")
@SessionAttributes("currentUser")
public class LoginController {
	
	@Autowired
	private LoginRegisterService service;
	
	
	@RequestMapping(method=RequestMethod.POST)
	public boolean login (@RequestBody LoginInfo info, Model model)
	{
		boolean statusCode = service.loginUser(info, model);
		return statusCode;
	}
}
