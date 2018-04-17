package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.blazing.objects.Movie;
import com.blazing.objects.User;
import com.blazing.services.MediaService;
import com.blazing.services.UserService;

@Controller
@RequestMapping("/viewmovie/{movie}")
public class MovieController{
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(path="/addwishlist", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addToWishList(@PathVariable("movie") long movie, 
			@SessionAttribute("currentUser") User currentUser)
	{
		if (currentUser == null)
		{
			return false;
		}
		else
		{
			boolean status = mediaService.addToWishlist(currentUser, movie);
			if (status)
			{
				userService.saveUserState(currentUser);
			}
			return status;
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getMovieDetails(@PathVariable("movie") long movie, Model model)
	{
		Movie currentMovie = mediaService.findMovie(movie);
		model.addAttribute("movie", currentMovie);
		return "movie-details";
	}
	

}
