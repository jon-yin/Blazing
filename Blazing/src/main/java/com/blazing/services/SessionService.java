package com.blazing.services;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.User;

@Service
public class SessionService {

	@Autowired
	private HttpSession session;
	
	public void updateCurrentUser(User user)
	{
		Hibernate.initialize(user.getFollowers());
		Hibernate.initialize(user.getWishlist());
		Hibernate.initialize(user.getNotInterested());
		Hibernate.initialize(user.getFollowing());
		Hibernate.initialize(user.getReviews());
		Hibernate.initialize(user.getBlockList());
		session.setAttribute("currentUser", user);
	}

}
