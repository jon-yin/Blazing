package com.blazing.controllers;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.blazing.objects.EditedReviewInfo;
import com.blazing.objects.Movie;
import com.blazing.objects.ReportInfo;
import com.blazing.objects.Review;
import com.blazing.objects.User;
import com.blazing.services.MediaService;
import com.blazing.services.UserService;

@Controller
@RequestMapping(path="/viewmovie/{movie}")
public class MovieController extends MediaController<Movie>{
	
	@Autowired
	private MediaService mediaService;
	@Autowired
	private UserService userService;
	@Autowired
	private EntityManager em;
	
	
	@RequestMapping(path="/addwishlist", method = RequestMethod.POST)
	@ResponseBody
	public boolean addToWishList(@PathVariable("movie") long movie, 
			@SessionAttribute("currentUser") User currentUser){
		System.out.println(em.contains(currentUser));
		return super.addToWishList(movie,currentUser);
	
	}
	
	@RequestMapping(path="/addnotinterested", method = RequestMethod.POST)
	@ResponseBody
	public boolean addToNotInterested(@PathVariable("movie") long movie, 
			@SessionAttribute("currentUser") User currentUser){
		return super.addToNotInterested(movie,currentUser);
	
	}
	
	@RequestMapping(path="/removewishlist", method = RequestMethod.POST)
	@ResponseBody
	public boolean removefromWishList(@PathVariable("movie") long movie, 
			@SessionAttribute("currentUser") User currentUser){
		boolean status =  super.removeFromWishList(movie,currentUser);
		return status;
	
	}
	
	@RequestMapping(path="/removenotinterested", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeFromNotInterested(@PathVariable("movie") long movie, 
			@SessionAttribute("currentUser") User currentUser){
		return super.removeFromNotInterested(movie,currentUser);
	
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getMovieDetails(@PathVariable("movie") long movie, Model model)
	{
		Movie currentMovie = mediaService.findMovie(movie);
		model.addAttribute("movie", currentMovie);
		return "movie-details";
	}
	
	@ResponseBody
	@RequestMapping(path="/submitreview", method=RequestMethod.POST)
	public boolean addReview(@PathVariable("movie") long id, @RequestBody Review review, @SessionAttribute("currentUser") User currentUser)
	{
		return mediaService.addReview(currentUser, id, review);
	}
	
	@ResponseBody
	@RequestMapping(path="/editreview")
	public boolean editReview(@RequestBody EditedReviewInfo newInfo, @SessionAttribute("currentUser") User currentUser)
	{
		return mediaService.editReview(newInfo,currentUser);
	}
	
	@ResponseBody
	@RequestMapping(path="/report", method=RequestMethod.POST)
	public boolean reportReview(@RequestBody ReportInfo data, @SessionAttribute("currentUser") User user)
	{
		return super.reportReview(data, user);
	}
	
	@ResponseBody
	@RequestMapping(path="/removereview", method=RequestMethod.POST)
	public boolean removeReview(@RequestBody long id)
	{
		return super.removeReview(id);
	}
	

}
