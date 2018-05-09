package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blazing.services.BrowseService;

@Controller
@RequestMapping(path={"/","/home","/index"})
public class IndexController {
	
	@Autowired
	private BrowseService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(Model model)
	{
		service.getHomePage(model);
		return "home";
	}
	

}
