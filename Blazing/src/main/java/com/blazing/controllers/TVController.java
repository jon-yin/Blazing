package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.blazing.objects.CriticReview;
import com.blazing.objects.EditedReviewInfo;
import com.blazing.objects.Episode;
import com.blazing.objects.ReportInfo;
import com.blazing.objects.Season;
import com.blazing.objects.TV;
import com.blazing.objects.User;
import com.blazing.services.MediaService;

@Controller
@RequestMapping(value="/viewtv/{tv}/{season}", method=RequestMethod.GET)
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
	public String getTVDetails(@PathVariable("tv") long tv, @PathVariable("season") long season, @SessionAttribute(required=false,value="currentUser") User user, Model model)
	{
		TV currentTV = mediaService.findTV(tv);
		model.addAttribute("tv", currentTV);
		long trueIndex = season - 1;
		Season sSeason = currentTV.getSeasons().get((int) trueIndex);
		model.addAttribute("season", sSeason);
		super.retrieveUnblockedReviews(user, sSeason, model);
		return "tv-details";
	}
	
	@RequestMapping(path="/{episode}",method = RequestMethod.GET)
	public String getEpisodeDetails(@PathVariable("tv") long tv, @PathVariable("season") long season, 
			@PathVariable("episode")long episode, @SessionAttribute(required=false,value="currentUser") User user, Model model)
	
	{
		TV currentTV = mediaService.findTV(tv);
		model.addAttribute("tv", currentTV);
		long trueIndex = season - 1;
		long trueEpisode = episode - 1;
		Season sSeason = currentTV.getSeasons().get((int) trueIndex);
		Episode sEpisode = sSeason.getEpisodes().get((int) trueEpisode);
		super.retrieveUnblockedReviews(user, sEpisode, model);
		return "episode-details";
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
	public boolean addReview(@PathVariable("tv") long id, @RequestBody CriticReview review, @SessionAttribute("currentUser") User currentUser)
	{
		return super.addReview(id,review,currentUser);
	}
	
	@ResponseBody
	@RequestMapping(path="/report", method = RequestMethod.POST)
	public boolean reportReview(@RequestBody ReportInfo info, @SessionAttribute("currentUser") User currentUser)
	{
		return super.reportReview(info, currentUser);
	}
	
	@ResponseBody
	@RequestMapping(path="/removereview", method = RequestMethod.POST)
	public boolean removeReview(@RequestBody long id)
	{
		return super.removeReview(id);
	}
	
	@ResponseBody
	@RequestMapping(path="/edit", method = RequestMethod.POST)
	public boolean  editReview(@RequestBody EditedReviewInfo info, @SessionAttribute(value="currentUser", required=false) User currentUser)
	{
		return super.editReview(currentUser, info);
	}
	
	
}
