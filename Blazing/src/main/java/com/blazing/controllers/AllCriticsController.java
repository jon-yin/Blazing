package com.blazing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blazing.objects.CriticApplication;
import com.blazing.objects.User;
import com.blazing.services.UserService;

@Controller
@RequestMapping(path="/critics")
public class AllCriticsController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCritics(Model model)
	{
		List<User> critics = userService.getCritics();
		model.addAttribute("critics", critics);
		return "critics-home";
	}
	
	@RequestMapping(path="/apply",method=RequestMethod.POST)
	@ResponseBody
	public boolean submitApplication(@RequestBody CriticApplication application)
	{
		return userService.addApplication(application);
	}
	
}
