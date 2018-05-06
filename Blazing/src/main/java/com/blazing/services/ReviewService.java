package com.blazing.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.Media;
import com.blazing.objects.Review;
import com.blazing.objects.User;
import com.blazing.repositories.MediaRepository;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.TVRepository;
import com.blazing.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MediaRepository mediaRepo;
	
	@Autowired
	private TVRepository tvRepo;
	
	public boolean addReview(Review review, long mediaID)
	{
		return true;
	}
	
	@Transactional
	public boolean removeReview(long id)
	{
		Optional<Review> foundReview = reviewRepo.findById(id);
		if (foundReview.isPresent())
		{
			Review review = foundReview.get();
			Media source = review.getSource();
			User reviewer = review.getUser();
			reviewer.removeFromReviews(review);
			source.removeReview(review);
			mediaRepo.save(source);
			userRepo.save(reviewer);
			reviewRepo.delete(review);
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
