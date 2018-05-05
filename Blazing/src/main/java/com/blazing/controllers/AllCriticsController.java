package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blazing.objects.CriticApplication;
import com.blazing.services.UserService;

@Controller
@RequestMapping(path="/critics")
public class AllCriticsController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCritics()
	{
		return "critics-home";
	}
	
	@RequestMapping(path="apply",method=RequestMethod.POST)
	@ResponseBody
	public boolean submitApplication(@RequestBody CriticApplication application)
	{
		userService.addApplication(application);
		return true;
	}
	
}
