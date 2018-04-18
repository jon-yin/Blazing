package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blazing.objects.Episode;
import com.blazing.objects.Media;
import com.blazing.objects.Season;
import com.blazing.repositories.GeneralMediaRepository;

@Controller
@RequestMapping("/")
public class IndexController {
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String home()
	{
		return "home";
	}
	

}
