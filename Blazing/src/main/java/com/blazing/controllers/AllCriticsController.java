package com.blazing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blazing.objects.CriticApplication;

@Controller
@RequestMapping(path="/critics")
public class AllCriticsController {

	@RequestMapping(method=RequestMethod.GET)
	public String getCritics()
	{
		return "critics-home";
	}
	
	@RequestMapping(path="apply",method=RequestMethod.POST)
	@ResponseBody
	public boolean submitApplication(@RequestBody CriticApplication application)
	{
		return true;
	}
	
}
