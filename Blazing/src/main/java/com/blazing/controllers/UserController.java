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

import com.blazing.objects.ChangePassword;
import com.blazing.objects.MovieInfo;
import com.blazing.objects.User;
import com.blazing.services.LoginRegisterService;
import com.blazing.services.MediaService;
import com.blazing.services.UserService;

@Controller
@RequestMapping("/viewprofile/{userid}")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private LoginRegisterService loginService;

	@RequestMapping(method = RequestMethod.GET)
	public String displayProfile(@PathVariable("userid") long userid, Model model) {
		User foundUser = userService.findUser(userid);
		model.addAttribute("foundUser", foundUser);
		if (foundUser == null) {
			return "redirect:/";
		} else {
			return "profile";
		}
	}

	@ResponseBody
	@RequestMapping(path = "/uploadmovie", method = RequestMethod.POST)
	public boolean uploadMedia(@RequestBody MovieInfo info) {
		mediaService.uploadMovie(info);
		return true;
	}

	@ResponseBody
	@RequestMapping(path = "/follow", method = RequestMethod.POST)
	public boolean follow(@SessionAttribute("currentUser") User curUser, @RequestBody Long id) {
		return userService.followUser(curUser, id);
	}

	@ResponseBody
	@RequestMapping(path = "/unfollow", method = RequestMethod.POST)
	public boolean unfollow(@SessionAttribute("currentUser") User curUser, @RequestBody Long id) {
		return userService.unfollowUser(curUser, id);
	}

	@RequestMapping(path = "/settings", method = RequestMethod.GET)
	public String settingsView(@SessionAttribute("currentUser") User curUser, @PathVariable("userid") long id) {

		if (curUser == null || curUser.getId() != id) {
			return "redirect:/home";
		} else {
			return "settings";
		}
	}

	@ResponseBody
	@RequestMapping(path = "/changepass", method = RequestMethod.POST)
	public boolean changePassword(@RequestBody ChangePassword info,
			@SessionAttribute(value = "currentUser", required = false) User user) {
		String password = info.getOldPass();
		String newPass = info.getNewPass();
		String confirm = info.getConfirmNewPass();
		return userService.changePassword(password, newPass, confirm, user);
	}

	@ResponseBody
	@RequestMapping(path = "/changeemail", method = RequestMethod.POST)
	public boolean changeEmail(@RequestBody String newEmail,
			@SessionAttribute(value = "currentUser", required = false) User user) {
		return loginService.changeEmail(newEmail, user);
	}

	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	public String deleteAccount(@RequestParam("userid") long id, HttpSession session) {
		User foundUser = userService.findUser(id);
		if (foundUser != null) {
			User currentUser = (User) session.getAttribute("currentUser");
			if (currentUser.equals(foundUser)) {
				session.setAttribute("currentUser", null);
			}
			userService.removeUser(foundUser);
		}
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(path = "/block", method = RequestMethod.POST)
	public boolean blockUser(@PathVariable("userid") long id, @SessionAttribute("currentUser") User user)
	{
		User target = userService.findUser(id);
		return userService.blockUser(target, user);
	}
	
	@ResponseBody
	@RequestMapping(path = "/unblock", method = RequestMethod.POST)
	public boolean unblockUser(@PathVariable("userid") long id, @SessionAttribute("currentUser") User user)
	{
		User target = userService.findUser(id);
		return userService.unblockUser(target, user);
	}
}
