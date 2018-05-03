package com.blazing.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.User;

@Service
public class SessionService {

	@Autowired
	private HttpSession session;
	
	public void updateCurrentUser(User user)
	{
		session.setAttribute("currentUser", user);
	}

}
