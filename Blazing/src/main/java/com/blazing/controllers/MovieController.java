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

import com.blazing.objects.Movie;
import com.blazing.objects.Review;
import com.blazing.objects.User;
import com.blazing.services.MediaService;

@Controller
@RequestMapping(path="/viewmovie/{movie}")
public class MovieController extends MediaController<Movie>{
	
	@Autowired
	private MediaService mediaService;
	
	
	@RequestMapping(path="/addwishlist", method = RequestMethod.POST)
	@ResponseBody
	public boolean addToWishList(@PathVariable("movie") long movie, 
			@SessionAttribute("currentUser") User currentUser){
		return super.addToWishList(movie,currentUser);
	
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
	public boolean addReview(@PathVariable("movie") long id, @RequestBody Review review, @SessionAttribute("currentUser") User currentUser, Model model)
	{
		System.out.println();
		return mediaService.addReview(currentUser, id, review);
	}
	

}
