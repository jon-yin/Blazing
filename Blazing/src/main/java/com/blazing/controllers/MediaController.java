package com.blazing.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.blazing.objects.Review;
import com.blazing.objects.User;
import com.blazing.services.MediaService;
import com.blazing.services.ReviewService;
import com.blazing.services.UserService;

@Controller
public class MediaController<T> {

	@Autowired
	private ReviewService revService;
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private UserService userService;
	
	
	@Transactional
	public boolean addToWishList(long id, User user)
	{
		if (user == null)
		{
			return false;
		}
		else
		{
			boolean status = mediaService.addToWishlist(user, id);
			if (status)
			{
				userService.saveUserState(user);
			}
			return status;
		}
	}
	
	@Transactional
	public boolean removeFromWishList(long id, User user)
	{
		if (user == null)
		{
			return false;
		}
		else
		{
			boolean status = mediaService.removeFromWishlist(user, id);
			if (status)
			{
				userService.saveUserState(user);
			}
			return status;
		}
	}
	
	@Transactional
	public boolean addToNotInterested(long id, User user)
	{
		if (user == null)
		{
			return false;
		}
		else
		{
			boolean status = mediaService.addToNotInterested(user, id);
			if (status)
			{
				userService.saveUserState(user);
			}
			return status;
		}
	}
	
	@Transactional
	public boolean removeFromNotInterested(long id, User user)
	{
		if (user == null)
		{
			return false;
		}
		else
		{
			boolean status = mediaService.removeFromNotInterested(user, id);
			if (status)
			{
				userService.saveUserState(user);
			}
			return status;
		}
	}
	
	@Transactional
	public boolean addReview(long id, Review review, User user)
	{
		if (user == null)
		{
			return false;
		}
		else
		{
			boolean status = mediaService.addReview(user, id, review);
			if (status)
			{
				userService.saveUserState(user);
			}
			return status;
		}
	}
	
	@Transactional
	public boolean removeReview(long reviewID)
	{
		return revService.removeReview(reviewID);
	}
	
	@Transactional
	public boolean reportReview(long reviewID, User currentUser)
	{
		if (currentUser == null)
		{
			return false;
		}
		return mediaService.reportReview(reviewID);
	}
	
	
	
}
