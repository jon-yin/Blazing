package com.blazing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.Review;
import com.blazing.objects.User;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.TVRepository;
import com.blazing.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private UserRepository<User> userRepo;
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired
	private TVRepository tvRepo;
	
	public boolean addReview(Review review, long mediaID)
	{
		return true;
	}
	
}
