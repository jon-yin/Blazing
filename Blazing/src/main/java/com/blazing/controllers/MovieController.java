package com.blazing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/viewmovie/{movie}")
public class MovieController{
	
	@RequestMapping(path="/addwishlist")
	@ResponseBody
	public Boolean addToWishList()
	{
		System.out.println("hello world");
		return true;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getMovieDetails(String movie)
	{
		//System.out.println(movie);
		return "movie-details";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String testPost(String movie)
	{
		//System.out.println("Hello World");
		return "movie-details";
	}

}
