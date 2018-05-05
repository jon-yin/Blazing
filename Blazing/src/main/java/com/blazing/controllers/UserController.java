package com.blazing.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.blazing.objects.CriticApplication;
import com.blazing.objects.MovieInfo;
import com.blazing.objects.Roles;
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
	
	@ResponseBody
	@RequestMapping(path="/follow", method=RequestMethod.POST)
	public boolean follow(@SessionAttribute("currentUser")User curUser, @RequestBody Long id)
	{
		return userService.followUser(curUser, id);
	}
	
	@ResponseBody
	@RequestMapping(path="/unfollow", method=RequestMethod.POST)
	public boolean unfollow(@SessionAttribute("currentUser")User curUser, @RequestBody Long id)
	{
		return userService.unfollowUser(curUser, id);
	}
	
	@RequestMapping(path="/settings", method = RequestMethod.GET)
	public String settingsView(@SessionAttribute("currentUser") User curUser, @PathVariable("userid") long id)
	{
		
		if (curUser == null || curUser.getId() != id)
		{
			return "redirect:/home";
		}
		else
		{
			return "settings";
		}
	}
	
	@RequestMapping(path="/delete", method = RequestMethod.POST)
	public String deleteAccount(@RequestParam("userid")long id, HttpSession session)
	{
		User foundUser = userService.findUser(id);
		if (foundUser != null)
		{
			User currentUser= (User)session.getAttribute("currentUser");
			if (currentUser.equals(foundUser))
			{
				session.setAttribute("currentUser", null);
			}
			userService.removeUser(foundUser);
		}
		return "redirect:/";
	}
	
	@RequestMapping(path="/approvecritic", method = RequestMethod.POST)
	@ResponseBody
	public void approveCritic(@RequestBody CriticApplication application)
	{
		userService.upgradeUser(application.getUserId(), Roles.CRITIC);
		userService.removeApplication(application);
	}
	
	@RequestMapping(path="/denycritic", method = RequestMethod.POST)
	@ResponseBody
	public void denyCritic(@RequestBody CriticApplication application)
	{
		userService.removeApplication(application);
	}
	
	
}
