package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blazing.objects.User;
import com.blazing.services.UserService;

@Controller
@RequestMapping("/viewprofile/{userid}")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(method=RequestMethod.GET)
	public String displayProfile(@PathVariable("userid") long userid, Model model)
	{
		User foundUser = userService.findUser(userid);
		model.addAttribute("foundUser", foundUser);
		return "profile";
	}
	
}
