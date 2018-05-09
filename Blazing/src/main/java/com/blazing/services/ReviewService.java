package com.blazing.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.CriticReview;
import com.blazing.objects.Media;
import com.blazing.objects.Review;
import com.blazing.objects.Roles;
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
	
	@Autowired
	private SessionService sesService;
	
	@Transactional
	public boolean removeReview(long id, boolean isSession)
	{
		Optional<Review> foundReview = reviewRepo.findById(id);
		if (foundReview.isPresent())
		{
			Review review = foundReview.get();
			Media source = review.getSource();
			User reviewer = review.getUser();
			reviewer.removeFromReviews(review);
			if (reviewer.getRole() == Roles.CRITIC)
			{
				source.removeCriticReview((CriticReview)review);
			}
			else
			{
				source.removeReview((CriticReview)review);
			}
			mediaRepo.save(source);
			User updated = userRepo.save(reviewer);
			reviewRepo.delete(review);
			if (isSession)
			{
				sesService.updateCurrentUser(updated);
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Transactional
	public List<Review> retrieveReportedReviews()
	{
		return reviewRepo.findByFlagCountGreaterThanOrderByFlagCountDesc(0);
	}
	
	@Transactional
	public boolean dismissFlag(long reviewID)
	{
		Optional<Review> review = reviewRepo.findById(reviewID);
		if (review.isPresent())
		{
			Review foundReview = review.get();
			foundReview.setFlagCount(0);
			Map<Long,String> flags = foundReview.getUserReports();
			flags.clear();
			reviewRepo.save(foundReview);
			return true;
		}
		else
		{
			return false;
		}
	}

	public List<Review> getMostRecentCritic() {
		return reviewRepo.findTop100ByCriticTrueOrderByDatetimeDesc();
	}
	
}
