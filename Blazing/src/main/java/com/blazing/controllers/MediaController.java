package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.blazing.objects.Review;
import com.blazing.objects.User;
import com.blazing.services.MediaService;
import com.blazing.services.UserService;

@Controller
public class MediaController<T> {

	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private UserService userService;
	
	
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
	
	
	
}
