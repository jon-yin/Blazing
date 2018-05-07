package com.blazing.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.blazing.objects.CriticReview;
import com.blazing.objects.EditedReviewInfo;
import com.blazing.objects.Media;
import com.blazing.objects.ReportInfo;
import com.blazing.objects.Review;
import com.blazing.objects.Roles;
import com.blazing.objects.User;
import com.blazing.services.MediaService;
import com.blazing.services.ReviewService;
import com.blazing.services.SessionService;
import com.blazing.services.UserService;

@Controller
public class MediaController<T> {

	@Autowired
	private ReviewService revService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sesService;
	

	public boolean addToWishList(long id, User user) {
		if (user == null) {
			return false;
		} else {
			boolean status = mediaService.addToWishlist(user, id);
			if (status) {
				User updatedUser = userService.saveUserState(user);
				sesService.updateCurrentUser(updatedUser);
			}
			return status;
		}
	}

	public boolean removeFromWishList(long id, User user) {
		if (user == null) {
			return false;
		} else {
			boolean status = mediaService.removeFromWishlist(user, id);
			if (status) {
				User updated = userService.saveUserState(user);
				sesService.updateCurrentUser(updated);
			}
			return status;
		}
	}

	public boolean addToNotInterested(long id, User user) {
		if (user == null) {
			return false;
		} else {
			boolean status = mediaService.addToNotInterested(user, id);
			if (status) {
				User updated = userService.saveUserState(user);
				sesService.updateCurrentUser(updated);
			}
			return status;
		}
	}

	public Review retrieveReviewFromUser(User user, long movieID) {
		if (user != null) {
			List<Review> reviews = user.getReviews();
			for (Review review : reviews) {
				if (review.getSource().getId() == movieID)
				{
					return review;
				}
			}
			return null;
		}
		else
		{
			return null;
		}
	}

	public boolean removeFromNotInterested(long id, User user) {
		if (user == null) {
			return false;
		} else {
			boolean status = mediaService.removeFromNotInterested(user, id);
			if (status) {
				User updated = userService.saveUserState(user);
				sesService.updateCurrentUser(updated);
			}
			return status;
		}
	}

	@Transactional
	public boolean addReview(long id, CriticReview review, User user) {
		if (user == null || !user.isEnabled()) {
			return false;
		} else {
			boolean status = false;
			if (user.getRole() == Roles.CRITIC)
			{
				status = mediaService.addReview(user, id, review);
			}
			else
			{
				Review castedReview = (Review) review;
				status = mediaService.addReview(user, id, castedReview);
			}
			if (status) {
				User updated = userService.saveUserState(user);
				sesService.updateCurrentUser(updated);
			}
			return status;
		}
	}

	@Transactional
	public boolean removeReview(long reviewID) {
		return revService.removeReview(reviewID,true);
	}

	@Transactional
	public boolean reportReview(ReportInfo reportData, User currentUser) {
		if (currentUser == null) {
			return false;
		}
		return mediaService.reportReview(reportData, currentUser.getId());
	}

	@Transactional
	public boolean editReview(User user, EditedReviewInfo newBody) {
		if (user == null) {
			return false;
		}
		return mediaService.editReview(newBody, user);
	}

	public void retrieveUnblockedReviews(User user, Media currentMedia,Model model) {
		if (user==null || currentMedia == null)
		{
			model.addAttribute("reviews", null);
			model.addAttribute("criticreviews", null);
			System.out.println("CRITIC REVIEWS: " + currentMedia.getCriticReviews().size());
			System.out.println("AUDIENCE REVIEWS: " + currentMedia.getAudienceReviews().size());
			return;
		}
		else{
			List<Review> unblocked = currentMedia.nonBlockedAudience(user);
			model.addAttribute("reviews", unblocked);
			List<CriticReview> c_unblocked = currentMedia.nonBlockedCritic(user);
			model.addAttribute("criticreviews", c_unblocked);
		}
	}

}
