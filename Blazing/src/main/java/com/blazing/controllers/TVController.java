package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.blazing.objects.Review;
import com.blazing.objects.TV;
import com.blazing.objects.User;
import com.blazing.services.MediaService;

@RequestMapping("/viewtv/{tv}")
public class TVController extends MediaController<TV>{
	
	@Autowired
	private MediaService mediaService;
	
	@RequestMapping(path="/addwishlist", method = RequestMethod.POST)
	@ResponseBody
	public boolean addToWishList(@PathVariable("tv") long tv, 
			@SessionAttribute("currentUser") User currentUser){
		return super.addToWishList(tv,currentUser);
	}
	
	@RequestMapping(path="/removewishlist", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeFromWishlist(@PathVariable("tv") long tv, 
			@SessionAttribute("currentUser") User currentUser){
		return super.removeFromWishList(tv,currentUser);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getTVDetails(@PathVariable("tv") long tv, Model model)
	{
		TV currentTV = mediaService.findTV(tv);
		model.addAttribute("tv", currentTV);
		return "tv-details";
	}
	
	@RequestMapping(path="/addnotinterested", method = RequestMethod.POST)
	@ResponseBody
	public boolean addToNotInterested(@PathVariable("tv") long tv, 
			@SessionAttribute("currentUser") User currentUser){
		return super.addToNotInterested(tv,currentUser);
	
	}
	
	@RequestMapping(path="/removenotinterested", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeFromNotInterested(@PathVariable("tv") long tv, 
			@SessionAttribute("currentUser") User currentUser){
		return super.removeFromNotInterested(tv,currentUser);
	}
	
	

	
	@ResponseBody
	@RequestMapping(path="/submitreview", method=RequestMethod.POST)
	public boolean addReview(@PathVariable("tv") long id, @RequestBody Review review, @SessionAttribute("currentUser") User currentUser)
	{
		return mediaService.addReview(currentUser, id, review);
	}
	
	@ResponseBody
	@RequestMapping(path="/removereview", method = RequestMethod.POST)
	public boolean removeReview(@RequestBody long id)
	{
		return super.removeReview(id);
	}
	
	
}
