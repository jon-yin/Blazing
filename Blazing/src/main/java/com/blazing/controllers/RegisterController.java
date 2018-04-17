package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.blazing.objects.RegisterInfo;
import com.blazing.services.LoginRegisterService;

@SessionAttributes("currentUser")
@RestController
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private LoginRegisterService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public boolean registerUser(@RequestBody RegisterInfo info, Model model)
	{
		System.out.println("Hello World");
		boolean statusCode = service.registerUser(info, model);
		return statusCode;
	}
	
}
