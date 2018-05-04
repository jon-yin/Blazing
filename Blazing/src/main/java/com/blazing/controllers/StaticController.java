package com.blazing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StaticController {

	@RequestMapping("about")
	public String aboutPage()
	{
		return "about";
	}
	
	@RequestMapping("tos")
	public String termsOfService()
	{
		return "tos";
	}
	
	@RequestMapping("help")
	public String help()
	{
		return "help";
	}
	
	@RequestMapping("contactus")
	public String contactUs()
	{
		return "contact-us";
	}
	
}
