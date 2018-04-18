package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blazing.objects.Movie;
import com.blazing.repositories.MovieRepository;

@RestController
@RequestMapping("/upload_movie")
public class MediaUploadController {

	@Autowired
	private MovieRepository mediaRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	public boolean UploadMovie(Movie movie, @RequestParam("cast") String cast, @RequestParam("runtimes") String[] runtime)
	{
		return true;
	}
	
}
