package com.blazing.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blazing.objects.Movie;
import com.blazing.objects.MovieInfo;
import com.blazing.objects.User;
import com.blazing.services.MediaService;
import com.blazing.services.UserService;

@Controller
@RequestMapping("/viewprofile/{userid}")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private MediaService mediaService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String displayProfile(@PathVariable("userid") long userid, Model model)
	{
		User foundUser = userService.findUser(userid);
		model.addAttribute("foundUser", foundUser);
		return "profile";
	}
	
	@ResponseBody
	@RequestMapping(path="/uploadmovie", method=RequestMethod.POST)
	public boolean uploadMedia(@RequestBody MovieInfo info)
	{
		mediaService.uploadMovie(info);
		return true;
	}
	
}
