package com.blazing.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blazing.objects.Review;

@RestController
@RequestMapping("/reviews/")
public class ReviewController {

	@RequestMapping(path="/add", method=RequestMethod.POST)
	public Review addReview(@RequestBody Review review, @RequestParam("media") long id)
	{
		
		return review;
	}
	
	@RequestMapping(path="/delete", method=RequestMethod.POST)
	public void deleteReview(@RequestParam("reviewID") int id)
	{
		return;
	}
	
	
}
