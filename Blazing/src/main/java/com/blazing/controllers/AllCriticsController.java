package com.blazing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/critics")
public class AllCriticsController {

	@RequestMapping(method=RequestMethod.GET)
	public String getCritics()
	{
		return "critics-home";
	}
}
