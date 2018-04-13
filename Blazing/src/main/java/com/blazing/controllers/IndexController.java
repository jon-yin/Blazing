package com.blazing.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blazing.objects.Movie;
import com.blazing.repositories.MovieRepository;

@Controller
@RequestMapping("/")
public class IndexController {
	
	private MovieRepository movieRepo;
	
	@Autowired
	public IndexController(MovieRepository repo)
	{
		movieRepo = repo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String home()
	{
		Optional<Movie> movie = movieRepo.findById(1L);
		System.out.println(movie.orElse(null).getName());
		Optional<Movie> m = movieRepo.findMovieByName("Mission Impossible");
		System.out.println(m.orElseGet(()-> new Movie()).getName());
		return "home";
	}
	
	

}
