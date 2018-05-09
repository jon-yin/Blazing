package com.blazing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.blazing.objects.CriticApplication;
import com.blazing.objects.Review;
import com.blazing.objects.Roles;
import com.blazing.objects.User;
import com.blazing.services.ReviewService;
import com.blazing.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private ReviewService reviewService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminPage(@SessionAttribute(value="currentUser", required=false) User user, Model model)
	{
		if (user == null || user.getRole() != Roles.ADMIN)
		{
			return "redirect:/";
		}
		else
		{
			List<CriticApplication> applications = userService.retrieveApplications();
			//System.out.println(applications.size());
			model.addAttribute("applications", applications);
			List<Review> reported = reviewService.retrieveReportedReviews();
			model.addAttribute("reported", reported);
			return "admin";
		}
	}
	
	@RequestMapping(path="/approvecritic", method = RequestMethod.POST)
	@ResponseBody
	public boolean approveCritic(@RequestBody CriticApplication application)
	{
		try {
			userService.upgradeUser(application.getUserId(), Roles.CRITIC);
			userService.removeApplication(application);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping(path="/denycritic", method = RequestMethod.POST)
	@ResponseBody
	public boolean denyCritic(@RequestBody CriticApplication application)
	{
		try {
			userService.removeApplication(application);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping(path="/removereview", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeReview(@RequestBody long reviewId)
	{
		return reviewService.removeReview(reviewId, false);
	}
	
	@RequestMapping(path="/dismissreview", method = RequestMethod.POST)
	@ResponseBody
	public boolean dismissReview(@RequestBody long reviewId)
	{
		return reviewService.dismissFlag(reviewId);
	}
	
}
