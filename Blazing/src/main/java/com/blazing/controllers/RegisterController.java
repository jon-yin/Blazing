package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blazing.objects.RegisterInfo;
import com.blazing.services.LoginRegisterService;

@Scope("session")
@RestController
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private LoginRegisterService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public boolean registerUser(@RequestBody RegisterInfo info)
	{
		boolean statusCode = service.registerUser(info);
		return statusCode;
	}
	
}
