package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blazing.services.EmailService;
import com.blazing.services.MediaService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private MediaService mediaService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(Model model)
	{
		mediaService.getTrendingMovies(model);
		return "home";
	}
	

}
